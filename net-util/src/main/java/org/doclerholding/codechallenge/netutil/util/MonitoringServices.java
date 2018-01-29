package org.doclerholding.codechallenge.netutil.util;

public enum MonitoringServices {
	ICMP("icmp_ping"), TPC_IP("tcp_ping"), TRACERT("trace");
	private String name;

	MonitoringServices(String name) {
		this.name = name;
	}
	
	public String getKey() {
		return this.name;
	}
}
