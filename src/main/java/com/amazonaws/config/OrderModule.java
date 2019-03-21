package com.amazonaws.config;

import com.amazonaws.dao.OrderFileDao;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.fasterxml.jackson.databind.ObjectMapper;
import dagger.Module;
import dagger.Provides;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Optional;

@Module
public class OrderModule {

    public static final String TABLE_NAME = "orderfiles";

    @Singleton
    @Provides
    @Named("tableName")
    String tableName() {
        return Optional.ofNullable(System.getenv("TABLE_NAME")).orElse(TABLE_NAME);
    }

    @Singleton
    @Provides
    DynamoDB dynamoDb() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        return new DynamoDB(client);
    }

    @Singleton
    @Provides
    ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Singleton
    @Provides
    public OrderFileDao orderDao(DynamoDB dynamoDb, @Named("tableName") String tableName) {
        return new OrderFileDao(dynamoDb, tableName);
    }
}
