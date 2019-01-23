package com.yahoo.vison;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DaxClientDemo {

  public static void main(String[] args) {
    // setup
    AmazonDynamoDB client;
    String endpoint = null;
    String tableName;
    String region;
    if (args.length >= 1) {
      endpoint = args[0];
    }
    tableName = System.getenv("TABLE_NAME");
    region = System.getenv("REGION");


    DynamoDBHelper helper = new DynamoDBHelper(region);
    if (endpoint != null) {
      client = helper.getDaxClient(endpoint);
    } else {
      client = helper.getDynamoDBClient();
    }

    List<QueryRequest> requests = generateRequests(tableName);

    for (int i = 0; i < 5; i++) {
      System.out.printf("==== Iteration %d ====\n", i + 1);
      for (QueryRequest request : requests) {
        long start = System.nanoTime();
        client.query(request);
        long end = System.nanoTime();
        System.out.printf("Duration: %.3f ms\n", (end - start) / (1000000.0));
      }
    }
  }

  private static List<QueryRequest> generateRequests(String tableName) {
    List<QueryRequest> requests = new ArrayList<QueryRequest>();
    HashMap<String, AttributeValue> key = new HashMap<String, AttributeValue>();
    key.put(":a", new AttributeValue().withS("com.yahoo.taipei.bus"));
    requests.add(new QueryRequest()
        .withTableName(tableName)
        .withKeyConditionExpression("appId = :a")
        .withExpressionAttributeValues(key)
        .withLimit(3000)
    );
    requests.add(new QueryRequest()
        .withTableName(tableName)
        .withKeyConditionExpression("appId = :a")
        .withExpressionAttributeValues(key)
        .withLimit(4000)
    );
    requests.add(new QueryRequest()
        .withTableName(tableName)
        .withKeyConditionExpression("appId = :a")
        .withExpressionAttributeValues(key)
        .withLimit(5000)
    );
    return requests;
  }
}
