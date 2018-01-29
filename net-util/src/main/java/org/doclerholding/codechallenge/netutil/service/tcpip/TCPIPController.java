package org.doclerholding.codechallenge.netutil.service.tcpip;

import java.util.Properties;

import org.doclerholding.codechallenge.netutil.service.AbstractProcessor;
import org.doclerholding.codechallenge.netutil.service.ShedulerbleJob;
import org.doclerholding.codechallenge.netutil.service.exception.BusnessExceptioin;
import org.doclerholding.codechallenge.netutil.service.exception.ErrorType;

public class TCPIPController extends AbstractProcessor {

	private long timeout;
	
	public TCPIPController(Properties properties) {
		super(properties);
	}


	@Override
	protected void runSpecifcValidation() throws BusnessExceptioin {
		//Validating schedule time
		if(properties.getProperty("tcpip.delay")==null) {
			throw new BusnessExceptioin(ErrorType.NULL_ICMP_DELAY);
		}
		delay = Long.valueOf(properties.getProperty("tcpip.delay"));
		if(properties.getProperty("tcpip.request.timeout")!=null ) {
			try {
				timeout = Long.valueOf(properties.getProperty("tcpip.request.timeout"));
			} catch (NumberFormatException e) {
				throw new BusnessExceptioin(ErrorType.INVALID_TCPIP_TIME_OUT);
			}
		}
		try {
			this.delay = Long.valueOf(properties.getProperty("tcpip.delay") );
		} catch (NumberFormatException e) {
			throw new BusnessExceptioin(ErrorType.INVALID_TCPIP_SHEDULE_TIME);
		}
	}


	@Override
	public ShedulerbleJob createJob() {
	
		return new TCPIPJob(timeout);
	}

}
