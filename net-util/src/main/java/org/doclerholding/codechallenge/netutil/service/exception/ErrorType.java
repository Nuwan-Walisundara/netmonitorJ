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

public enum ErrorType {
EMPTY_MONITORS("NU-00001","At least one monitoring needed"), 
INVALID_ICMP_COMMAND("NU-00002","Invalid ICMP Command "),
NULL_ICMP_DELAY("NU-00003","ICMP shedule time  must confiured.Lookup key icmp.delay in ms  "),
INVALID_ICMP_SHEDULE_TIME("NU-00004","Invalid sheduled time defind for icmp.delay   "), 
INVALID_TCPIP_TIME_OUT("NU-00005","Invalid sheduled time defind for tcpip.delay   "),
INVALID_TCPIP_SHEDULE_TIME("NU-00006","Invalid sheduled time defind for tcpip.request.timeout   "),
INVALID_TRACERT_SHEDULE_TIME ("NU-00006","Invalid sheduled time defind for tracert.delay  "),
DUPLICATE_TRACE_RT ("NU-00007","Trace route Command already done,duplicate not allowd "),
NULL_TRACE_RT("NU-00008","Trace route Command not initialized yet "),
NULL_TRACE_RT_STR("NU-00009","Null or empty trace command"),
NULL_HOSTS("NU-00010","host cann't be empty"),
PROCESS_ERROR("NU-00011","Ubanble to execute the process"),
TCPIP_FAIL("NU-00012","Fail to ping tcpip"), INVALID_REPORTING_URL("NU-00013","Invalid reposrt posting url provided for reporting.url");
	private String code;
	private String val;

	ErrorType(String code,String val){
		this.code = code;
		this.val = val;
	}
	
	public String getCode() {
		return this.code;
	}
	public String getError() {
		return this.val;
	}
}
