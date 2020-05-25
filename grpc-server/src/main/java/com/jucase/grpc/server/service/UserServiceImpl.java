package com.jucase.grpc.server.service;

import com.jucase.grpc.lib.dto.User;
import com.jucase.grpc.server.repository.UserRepository;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MongoClient mongoClient;


    public void saveUser(User user) {
        MongoDatabase database = mongoClient.getDatabase("grpctestdb");
        MongoCollection<Document> collection = database.getCollection("user");
        collection.insertOne(new Document()
                    .append("firstName",user.getFirstName())
                    .append("lastName",user.getLastName()));
    }

    private UserDetails toUserDetails(User user) {
        UserDetails userDetails = new UserDetails();
        userDetails.setFirstName(user.getFirstName());
        userDetails.setLastName(user.getLastName());
        return userDetails;
    }
}
