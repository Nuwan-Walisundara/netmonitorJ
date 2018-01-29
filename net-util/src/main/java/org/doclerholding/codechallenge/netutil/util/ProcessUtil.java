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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.doclerholding.codechallenge.netutil.service.exception.BusnessExceptioin;
import org.doclerholding.codechallenge.netutil.service.exception.ErrorType;

public class ProcessUtil {

	/**
	 * Executes the given command Returns the output of the process as a List of
	 * strings
	 * 
	 * @param command
	 * @return List<String>
	 */
	public static StringBuilder executeProcessAndGetOutputAsStringList(final String command) throws BusnessExceptioin {

		// delegate
		final Runtime runtime = Runtime.getRuntime();
		Process process;
		try {
			process = runtime.exec(command);

			process.waitFor();
		} catch (IOException | InterruptedException e) {
			throw new BusnessExceptioin(e);
		}
		// extract output
		BufferedReader inputStream = new BufferedReader(new InputStreamReader(process.getInputStream()));

		if (process.exitValue() != 0) {

			String errorMsg = getStringFromInputStream(process.getErrorStream());
			throw new BusnessExceptioin(ErrorType.PROCESS_ERROR, errorMsg);
		}
		StringBuilder outPut = new StringBuilder();
		String string = null;
		try {
			while ((string = inputStream.readLine()) != null) {

				// track
				outPut.append(string);
			}
		} catch (IOException e) {
			throw new BusnessExceptioin(e);
		}

		// done
		return outPut;

	}

	// convert InputStream to String
	private static String getStringFromInputStream(InputStream is) {
		
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if ( sb.toString().isEmpty()) {
			return "Error occurd while executing the process ";
		}

		return sb.toString();

	}

}