package com.amazonaws.handlers;

import com.amazonaws.config.DaggerS3FileComponent;
import com.amazonaws.config.S3FileComponent;
import com.amazonaws.dao.S3FileDao;
import com.amazonaws.model.request.S3File;
import com.amazonaws.model.request.S3Records;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;

public class QueueConsumerLambda implements RequestHandler<SQSEvent, String> {

    @Inject
    ObjectMapper objectMapper;
    @Inject
    S3FileDao s3FileDao;
    private final S3FileComponent s3FileComponent;

    public QueueConsumerLambda() {
        s3FileComponent = DaggerS3FileComponent.builder().build();
        s3FileComponent.inject(this);
    }

    @Override
    public String handleRequest(SQSEvent event, Context context) {
        for (SQSMessage message : event.getRecords()) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                S3Records s3Records = mapper.readValue(message.getBody(), S3Records.class);
                for (S3File s3File : s3Records.getRecords()) {
                    s3FileDao.createS3File(s3File);
                }
            } catch (Exception e) {
            }
        }
        return "OK";
    }
}
