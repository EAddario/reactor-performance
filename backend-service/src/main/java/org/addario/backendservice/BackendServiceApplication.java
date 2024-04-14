package org.addario.backendservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.blockhound.BlockHound;
import reactor.tools.agent.ReactorDebugAgent;

@SpringBootApplication
public class BackendServiceApplication {
    public static void main(String[] args) {
        System.setProperty("reactor.schedulers.defaultBoundedElasticOnVirtualThreads", "true");
        ReactorDebugAgent.init(); // Dev friendly reactive stack traces
        BlockHound.install(); // Detect and throw on blocking calls from non-blocking threads
        SpringApplication.run(BackendServiceApplication.class, args);
    }
}
