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
package org.doclerholding.codechallenge.netutil.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

public class SoketHelper {
	
	public StringBuilder ping(final int timeOut, final String hosts) throws IOException, UnknownHostException {
		StringBuilder responseLine = new StringBuilder();
		try (Socket smtpSocket = new Socket() ;
				ObjectOutputStream os = new ObjectOutputStream(smtpSocket.getOutputStream());
				ObjectInputStream is = new ObjectInputStream(smtpSocket.getInputStream());) {
			
			
			SocketAddress sockaddr = new InetSocketAddress(hosts, 80);

			
			smtpSocket.connect(sockaddr, timeOut);
			
			// If everything has been initialized then we want to write some data
			// to the socket we have opened a connection to on port 80
			if (smtpSocket != null && os != null && is != null) {
				// The capital string before each colon has a special meaning to SMTPs
				/* // you may want to read the SMTP specification, RFC1822/3 */
				os.writeBytes("HELO\n");
				os.writeBytes("QUIT");
				// keep on reading from/to the socket 
				// once we received that then we want to break.
			
				
				while (true) {
					
					String temp= is.readUTF();
					if (temp==null) {
						break;
					}
					
					responseLine.append(temp);
				}
			
			}
			
			return responseLine;
		}
	}
}
