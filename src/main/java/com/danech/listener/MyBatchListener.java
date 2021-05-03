package com.danech.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
/**
 * 
 * @author dev77
 *
 */
public class MyBatchListener implements JobExecutionListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyBatchListener.class);
	
	@Override
	public void beforeJob(JobExecution jobExecution) {
		LOGGER.info("MyBatchListener.beforeJob()");
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		LOGGER.info("MyBatchListener.afterJob()");
	}

}
