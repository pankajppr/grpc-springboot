package com.jucase.grpc.server;

import com.jucase.grpc.server.service.GreetingServiceImpl;
import com.jucase.grpc.server.service.UserServiceGrpcImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.annotation.PostConstruct;
import java.io.IOException;

@SpringBootApplication(scanBasePackages = "com.jucase.grpc.*")
@ComponentScan({"com.jucase.grpc.*"})
@EnableMongoRepositories(basePackages = "com.jucase.grpc.server.repository")
public class GrpcServerApplication  extends SpringBootServletInitializer {

    static Log logger = LogFactory.getLog(GrpcServerApplication.class);

    @Autowired
    UserServiceGrpcImpl userServiceGrpc;

    public static void main(String[] args) {
        SpringApplication.run(GrpcServerApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(GrpcServerApplication.class);
    }

    @PostConstruct
    public void startGrpcServer() throws IOException, InterruptedException {
        // Create a new server to listen on port 5656
        Server server = ServerBuilder.forPort(5656)
                .addService(new GreetingServiceImpl())
                .addService(userServiceGrpc)
                .build();
        // Start the server
        server.start();
        // Server threads are running in the background.
        logger.info("GRPC Server started on port: "+ server.getPort());
        // Don't exit the main thread. Wait until server is terminated.
        server.awaitTermination();
    }
}
