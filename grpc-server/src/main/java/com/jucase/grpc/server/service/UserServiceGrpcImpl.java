package com.jucase.grpc.server.service;

import com.jucase.grpc.GreetingServiceOuterClass;
import com.jucase.grpc.UserServiceGrpc;
import com.jucase.grpc.UserServiceOuterClass;
import com.jucase.grpc.lib.dto.User;
import com.jucase.grpc.lib.util.GrpcDtoUtil;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserServiceGrpcImpl extends UserServiceGrpc.UserServiceImplBase {

    @Autowired
    UserServiceImpl userService;

    public UserServiceGrpcImpl(){

    }

    @Override
    public void user(UserServiceOuterClass.UserRequest request, StreamObserver<UserServiceOuterClass.UserResponse> responseObserver) {
        // HelloRequest has toString auto-generated.
        User user = null;
        try {
            user  = GrpcDtoUtil.deserialize(request.getUser().toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        userService.saveUser(user);
        System.out.println(user);


        // You must use a builder to construct a new Protobuffer object
        UserServiceOuterClass.UserResponse response = UserServiceOuterClass.UserResponse.newBuilder()
                .setUser( request.getUser())
                .build();

        // Use responseObserver to send a single response back
        responseObserver.onNext(response);

        // When you are done, you must call onCompleted.
        responseObserver.onCompleted();
    }
}
