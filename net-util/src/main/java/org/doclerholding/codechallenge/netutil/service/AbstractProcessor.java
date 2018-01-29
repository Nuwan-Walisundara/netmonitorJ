
/**
 * netUtilJ
 * Copyright 2018 and beyond, Nuwan Walisundara
 * <p/>
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License version 3
 * You contribute feedback, fixes, and requests for features
 * User: Nuwan Walisundara
 * Date: Jan 28, 2018
 * Time: 10:02:25 PM
 */
package org.doclerholding.codechallenge.netutil.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

import org.doclerholding.codechallenge.netutil.model.ErrorMsgDTO;
import org.doclerholding.codechallenge.netutil.model.MessageDTO;
import org.doclerholding.codechallenge.netutil.service.exception.BusnessExceptioin;
import org.doclerholding.codechallenge.netutil.service.exception.ErrorType;

public abstract class AbstractProcessor implements ProcessControllerble {
	protected final Properties properties;
	protected Long delay;
	protected List<String> hosts = new ArrayList<>();
	private boolean haveValidHosts;
	protected Logger log = Logger.getLogger(this.getClass().getName());
	protected BlockingQueue<ErrorMsgDTO>  errorQueue;
	protected BlockingQueue<MessageDTO>  sucessrQueue;
	/**
	 * Run concreet implemented validations
	 * 
	 * @throws BusnessExceptioin
	 */
	protected abstract void runSpecifcValidation() throws BusnessExceptioin;

	protected abstract ShedulerbleJob createJob();


	public AbstractProcessor(final Properties properties) {
		this.properties = properties;
	}

	/**
	 * run validation process
	 */
	@Override
	public final void init(final BlockingQueue<ErrorMsgDTO> errorQueue , final BlockingQueue<MessageDTO> sucessrQueue ) throws BusnessExceptioin {
		this.errorQueue = errorQueue;
		this.sucessrQueue = sucessrQueue;
		validateHosts();

		runSpecifcValidation();

	}

	/**
	 * hosts need to derive as genarically
	 */
	private void validateHosts() throws BusnessExceptioin {
		if (this.properties.get("hosts") == null || properties.get("hosts").toString().trim().length() == 0) {
			throw new BusnessExceptioin(ErrorType.NULL_HOSTS);
		}
		haveValidHosts = true;
		// final String
		// hostRegex="(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])";
		final String[] hostsStr = properties.get("hosts").toString().trim().split(",");
		// Pattern p = Pattern.compile(hostRegex);

		// TODO:need ip/host validation
		// Arrays.stream( hostsStr).forEach(item ->{
		// Matcher m = p.matcher(item);
		// if(m.matches()) {
		// hosts.add(item);
		// }else {
		// throw new BusnessExceptioin(ErrorType.NULL_HOSTS);
		// }
		// });
		hosts.addAll(Arrays.asList(hostsStr));

	}

	@Override
	public void sheduleHosts() throws BusnessExceptioin {
		if (haveValidHosts) {
			hosts.forEach(host -> {
				new Thread(new Runnable() {

					@Override
					public void run() {
						while (true) {
							ShedulerbleJob job =createJob();
							job.setErrorQueue(errorQueue);
							job.setHost(host);	
							job.setSuccessQueue(sucessrQueue);
							
							new Thread(job).start();

							try {
								Thread.sleep(delay);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					}

				}).start();
			});
		} else {
			throw new BusnessExceptioin(ErrorType.NULL_HOSTS);
		}
	}


}
