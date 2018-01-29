package org.doclerholding.codechallenge.netutil.service.icmp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.doclerholding.codechallenge.netutil.service.exception.BusnessExceptioin;
import org.doclerholding.codechallenge.netutil.service.exception.ErrorType;

class Command {
	private static Command instance;

	private static final String DEFAULT = "ping -c 5";
	private static final String REGEX = "(ping (-[aAbBdDfhLnOqrRUvV]+){0,1}(-c ?\\d{1,3}){0,1} ?(-t ?\\d{1,3}){0,1} ?)(HOST)";
	private String configured = null;

	private Command() {

	}

	public static Command getInstance() {
		if (instance == null) {
			instance = new Command();
		}
		return instance;
	}

	// If command is given it will validate else continue with the default command
	public void validate(final String commandString) throws BusnessExceptioin {
		if (commandString != null || commandString.trim().length() < 0) {
			Pattern p = Pattern.compile(REGEX);
			Matcher m = p.matcher(commandString);
			/**
			 * if given command match with the defined regex pattern considerd as valid
			 * command extract the group 1 as command
			 */
			if (m.matches()) {
				this.configured = m.group(1);
				throw new BusnessExceptioin(ErrorType.INVALID_ICMP_COMMAND,
						" supported command should compile with" + REGEX);
			}
		}
	}

	public String getCommandString() {
		if (this.configured != null) {
			return this.configured;
		} else {
			return DEFAULT;
		}
	}
}
