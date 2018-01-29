package org.doclerholding.codechallenge.netutil.service.tcpip;

import java.io.IOException;
import java.util.logging.Logger;

import org.doclerholding.codechallenge.netutil.service.AbstractJob;
import org.doclerholding.codechallenge.netutil.service.ReportableError;
import org.doclerholding.codechallenge.netutil.service.exception.BusnessExceptioin;
import org.doclerholding.codechallenge.netutil.service.exception.ErrorType;
import org.doclerholding.codechallenge.netutil.util.MonitoringServices;
import org.doclerholding.codechallenge.netutil.util.SoketHelper;

public class TCPIPJob  extends AbstractJob implements ReportableError{

	private long timeOut;
 
	
	protected TCPIPJob(final long timeOut) {
		super("");
		log= Logger.getLogger(TCPIPJob.class.getName());
		this.timeOut = timeOut;
	}

	@Override
	protected String runService() throws BusnessExceptioin {
		try {
			StringBuilder result= new SoketHelper().ping((int) timeOut, host);
			return result.toString();
		} catch (IOException e) {
			throw new BusnessExceptioin(ErrorType.TCPIP_FAIL,e);
		}
		
	}

	@Override
	protected MonitoringServices getServiceType() {
		return MonitoringServices.TPC_IP;
	}

	

}
