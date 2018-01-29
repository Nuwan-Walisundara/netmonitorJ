package org.doclerholding.codechallenge.netutil.service.icmp;

import java.util.Properties;
import java.util.logging.Level;

import org.doclerholding.codechallenge.netutil.service.AbstractProcessor;
import org.doclerholding.codechallenge.netutil.service.ShedulerbleJob;
import org.doclerholding.codechallenge.netutil.service.exception.BusnessExceptioin;
import org.doclerholding.codechallenge.netutil.service.exception.ErrorType;

public class ICMPController extends AbstractProcessor {

	private String commandStr;

	public ICMPController(Properties properties) {
		super(properties);
	}

	@Override
	public void runSpecifcValidation() throws BusnessExceptioin {

		if (properties.getProperty("icmp.command") == null
				|| properties.getProperty("icmp.command").trim().length() == 0) {
			throw new BusnessExceptioin(ErrorType.NULL_TRACE_RT);
		}
		/**
		 * validate the icmp command if configured
		 */
		commandStr = properties.getProperty("icmp.command").toString().trim().replace("HOST","");

		// Validating schedule time
		if (properties.getProperty("icmp.delay") == null) {
			throw new BusnessExceptioin(ErrorType.NULL_ICMP_DELAY);
		}
		try {
			this.delay = Long.valueOf(properties.getProperty("icmp.delay"));
		} catch (NumberFormatException e) {
			throw new BusnessExceptioin(ErrorType.INVALID_ICMP_SHEDULE_TIME);
		}

	}

	@Override
	protected ShedulerbleJob createJob() {
		log.log(Level.FINEST, "new ICMP job create ");
		return new ICMPJob(commandStr);
	}


}
