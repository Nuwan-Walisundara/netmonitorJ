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
package org.doclerholding.codechallenge.netutil.service.trcert;

import java.util.Properties;

import org.doclerholding.codechallenge.netutil.service.AbstractProcessor;
import org.doclerholding.codechallenge.netutil.service.ShedulerbleJob;
import org.doclerholding.codechallenge.netutil.service.exception.BusnessExceptioin;
import org.doclerholding.codechallenge.netutil.service.exception.ErrorType;

public class TraceRTController  extends AbstractProcessor  {

	private String command;
	
	public TraceRTController(Properties properties) {
		super(properties);
	}

	@Override
	protected void runSpecifcValidation() throws BusnessExceptioin {
		try {
			this.delay = Long.valueOf(properties.getProperty("tracert.delay") );
		} catch (NumberFormatException e) {
			throw new BusnessExceptioin(ErrorType.INVALID_TRACERT_SHEDULE_TIME);
		}
		
		/**
		 * traceRT command is mandetory
		 */
		if(properties.getProperty("tracert.command")==null || properties.getProperty("tracert.command").trim().length()==0) {
			throw new BusnessExceptioin(ErrorType.NULL_TRACE_RT_STR);
		}
		command=properties.getProperty("tracert.command").toString().replace("HOST","");;
		
	}

	@Override
	public ShedulerbleJob createJob() {
		return new TraceRTJob(command);
	}



	 

}
