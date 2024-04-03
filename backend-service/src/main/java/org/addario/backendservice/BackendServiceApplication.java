package org.addario.backendservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.tools.agent.ReactorDebugAgent;

@SpringBootApplication
public class BackendServiceApplication {
    public static void main(String[] args) {
        System.setProperty("reactor.schedulers.defaultBoundedElasticOnVirtualThreads", "true");
        ReactorDebugAgent.init();
        SpringApplication.run(BackendServiceApplication.class, args);
    }
}
