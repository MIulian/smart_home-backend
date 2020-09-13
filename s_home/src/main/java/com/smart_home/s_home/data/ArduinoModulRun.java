package com.smart_home.s_home.data;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import com.smart_home.s_home.service.impl.ModuleImpl;

import arduino.Arduino;


@Configuration
@EnableScheduling
@EnableAsync
public class ArduinoModulRun implements SchedulingConfigurer{

	ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
	Arduino arduino = new Arduino("COM3", 9600);
	
    @Autowired
    Environment env;

    @Bean
    public ModuleImpl myBean() {
        return new ModuleImpl();
    }

    @Bean(destroyMethod = "shutdown")
    public Executor taskExecutor() {
        return Executors.newScheduledThreadPool(10);
    }
    
    @Scheduled(cron="0 0 0 * * *")
    public void refreshBoardList() {
    	myBean().updateListe();
    }
    

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
        //arduino.openConnection();
        taskRegistrar.addTriggerTask(
                new Runnable() {
                    @Override public void run() {
                    	String arduinoCommand = myBean().execute();
						if(!arduinoCommand.equals("")) {
							arduino.serialWrite(arduinoCommand);
						}

                    }
                },
                new Trigger() {
                    @Override public Date nextExecutionTime(TriggerContext triggerContext) {
                        Calendar nextExecutionTime =  new GregorianCalendar();
                        Date lastActualExecutionTime = triggerContext.lastActualExecutionTime();
                        nextExecutionTime.setTime(lastActualExecutionTime != null ? lastActualExecutionTime : new Date());
                        nextExecutionTime.add(Calendar.MILLISECOND, 1000);
                        return nextExecutionTime.getTime();
                    }
                }
        );
    }
    
    
}

