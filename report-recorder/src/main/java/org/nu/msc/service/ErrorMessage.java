package org.nu.msc.service;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorMessage {

	private String host;
	 @JsonProperty
	public String getHost() {
		return this.host;
	}
	 @JsonProperty
	public void setHost(String host) {
		this.host = host;
	}

	private String icmp_ping;
	 @JsonProperty
	public String getIcmpPing() {
		return this.icmp_ping;
	}
	 @JsonProperty
	public void setIcmpPing(String icmp_ping) {
		this.icmp_ping = icmp_ping;
	}
	private String tcp_ping;
	 @JsonProperty
	public String getTcpPing() {
		return this.tcp_ping;
	}
	 @JsonProperty
	public void setTcpPing(String tcp_ping) {
		this.tcp_ping = tcp_ping;
	}

	private String trace;
	 @JsonProperty
	public String getTrace() {
		return this.trace;
	}
	 @JsonProperty
	public void setTrace(String trace) {
		this.trace = trace;
	}

	@Override
	public String toString() {
		return "ErrorMessage [host=" + host + ", icmp_ping=" + icmp_ping + ", tcp_ping=" + tcp_ping + ", trace=" + trace
				+ "]";
	}
	
	
	
}
