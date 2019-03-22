package com.amazonaws.config;

import com.amazonaws.dao.S3FileDao;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class S3FileModule {

    @Singleton
    @Provides
    DynamoDBMapper dynamoDb() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        return new DynamoDBMapper(client);
    }

    @Singleton
    @Provides
    ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Singleton
    @Provides
    public S3FileDao s3FileDao(DynamoDBMapper dynamoDb) {
        return new S3FileDao(dynamoDb);
    }
}
