package com.amazonaws.config;

import com.amazonaws.dao.S3FileDao;
import com.amazonaws.handlers.QueueConsumerLambda;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {S3FileModule.class})
public interface S3FileComponent {
    S3FileDao provideOrderDao();
    void inject(QueueConsumerLambda requestHandler);
}
