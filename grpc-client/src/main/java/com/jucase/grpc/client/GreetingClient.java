package com.jucase.grpc.client;

import com.jucase.grpc.GreetingServiceGrpc;
import com.jucase.grpc.GreetingServiceOuterClass;

import com.jucase.grpc.lib.dto.User;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Component;

@Component
public class GreetingClient {

    public String callUnaryGreet(User user){
        // Channel is the abstraction to connect to a service endpoint
        // Let's use plaintext communication because we don't have certs
        final ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:8080")
                .usePlaintext()
                .build();

        // It is up to the client to determine whether to block the call
        // Here we create a blocking stub, but an async stub,
        // or an async stub with Future are always possible.
        GreetingServiceGrpc.GreetingServiceBlockingStub stub = GreetingServiceGrpc.newBlockingStub(channel);
        GreetingServiceOuterClass.HelloRequest request =
                GreetingServiceOuterClass.HelloRequest.newBuilder()
                        .setName(user.getFirstName()+" "+ user.getLastName())
                        .build();

        // Finally, make the call using the stub
        GreetingServiceOuterClass.HelloResponse response =
                stub.greeting(request);

        System.out.println(response);

        // A Channel should be shutdown before stopping the process.
        channel.shutdownNow();

        return response.getGreeting();
    }

}
