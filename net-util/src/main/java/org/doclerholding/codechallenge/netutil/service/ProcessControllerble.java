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
package org.doclerholding.codechallenge.netutil.service;

import java.util.concurrent.BlockingQueue;

import org.doclerholding.codechallenge.netutil.model.ErrorMsgDTO;
import org.doclerholding.codechallenge.netutil.model.MessageDTO;
import org.doclerholding.codechallenge.netutil.service.exception.BusnessExceptioin;

public interface ProcessControllerble  {

	void init(final BlockingQueue<ErrorMsgDTO> errorQueue , final BlockingQueue<MessageDTO> sucessrQueue )throws BusnessExceptioin ;

	
	void sheduleHosts()throws BusnessExceptioin ;
	

}
