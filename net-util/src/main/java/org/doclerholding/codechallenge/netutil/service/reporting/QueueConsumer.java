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
package org.doclerholding.codechallenge.netutil.service.reporting;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.doclerholding.codechallenge.netutil.model.HostStatusDTO;

public class QueueConsumer<E> implements Runnable{
	private  final BlockingQueue<E> queue  ;
	private  final MsgProcessorble<E> msgProcess ;
	private final ConcurrentMap<String,HostStatusDTO> concurrentMap;
	private ExecutorService executorService;
	
	
	public QueueConsumer(BlockingQueue<E> queue,MsgProcessorble<E> msgProcess  ) {
		this.queue = queue;
		this.msgProcess=msgProcess;
		this.concurrentMap  = new ConcurrentHashMap<String,HostStatusDTO>();
		this.executorService = Executors.newCachedThreadPool();
	}
	@Override
	public void run() {
		while(true) {
			try {
				
				E e= queue.take();
			
				msgProcess.process(e);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
