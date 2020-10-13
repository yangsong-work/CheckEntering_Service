package com.fri.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

@Configuration
public class SchedulerConfig implements SchedulingConfigurer {

    private static final Integer POOL_SIZE = 5;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        //配置核心线程数
        threadPoolTaskScheduler.setPoolSize(POOL_SIZE);
        //配置线程池中的线程的名称前缀
        threadPoolTaskScheduler.setThreadNamePrefix("com-fri-scheduler-task");
        threadPoolTaskScheduler.initialize();

        taskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
    }
}
