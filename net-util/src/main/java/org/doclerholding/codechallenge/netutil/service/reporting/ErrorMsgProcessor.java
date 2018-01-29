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
package org.doclerholding.codechallenge.netutil.service.reporting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.doclerholding.codechallenge.netutil.model.ErrorMsgDTO;
import org.doclerholding.codechallenge.netutil.model.HostStatusDTO;
import org.doclerholding.codechallenge.netutil.service.exception.InvalidHostException;
import org.doclerholding.codechallenge.netutil.util.HTTPHelper;
import org.doclerholding.codechallenge.netutil.util.MonitoringServices;

public class ErrorMsgProcessor implements MsgProcessorble<ErrorMsgDTO> {

	private final String postUrl;
	private final ExecutorService executorService;
	private final Logger log;
	private static ErrorMsgProcessor instance;
	private List<MonitoringServices> enabled = new ArrayList<>();;
	private List<MonitoringServices> desabled = new ArrayList<>();;

	public void setEnabledMonitoringList(List<MonitoringServices> enabled) {
		if (enabled != null && !enabled.isEmpty()) {
			this.enabled.addAll(enabled);
		}

	}

	public void setDesableddMonitoringList(List<MonitoringServices> desabled) {
		if (desabled != null && !desabled.isEmpty()) {
			this.desabled.addAll(desabled);
		}

	}

	private ErrorMsgProcessor(final String postUrl) {
		this.postUrl = postUrl;
		executorService = Executors.newCachedThreadPool();
		log = Logger.getLogger(ErrorMsgDTO.class.getName());
	}

	public static ErrorMsgProcessor getInstance(final String reportSendingUrl) {

		if (instance == null) {
			instance = new ErrorMsgProcessor(reportSendingUrl);
		}

		return instance;
	}

	@Override
	public void process(ErrorMsgDTO e) {

		executorService.execute(new Runnable() {

			@Override
			public void run() {
				HostStatusDTO hostStatus = null;
				JsonObject monitoringStatus = null;

				try {
					hostStatus = MessageProcessor.getInstance().getResult(e.getHost());
					monitoringStatus = buildJsonMsg(hostStatus);
				} catch (InvalidHostException ex) {
					log.fine("No host object found :" + e.getHost());
					monitoringStatus = buildJsonMsg(e.getHost(), e);

				}
				log.info("monitoringStatus" + monitoringStatus);

				try {
					new HTTPHelper(postUrl).sendPOST(monitoringStatus);
					// new ApacheHttpHelper(postUrl).doPost();

				} catch (IOException e1) {
					log.log(Level.SEVERE, "IOException", e1);
				}

			}
		}

		);

	}

	private JsonObject buildJsonMsg(HostStatusDTO dto) {

		JsonObjectBuilder objectBuilder = Json.createObjectBuilder().add("host", dto.getHost());

		desabled.forEach(item -> {
			objectBuilder.add(item.getKey(), "Service disabled");

		});

		enabled.forEach(itme -> {
			if (dto.contain(itme)) {
				objectBuilder.add(itme.getKey(), dto.getMessage(itme).getMessage());
			} else {
				objectBuilder.add(itme.getKey(), "Service not yet monitord");
			}
		});
		return objectBuilder.build();

	}

	private JsonObject buildJsonMsg(final String host, ErrorMsgDTO e) {

		JsonObjectBuilder objectBuilder = Json.createObjectBuilder().add("host", host);

		desabled.forEach(item -> {
			objectBuilder.add(item.getKey(), "Service disabled");

		});

		enabled.forEach(itme -> {
			if (enabled.contains(e.getService())) {
				System.out.println(e.toString());
				objectBuilder.add(itme.getKey(), e.getMessage());
			} else {
				objectBuilder.add(itme.getKey(), "Service not yet monitord");
			}
		});
		return objectBuilder.build();

	}

	private byte[] buildByteArrayMsg(HostStatusDTO dto) {

		StringBuilder out = new StringBuilder();

		out.append("{").append("host").append(":").append(dto.getHost()).append(",");
		

		desabled.forEach(item -> {
			out.append(item.getKey()).append(":").append("Service disabled").append(",");
		});

		enabled.forEach(itme -> {
			if (dto.contain(itme)) {
				out.append(itme.getKey()).append(":").append( dto.getMessage(itme).getMessage()).append(",");
			} else {
				out.append(itme.getKey()).append(":").append( "Service not yet monitord").append(",");
			}
		});
		return out.toString().getBytes();

	}

	private JsonObject buildByteArrayMsg(final String host, ErrorMsgDTO e) {

		JsonObjectBuilder objectBuilder = Json.createObjectBuilder().add("host", host);

		desabled.forEach(item -> {
			objectBuilder.add(item.getKey(), "Service disabled");

		});

		enabled.forEach(itme -> {
			if (enabled.contains(e.getService())) {
				System.out.println(e.toString());
				objectBuilder.add(itme.getKey(), e.getMessage());
			} else {
				objectBuilder.add(itme.getKey(), "Service not yet monitord");
			}
		});
		return objectBuilder.build();

	}

}
