package com.danech.config;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.danech.listener.MyBatchListener;
import com.danech.model.Product;

/**
 * 
 * @author dev77
 *
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig {
	
	@Bean
	public ItemReader<Product> reader(){
		FlatFileItemReader<Product> reader = new FlatFileItemReader<>();
		reader.setResource(new ClassPathResource("products.csv"));
		reader.setLineMapper(new DefaultLineMapper<Product>() {{
			setLineTokenizer(new DelimitedLineTokenizer() {{
				setDelimiter(DELIMITER_COMMA);
				setNames("productCode","variantSKU"
						,"productName","parentSKU","category","subCategory",
						"productLine","totalQuantity","mrp","webMrp","color","size","productDescription");
				
			}});
			setFieldSetMapper(new BeanWrapperFieldSetMapper<Product>() {{
				setTargetType(Product.class);
			}});
		}});
		return reader;
	}
	
	@Autowired
	private EntityManagerFactory emf;
	
	@Bean
	public ItemWriter<Product> writer(){
		JpaItemWriter<Product> writer = new JpaItemWriter<>();
		writer.setEntityManagerFactory(emf);
		return writer;
	}
	
	@Bean
	public JobExecutionListener listener() {
		return new MyBatchListener();
	}
	
	@Bean
	public ItemProcessor<Product, Product> processor(){
		return (p)->{
				p.setProductName("Batch :: "+p.getProductName());
				return p;
			};
	}
	
	@Autowired
	private StepBuilderFactory stepFactory;
	
	public Step stepA() {
		return stepFactory
				.get("stepA")
				.<Product,Product>chunk(10000)
				.reader(reader())
				.writer(writer())
				.processor(processor())
				.build();
	}
	
	@Autowired
	private JobBuilderFactory jobFactory;
	
	@Bean
	public Job job() {
		return jobFactory
				.get("jobA")
				.listener(listener())
				.start(stepA())
				.build();
	}
}