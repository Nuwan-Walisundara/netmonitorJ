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

import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.doclerholding.codechallenge.netutil.model.ErrorMsgDTO;
import org.doclerholding.codechallenge.netutil.model.MessageDTO;
import org.doclerholding.codechallenge.netutil.service.exception.BusnessExceptioin;
import org.doclerholding.codechallenge.netutil.util.MonitoringServices;

public abstract class AbstractJob implements ShedulerbleJob {

	protected String host;
	protected final String command;
	protected BlockingQueue<ErrorMsgDTO> errorQueue;
	protected BlockingQueue<MessageDTO> messageQueue;

	protected abstract String runService() throws BusnessExceptioin;

	protected abstract MonitoringServices getServiceType();

	protected Logger log;

	protected AbstractJob(String command) {
		this.command = command;
	}

	@Override
	final public void setHost(String host) {
		this.host = host;
	}

	@Override
	final public void setSuccessQueue(BlockingQueue<MessageDTO> sucessrQueue) {
		this.messageQueue = sucessrQueue;
	}

	@Override
	final public void setErrorQueue(BlockingQueue<ErrorMsgDTO> errorQueue) {
		this.errorQueue = errorQueue;
	}

	@Override
	public void run() {
		final String runningCmd = command + host;
		String successMessag=null;
		try {

			log.log(Level.INFO, "Job started :" + runningCmd);

			/**
			 * Run the concrete service and get the success message
			 */
			successMessag = runService();
			
		} catch (BusnessExceptioin e) {
			successMessag =  e.getMessage();
			reportToErrorQueue(e);
			
		}
		addtoGenaralQueue(successMessag,runningCmd);
	}
	/**
	 * if the occured error is need to report (i.e ICMP,TCP/IP) ,the messages put
	 * into error Queue
	 */
	private void reportToErrorQueue(BusnessExceptioin e) {
		/**
		 * if the occured error is need to report (i.e ICMP,TCP/IP) ,the messages put
		 * into error Queue
		 */
		if (this instanceof ReportableError) {
			try {
				ErrorMsgDTO error = new ErrorMsgDTO(host, getServiceType(), e.getMessage());

				log.log(Level.SEVERE, error.toString());
				errorQueue.put(error);
			} catch (InterruptedException e1) {
				log.log(Level.WARNING, "exception occurd whil waiting to add to error Quee :", e1);
			}
		}
	}
	/**
	 * put the success message into ,success Queue for processing
	 */

	private void addtoGenaralQueue(final String msg, final String runningCmd) {
		MessageDTO successMsg = new MessageDTO(host, getServiceType(), msg);
		log.log(Level.INFO, successMsg.toString());
		try {
			messageQueue.put(successMsg);
		} catch (InterruptedException e) {
			log.log(Level.WARNING, "exception occurd :" + runningCmd, e);
		}
	}
	
}
