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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Properties;

import org.doclerholding.codechallenge.netutil.service.exception.EmptyConfigFileException;
import org.doclerholding.codechallenge.netutil.service.exception.NoConfigFileFoundException;


public class ConfigReader {

	private static ConfigReader instance;

	private ConfigReader() {
	}

	public static ConfigReader getInstance() {
		if (instance == null) {
			instance = new ConfigReader();
		}
		return instance;
	}

	public Properties load(final String [] filePath) throws NoConfigFileFoundException ,EmptyConfigFileException,IOException{
		Properties props = new Properties();
		File configFile;
		/**
		 * check for customized configuration file 
		 */
		if (filePath .length>0 && !filePath[0].isEmpty()) {
			System.out.println("File path "+filePath[0] );
			
			configFile =  Paths.get(filePath[0] ).toAbsolutePath().toFile();
		} else {//Default configuration file
			configFile= Paths.get("config/config.properties").toAbsolutePath().toFile();
		}
		//No configuration file found
		if(configFile==null) {
			throw new NoConfigFileFoundException();
		}	//if configuration file is emptuy
		
		InputStream strem = new FileInputStream(configFile);
		props.load(strem);
		
		if(props.isEmpty()) {
			throw new EmptyConfigFileException();
		}
		return props;
	}

}
