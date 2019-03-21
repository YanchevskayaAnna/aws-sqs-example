package com.amazonaws.dao;

import com.amazonaws.exception.CouldNotCreateOrderException;
import com.amazonaws.exception.TableDoesNotExistException;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;

public class OrderFileDao {

    public static final String HASH_KEY_NAME = "999999";
    private final DynamoDB dynamoDB;
    private final String tableName;

    public OrderFileDao(final DynamoDB dynamoDb, final String tableName) {
        this.tableName = tableName;
        this.dynamoDB = dynamoDb;

    }

    public void createOrder(SQSEvent.SQSMessage message) {
        if (message == null) {
            throw new IllegalArgumentException("message was null");
        }
        int tries = 0;
        while (tries < 10) {
            try {
                Item item = createOrderItem(message);
                Table table = dynamoDB.getTable(tableName);
                table.putItem(item);
            } catch (ConditionalCheckFailedException e) {
                tries++;
            } catch (ResourceNotFoundException e) {
                throw new TableDoesNotExistException(
                        "Order table " + tableName + " does not exist");
            }
        }
        throw new CouldNotCreateOrderException(
                "Unable to generate unique order id after 10 tries");
    }

    private Item createOrderItem(SQSEvent.SQSMessage message) {
        Item item = new Item()
                .withPrimaryKey(HASH_KEY_NAME, "2")
                .with("message-body", message.getBody());
        return item;
    }

}

