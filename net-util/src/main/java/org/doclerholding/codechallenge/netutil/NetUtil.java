
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
 */package org.doclerholding.codechallenge.netutil;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.doclerholding.codechallenge.netutil.service.ProcessFactory;
import org.doclerholding.codechallenge.netutil.service.exception.BusnessExceptioin;
import org.doclerholding.codechallenge.netutil.service.exception.EmptyConfigFileException;
import org.doclerholding.codechallenge.netutil.service.exception.NoConfigFileFoundException;
import org.doclerholding.codechallenge.netutil.util.ConfigReader;

public class NetUtil {
	static Logger  LOG = Logger.getLogger(NetUtil.class.getName());
	
	public static void main(String[] args) {
		
		try {
			LOG.log(Level.INFO, "NetUtil starting");
			/**
			 * read the config file
			 */
			Properties properties = ConfigReader.getInstance().load(args);
			
			/**
			 * create list of process according to configurations
			 */
			
			ProcessFactory.getInstance().initMonitoringProcess(properties);
			/*
			 * start the each ping service in separate Thread
			 */
			ProcessFactory.getInstance().sheduleProcess();
			
		
			
		} catch (NoConfigFileFoundException  |EmptyConfigFileException|BusnessExceptioin  |IOException e ) {
			LOG.log(Level.SEVERE,"", e);
		}
	}

}
