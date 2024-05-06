package org.addario.reactivegateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.tools.agent.ReactorDebugAgent;

@SpringBootApplication
public class ReactiveGatewayApplication {
    public static void main(String[] args) {
        ReactorDebugAgent.init(); // Dev friendly reactive stack traces
        //BlockHound.install(); // Detect and throw on blocking calls from non-blocking threads
        SpringApplication.run(ReactiveGatewayApplication.class, args);
    }
}
