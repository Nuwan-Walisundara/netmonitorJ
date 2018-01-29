
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
package org.doclerholding.codechallenge.netutil.model;

import java.io.Serializable;

import org.doclerholding.codechallenge.netutil.util.MonitoringServices;

public class ErrorMsgDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3839526138507530315L;
	private String host;
	private MonitoringServices service;
	private long time;
	private String msg;
	

	public ErrorMsgDTO(final String host,final MonitoringServices service,final String msg) {
		this.time = System.currentTimeMillis();
		this.host = host;
		this.service =service;
		this.msg =msg;
		
	}

	public String getHost() {
		return host;
	}

 
	public MonitoringServices getService() {
		return service;
	}

	public String getMessage() {
		return this.msg;
	}
 
	public long getTime() {
		return time;
	}

	@Override
	public String toString() {
		return "ErrorMessage [host=" + host + ", service=" + service + ", time=" + time + ", msg=" + msg + "]";
	}
	
	

}
