package org.doclerholding.codechallenge.netutil.service.exception;

public class EmptyConfigFileException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -567732716897300160L;
	public EmptyConfigFileException() {
		super("empty Configuration file");
	}
}
