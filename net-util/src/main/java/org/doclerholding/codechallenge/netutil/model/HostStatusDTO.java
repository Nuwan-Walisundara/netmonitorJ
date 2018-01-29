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

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.doclerholding.codechallenge.netutil.util.MonitoringServices;

public class HostStatusDTO {
	private final String host;
	/**
	 * As there are multiple threads try to monitor the message for the host it
	 * needs to use thread safe ConcurrentMap
	 */
	private final ConcurrentMap<MonitoringServices, MessageDTO> messageMap = new ConcurrentHashMap<>();

	public HostStatusDTO(final String host) {
		this.host = host;
	}

	public void updateMessage(MessageDTO msg) {
		messageMap.put(msg.getService(), msg);
	}


	public String getHost() {
		return host;
	}

	public boolean contain(MonitoringServices key) {
		return messageMap.containsKey(key);
	}

	public MessageDTO getMessage(MonitoringServices key) {
		if (messageMap.containsKey(key)) {
			return messageMap.get(key);

		} else {
			return null;
		}
	}
}
