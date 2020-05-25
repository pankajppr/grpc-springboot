package com.jucase.grpc.server.repository;

import com.jucase.grpc.lib.dto.User;
import com.jucase.grpc.server.service.UserDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserDetails, String> {

}
