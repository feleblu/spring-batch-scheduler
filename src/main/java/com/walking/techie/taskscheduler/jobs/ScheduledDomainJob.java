package com.walking.techie.taskscheduler.jobs;

import com.walking.techie.taskscheduler.model.Domain;
import com.walking.techie.taskscheduler.scheduler.RunScheduler;
import com.walking.techie.taskscheduler.writer.CustomWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableBatchProcessing
@Primary
public class ScheduledDomainJob {

  @Autowired
  private JobBuilderFactory jobBuilderFactory;
  @Autowired
  private StepBuilderFactory stepBuilderFactory;

  @Bean
  public Job scheduledJob() {
    return jobBuilderFactory.get("scheduledJob").flow(step1()).end().build();
  }

  @Bean
  public Step step1() {
    return stepBuilderFactory.get("step1").<Domain, Domain>chunk(10)
        .reader(reader()).writer(writer()).build();
  }

  @Bean
  public FlatFileItemReader<Domain> reader() {
    FlatFileItemReader<Domain> reader = new FlatFileItemReader<>();
    reader.setResource(new ClassPathResource("csv/domain-1-03-2017.csv"));
    reader.setLineMapper(new DefaultLineMapper<Domain>() {{
      setLineTokenizer(new DelimitedLineTokenizer() {{
        setNames(new String[]{"id", "domain"});
      }});
      setFieldSetMapper(new BeanWrapperFieldSetMapper<Domain>() {{
        setTargetType(Domain.class);
      }});
    }});
    return reader;
  }

  @Bean
  public CustomWriter writer() {
    CustomWriter writer = new CustomWriter();
    return writer;
  }

  @Bean
  public RunScheduler scheduler() {
    RunScheduler scheduler = new RunScheduler();
    return scheduler;
  }
}
