package com.yahoo.vison;

import com.amazon.dax.client.dynamodbv2.AmazonDaxClient;
import com.amazon.dax.client.dynamodbv2.ClientConfig;
import com.amazon.dax.client.dynamodbv2.ClusterDaxClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

public class DynamoDBHelper {
  public static final String REGION = "us-east-2";

  public static AmazonDynamoDB getDynamoDBClient() {
    AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
        .withCredentials(AWSCredentialProviderFactory.get())
        .withRegion(REGION)
        .build();
    return client;
  }

  public static AmazonDynamoDB getDaxClient(String endpoint) {
    ClientConfig daxConfig = new ClientConfig()
        .withEndpoints(endpoint)
        .withCredentialsProvider(AWSCredentialProviderFactory.get());


    AmazonDaxClient client = new ClusterDaxClient(daxConfig);
    return client;
  }
}
