package com.amazonaws.handlers;

import com.amazonaws.config.DaggerOrderFileComponent;
import com.amazonaws.config.OrderFileComponent;
import com.amazonaws.dao.OrderFileDao;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;

public class QueueConsumerLambda implements RequestHandler<SQSEvent, String> {

    @Inject
    ObjectMapper objectMapper;
    @Inject
    OrderFileDao orderFileDao;
    private final OrderFileComponent orderFileComponent;

    public QueueConsumerLambda() {
        orderFileComponent = DaggerOrderFileComponent.builder().build();
        orderFileComponent.inject(this);
    }

    @Override
    public String handleRequest(SQSEvent event, Context context) {
        for (SQSMessage message : event.getRecords()) {
            orderFileDao.createOrder(message);
        }
        return "OK";
    }
}
