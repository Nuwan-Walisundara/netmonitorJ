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
package org.doclerholding.codechallenge.netutil.service.exception;

public class InvalidHostException extends BusnessExceptioin {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3695080499085058585L;
	public InvalidHostException(ErrorType error) {
		super(error);
		// TODO Auto-generated constructor stub
	}
	public InvalidHostException() {
		super("Invalid Host ");
		
	}
}
