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

public class NoConfigFileFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7548678677541900487L;
	
	public NoConfigFileFoundException(){
		super("No Configuration file Found");
	}
}
