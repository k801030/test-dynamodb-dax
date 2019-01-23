package com.yahoo.vison;

import com.amazon.dax.client.dynamodbv2.AmazonDaxClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

public class DynamoDBHelper {
  public String region;

  public DynamoDBHelper(String region) {
    this.region = region;
  }

  public AmazonDynamoDB getDynamoDBClient() {
    AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
        .withCredentials(AWSCredentialProviderFactory.get())
        .withRegion(region)
        .build();
    return client;
  }

  public AmazonDynamoDB getDaxClient(String endpoint) {
    AmazonDaxClientBuilder daxClientBuilder = AmazonDaxClientBuilder.standard()
        .withCredentials(AWSCredentialProviderFactory.get());
    daxClientBuilder.withRegion(region).withEndpointConfiguration(endpoint);
    AmazonDynamoDB client = daxClientBuilder.build();
    return client;
  }
}
