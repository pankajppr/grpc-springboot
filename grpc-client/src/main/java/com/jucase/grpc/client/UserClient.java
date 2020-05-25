package com.jucase.grpc.client;

import com.google.protobuf.ByteString;
import com.jucase.grpc.UserServiceGrpc;
import com.jucase.grpc.UserServiceOuterClass;
import com.jucase.grpc.lib.dto.User;
import com.jucase.grpc.lib.util.GrpcDtoUtil;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UserClient {

    public String callUnaryUser(User user) throws IOException, ClassNotFoundException {
        // Channel is the abstraction to connect to a service endpoint
        // Let's use plaintext communication because we don't have certs
        final ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:5656")
                .usePlaintext()
                .build();

        // It is up to the client to determine whether to block the call
        // Here we create a blocking stub, but an async stub,
        // or an async stub with Future are always possible.
        UserServiceGrpc.UserServiceBlockingStub stub = UserServiceGrpc.newBlockingStub(channel);
        UserServiceOuterClass.UserRequest request =
                UserServiceOuterClass.UserRequest.newBuilder()
                        .setUser(ByteString.copyFrom(GrpcDtoUtil.getBytes(user)))
                        .build();

        // Finally, make the call using the stub
        UserServiceOuterClass.UserResponse response =  stub.user(request);

        // A Channel should be shutdown before stopping the process.
        channel.shutdownNow();

        return response.getUser().toString();
    }

}
