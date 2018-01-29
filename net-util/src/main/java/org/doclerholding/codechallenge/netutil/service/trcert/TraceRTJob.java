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

import java.util.logging.Logger;

import org.doclerholding.codechallenge.netutil.service.AbstractJob;
import org.doclerholding.codechallenge.netutil.service.ReportableError;
import org.doclerholding.codechallenge.netutil.service.exception.BusnessExceptioin;
import org.doclerholding.codechallenge.netutil.service.tcpip.TCPIPJob;
import org.doclerholding.codechallenge.netutil.util.MonitoringServices;
import org.doclerholding.codechallenge.netutil.util.ProcessUtil;

class TraceRTJob extends AbstractJob implements ReportableError {

	protected TraceRTJob(String command) {
		super(command);
		log= Logger.getLogger(TCPIPJob.class.getName());
	}

	@Override
	protected String runService() throws BusnessExceptioin {
		StringBuilder successMsg =ProcessUtil.executeProcessAndGetOutputAsStringList(command+ " "+ host);
		return successMsg.toString();
	}


	@Override
	protected MonitoringServices getServiceType() {
		return MonitoringServices.TRACERT;
	}

}
