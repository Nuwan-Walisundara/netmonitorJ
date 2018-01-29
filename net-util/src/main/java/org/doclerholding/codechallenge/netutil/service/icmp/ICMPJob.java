package org.doclerholding.codechallenge.netutil.service.icmp;

import java.util.logging.Logger;

import org.doclerholding.codechallenge.netutil.service.AbstractJob;
import org.doclerholding.codechallenge.netutil.service.ReportableError;
import org.doclerholding.codechallenge.netutil.service.exception.BusnessExceptioin;
import org.doclerholding.codechallenge.netutil.util.MonitoringServices;
import org.doclerholding.codechallenge.netutil.util.ProcessUtil;

class ICMPJob extends AbstractJob implements ReportableError{

	protected ICMPJob(String command) {
		super(command);
		log = Logger.getLogger(ICMPJob.class.getName() );
	}

	@Override
	protected MonitoringServices getServiceType() {
		return MonitoringServices.ICMP;
	}

	@Override
	protected String runService() throws BusnessExceptioin {
		StringBuilder successMsg = ProcessUtil.executeProcessAndGetOutputAsStringList(command + " " + host);
		return successMsg.toString();
	}

}
