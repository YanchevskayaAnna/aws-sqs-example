package com.amazonaws.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="s3files")
public class DenamoDBFile {
    private String id;
    private String timeFile;
    private int sizeFile;

    @DynamoDBHashKey(attributeName="id")
    public String getId() { return id; }
    public void setId(String id) {this.id = id; }

    @DynamoDBAttribute(attributeName="time")
    public String getTimeFile() {return timeFile;}
    public void setTimeFile(String timeFile) {this.timeFile = timeFile;}

    @DynamoDBAttribute(attributeName="size")
    public int getSizeFile() { return sizeFile; }
    public void setSizeFile(int sizeFile) { this.sizeFile = sizeFile; }

}
