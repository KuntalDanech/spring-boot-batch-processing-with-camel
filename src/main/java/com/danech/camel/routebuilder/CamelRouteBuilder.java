package com.danech.camel.routebuilder;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CamelRouteBuilder extends RouteBuilder {

	@Autowired
	private JobLauncher launcher;
	
	@Autowired
	private Job job;
	
	@Override
	public void configure() throws Exception {
		
		// Apache camel will listen to file
		from("{{my.file.url}}")
		.routeId("route-1")
		.log("started route -1 ")
		.unmarshal()
		.csv()
		.process(exchange->{
			final String fileName = (String) exchange.getIn().getHeader(Exchange.FILE_NAME);
			log.info("File name : {}",fileName);
			@SuppressWarnings("unchecked")
			List<List<String>> data = (List<List<String>>) exchange.getIn().getMandatoryBody();
			log.info("My Data : {}",data);
			
			log.info("My Job is running");
			
			launcher.run(job, new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters());
		})
		.end();
		
	}

}
