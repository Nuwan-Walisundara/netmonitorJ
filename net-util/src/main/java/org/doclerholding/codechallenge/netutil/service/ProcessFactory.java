package org.doclerholding.codechallenge.netutil.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.doclerholding.codechallenge.netutil.model.ErrorMsgDTO;
import org.doclerholding.codechallenge.netutil.model.MessageDTO;
import org.doclerholding.codechallenge.netutil.service.exception.BusnessExceptioin;
import org.doclerholding.codechallenge.netutil.service.exception.ErrorType;
import org.doclerholding.codechallenge.netutil.service.exception.InvalidOparationException;
import org.doclerholding.codechallenge.netutil.service.icmp.ICMPController;
import org.doclerholding.codechallenge.netutil.service.reporting.ErrorMsgProcessor;
import org.doclerholding.codechallenge.netutil.service.reporting.QueueConsumer;
import org.doclerholding.codechallenge.netutil.service.reporting.MessageProcessor;
import org.doclerholding.codechallenge.netutil.service.tcpip.TCPIPController;
import org.doclerholding.codechallenge.netutil.service.trcert.TraceRTController;
import org.doclerholding.codechallenge.netutil.util.MonitoringServices;

public final class ProcessFactory {
	private ProcessFactory() {
	}

	private static ProcessFactory instance;
	private List<ProcessControllerble> list = new ArrayList<>();
	private final BlockingQueue<ErrorMsgDTO> errorQueue = new LinkedBlockingQueue<ErrorMsgDTO>();
	private final BlockingQueue<MessageDTO> messageQueue = new LinkedBlockingQueue<MessageDTO>();
	private String reportSendingUrl;
	private boolean validationSucsess;
	private List<MonitoringServices> enabled = new ArrayList<>();
	private List<MonitoringServices> desabled = new ArrayList<>();

	public static synchronized ProcessFactory getInstance() {
		if (instance == null) {
			instance = new ProcessFactory();
		}
		return instance;
	}

	public List<ProcessControllerble> initMonitoringProcess(final Properties properties) throws BusnessExceptioin {

		// If ICMP enabled
		if (Boolean.valueOf(properties.getProperty("icmp.enable"))) {
			list.add(new ICMPController(properties));
			enabled.add(MonitoringServices.ICMP);
		} else {
			desabled.add(MonitoringServices.ICMP);
		}

		// If ICMP enabled
		if (Boolean.valueOf(properties.getProperty("tcpip.enable"))) {
			list.add(new TCPIPController(properties));
			enabled.add(MonitoringServices.TPC_IP);
		} else {
			desabled.add(MonitoringServices.TPC_IP);
		}

		// if trace route enabled
		if (Boolean.valueOf(properties.getProperty("traceroute.enable"))) {
			list.add(new TraceRTController(properties));
			enabled.add(MonitoringServices.TRACERT);
		} else {
			desabled.add(MonitoringServices.TRACERT);
		}

		// Reading the reporting url
		reportSendingUrl = properties.getProperty("reporting.url");
		if (reportSendingUrl == null || reportSendingUrl.isEmpty()) {
			throw new BusnessExceptioin(ErrorType.INVALID_REPORTING_URL);
		}
		/**
		 * if no network monitoring disabled system wont start
		 */
		if (list.isEmpty()) {
			throw new BusnessExceptioin(ErrorType.NULL_HOSTS);
		}

		for (ProcessControllerble controller : list) {

			// If any validation fails service would not start
			controller.init(errorQueue, messageQueue);

		}
		/**
		 * since all the initialization success ,mark as success
		 */
		this.validationSucsess = true;
		return list;
	}

	public void sheduleProcess() throws BusnessExceptioin {

		/**
		 * if no network monitoring disabled system wont start
		 */
		if (!this.validationSucsess) {
			throw new InvalidOparationException("Initial validation Fails,Unable to start the shedule process");
		}

		/*
		 * start error capturing and processing servies
		 */
		startErrorProcessing();

		/*
		 * start Success message processor and the listner
		 */
		startMessageProcessing();

		for (ProcessControllerble item : list) {
			item.sheduleHosts();
		}

	}

	/**
	 * Strart error process and error listner
	 */
	private void startErrorProcessing() {

		ErrorMsgProcessor errorProcess = ErrorMsgProcessor.getInstance(reportSendingUrl);
		errorProcess.setDesableddMonitoringList(desabled);
		errorProcess.setEnabledMonitoringList(enabled);
		new Thread(new QueueConsumer<ErrorMsgDTO>(errorQueue, errorProcess)).start();
	}

	/**
	 * Strart All message process and message listners
	 */
	private void startMessageProcessing() {

		MessageProcessor successMsgProcess = MessageProcessor.getInstance();
		new Thread(new QueueConsumer<MessageDTO>(messageQueue, successMsgProcess)).start();
		// new QueueConsumer<SuccessMessage>(sucessrQueue,successMsgProcess).run();
	}

}
