package org.doclerholding.codechallenge.netuitl.util;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

import org.doclerholding.codechallenge.netutil.service.exception.BusnessExceptioin;
import org.doclerholding.codechallenge.netutil.util.HTTPHelper;
import org.doclerholding.codechallenge.netutil.util.ProcessUtil;
import org.junit.Test;

public class ProcessUtilTest {

	@Test
	public void test() {
		ProcessUtil pUtil =  new ProcessUtil();
		//"ping -c 5 13.58.130.22
		StringBuilder outPut;
		try {
			outPut = pUtil.executeProcessAndGetOutputAsStringList("ping -c 5 google.com");
			System.out.println(outPut.toString());
		} catch (BusnessExceptioin e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void test2() {
		ProcessUtil pUtil =  new ProcessUtil();
		//"ping -c 5 13.58.130.22
		StringBuilder outPut;
		try {
			outPut = pUtil.executeProcessAndGetOutputAsStringList("tracert google.com");
			System.out.println(outPut.toString());
		} catch (BusnessExceptioin e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void test3() {

		try {
			JsonObjectBuilder objectBuilder = Json.createObjectBuilder().add("host", "test")
					.add("icmp_ping", "icmp_ping message").add("tcp_ping", "tcp_ping message")
					.add("trace", "trace results");

			HTTPHelper x = new HTTPHelper("http://localhost:8181/errorrport");
			x.sendPOST(objectBuilder.build());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
