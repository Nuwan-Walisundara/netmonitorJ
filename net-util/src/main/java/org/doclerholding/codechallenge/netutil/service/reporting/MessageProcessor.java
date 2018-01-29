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
package org.doclerholding.codechallenge.netutil.service.reporting;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.doclerholding.codechallenge.netutil.model.HostStatusDTO;
import org.doclerholding.codechallenge.netutil.model.MessageDTO;
import org.doclerholding.codechallenge.netutil.service.exception.InvalidHostException;
/**
 * This service will process all the messages send from the message queue
 * 
 * @author nuwan
 *
 */
public class MessageProcessor implements MsgProcessorble<MessageDTO> {
	private static MessageProcessor instance;
	private final ConcurrentMap<String, HostStatusDTO> concurrentMap;
	private final ExecutorService executorService;

	private MessageProcessor() {
		concurrentMap = new ConcurrentHashMap<String, HostStatusDTO>();
		executorService = Executors.newCachedThreadPool();
		
	}

	public static MessageProcessor getInstance() {
		if (instance == null) {
			instance = new MessageProcessor();
		}
		return instance;
	}
	


	@Override
	public void process(MessageDTO e) {

		executorService.submit(new Runnable() {

			@Override
			public void run() {
				if (concurrentMap.containsKey(e.getHost())) {
					/**
					 * update the lates message
					 */
					concurrentMap.get(e.getHost()).updateMessage(e);
				} else {
					HostStatusDTO hostdto= new  HostStatusDTO( e.getHost());
					hostdto.updateMessage(e);
					concurrentMap.put(e.getHost(),hostdto);

				}
			}

		});

	}

	public HostStatusDTO getResult(String host) throws InvalidHostException {
		if (host == null || host.trim().length() == 0) {
			throw new InvalidHostException();
		}

		if (concurrentMap.containsKey(host)) {
			return concurrentMap.get(host);
		} else {
			throw new InvalidHostException();
		}
	}
	
}
