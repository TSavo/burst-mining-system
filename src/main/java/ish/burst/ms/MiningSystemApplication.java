package ish.burst.ms;

import ish.burst.ms.controllers.websocket.LogWebSocketHandler;
import ish.burst.ms.services.NetStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Created by ihartney on 9/1/14.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableWebSocket
public class MiningSystemApplication implements WebSocketConfigurer {

    @Autowired
    @Value("${plot.generation.threads}")
    int plotGenerationThreads;

    @Autowired
    @Value("${miner.threads}")
    int minerThreads;

    @Autowired
    @Value("${miner.cpu.threads}")
    int minerCpuThreads;


    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(5);
        pool.setMaxPoolSize(10);
        pool.setWaitForTasksToCompleteOnShutdown(false);
        return pool;
    }

    @Bean
    public ThreadPoolTaskExecutor cpuMiningPool() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(minerCpuThreads);
        pool.setMaxPoolSize(minerCpuThreads);
        pool.setWaitForTasksToCompleteOnShutdown(false);
        return pool;
    }

    @Bean
    public ThreadPoolTaskExecutor plotGenerationPool() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(plotGenerationThreads);
        pool.setMaxPoolSize(plotGenerationThreads);
        pool.setWaitForTasksToCompleteOnShutdown(false);
        return pool;
    }

    @Bean
    public ThreadPoolTaskExecutor minerPool() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(minerThreads);
        pool.setMaxPoolSize(minerThreads);
        pool.setWaitForTasksToCompleteOnShutdown(false);
        return pool;
    }

    @Bean
    public ThreadPoolTaskExecutor shareSubmitPool() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(3);
        pool.setMaxPoolSize(3);
        pool.setWaitForTasksToCompleteOnShutdown(false);
        return pool;
    }


    @Bean
    public ThreadPoolTaskScheduler taskScheduler(){
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(5);
        scheduler.setWaitForTasksToCompleteOnShutdown(false);
        return scheduler;
    }

    @Bean
    public NetStateService netStateService(){
        return new NetStateService();
    }

    @Bean
    public LogWebSocketHandler logWebSocketHandler(){
        return new LogWebSocketHandler();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(logWebSocketHandler(), "/logWs").withSockJS();

    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MiningSystemApplication.class, args);
    }





}
