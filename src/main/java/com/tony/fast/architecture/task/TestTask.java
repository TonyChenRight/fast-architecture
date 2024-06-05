package com.tony.fast.architecture.task;

import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestTask {


    @Scheduled(cron = "0/15 * * * * ?")
    @SchedulerLock(name = "TestTask.execute",lockAtMostFor = "PT1M", lockAtLeastFor = "PT1M")
    public void execute() {
        try {
            log.info("TestTask.execute start....");
            // 模拟耗时操作
            Thread.sleep(5000);
            log.info("TestTask.execute end....");
        }catch (Exception e) {
            log.error("execute error: ", e);
        }
    }
}