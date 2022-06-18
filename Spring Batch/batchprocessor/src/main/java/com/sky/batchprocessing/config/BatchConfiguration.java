package com.sky.batchprocessing.config;

import java.util.concurrent.Flow.Processor;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.sky.batchprocessing.model.InProduct;
import com.sky.batchprocessing.model.OutProduct;
import com.sky.batchprocessing.model.Person;
import com.sky.batchprocessing.processor.PersonItemProcessor;
import com.sky.batchprocessing.processor.ProductProcessor;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {


	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	// end::setup[]

	@Bean
	public FlatFileItemReader<Person> reader() {
		return new FlatFileItemReaderBuilder<Person>().name("personItemReader")
				.resource(new ClassPathResource("sample-data.csv")).delimited()
				.names(new String[] { "firstName", "lastName" })
				.fieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {
					{
						setTargetType(Person.class);
					}
				}).build();
	}

	@Bean
	public PersonItemProcessor processor() {
		return new PersonItemProcessor();
	}

	@Bean
	public JdbcBatchItemWriter<Person> writer(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<Person>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Person>())
				.sql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)").dataSource(dataSource)
				.build();
	}

	@Bean
	public Step step1(JdbcBatchItemWriter<Person> writer) {
		return stepBuilderFactory.get("step1").<Person, Person>chunk(10).reader(reader()).processor(processor())
				.writer(writer).build();
	}

	@Bean
	public Job importUserJob(@Qualifier("step1") Step step1 , @Qualifier("step2") Step step2) {
		return jobBuilderFactory.get("importUserJob")
			.incrementer(new RunIdIncrementer())
		
			.flow(step1)
			.next(step2)
			.end()
			.build();
	}
	@Bean
	public FlatFileItemReader<InProduct> productReader() {
		return new FlatFileItemReaderBuilder<InProduct>().name("productItemReader").resource(new ClassPathResource("product.csv"))
				.delimited().names(new String[] { "name", "price" })
				.fieldSetMapper(new BeanWrapperFieldSetMapper<InProduct>() {
					{
						setTargetType(InProduct.class);
					}
				}).build();
	}

	@Bean
	public ProductProcessor productProcessor() {
		return new ProductProcessor();
	}

	@Bean
	public JdbcBatchItemWriter<OutProduct> productWriter(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<OutProduct>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<OutProduct>())
				.sql("INSERT INTO product (name, price,disPrice) VALUES (:name, :price,:disPrice)")
				.dataSource(dataSource).build();
	}

	@Bean
	public Step step2(JdbcBatchItemWriter<OutProduct> productWriter) {
		return stepBuilderFactory.get("step2").<InProduct, OutProduct>chunk(10).reader(productReader())
				.processor(productProcessor()).writer(productWriter).build();
	}

}
