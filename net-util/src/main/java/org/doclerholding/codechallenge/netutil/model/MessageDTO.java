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

public class MessageDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -750995595222846327L;
	/**
	 * 
	 */
	private String host;
	private MonitoringServices service;
	private long time;
	private String message;

	public MessageDTO(final String host, final MonitoringServices service, final String msg) {
		this.host = host;
		this.service = service;
		time = System.currentTimeMillis();

		this.message = msg;

	}

	public String getHost() {
		return host;
	}

	public MonitoringServices getService() {
		return service;
	}

	public long getTime() {
		return time;
	}

	public String getMessage() {
		return this.message;
	}

	@Override
	public String toString() {
		return "MessageDTO [host=" + host + ", service=" + service + ", time=" + time + ", message=" + message + "]";
	}


	
}
