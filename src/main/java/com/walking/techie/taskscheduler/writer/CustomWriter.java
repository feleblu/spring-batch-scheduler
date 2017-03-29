package com.walking.techie.taskscheduler.writer;


import com.walking.techie.taskscheduler.model.Domain;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;

@Slf4j
public class CustomWriter implements ItemWriter<Domain> {

  @Override
  public void write(List<? extends Domain> items) throws Exception {
    log.info("writer ....... " + items.size());
    for (Domain domain : items) {
      log.info(domain + "\n");
    }
  }
}
