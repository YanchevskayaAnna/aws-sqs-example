package com.amazonaws.dao;

import com.amazonaws.exception.CouldNotCreateOrderException;
import com.amazonaws.model.DenamoDBFile;
import com.amazonaws.model.request.S3Body;
import com.amazonaws.model.request.S3File;
import com.amazonaws.model.request.S3Object;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public class S3FileDao {

    private final DynamoDBMapper dynamoDbMapper;

    public S3FileDao(final DynamoDBMapper dynamoDbMapper) {
        this.dynamoDbMapper = dynamoDbMapper;
    }

    public String createS3File(S3File s3File) {
        if (s3File == null) {
            throw new IllegalArgumentException("Message was null");
        }
        int tries = 0;
        while (tries < 10) {
            try {

                DenamoDBFile item = new DenamoDBFile();
                String s3Time = s3File.getEventTime();
                S3Body s3Body = s3File.getS3();
                S3Object s3Object = s3Body.getObject();
                String key = s3Object.getKey();
                int size = s3Object.getSize();

                item.setId(key);
                item.setSizeFile(size);
                item.setTimeFile(s3Time);

                dynamoDbMapper.save(item);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        throw new CouldNotCreateOrderException(
                "Unable to generate unique order id after 10 tries");
    }

}

