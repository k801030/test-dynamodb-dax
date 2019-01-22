package com.yahoo.vison;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DaxClientDemo {

  private static String endpoint = "vison-dax2.mkbllw.clustercfg.dax.use2.cache.amazonaws.com:8111";

  public static void main(String[] args) throws Exception {
    AmazonDynamoDB client;

    if (args.length >= 1) {
      client = DynamoDBHelper.getDaxClient(args[0]);
    } else {
      client = DynamoDBHelper.getDynamoDBClient();
    }

    HashMap<String, AttributeValue> key = new HashMap<String, AttributeValue>();
    key.put(":a", new AttributeValue().withS("com.yahoo.taipei.bus"));

    List<QueryRequest> requests = new ArrayList<QueryRequest>();
    requests.add(new QueryRequest()
        .withTableName("vison-feed-db")
        .withKeyConditionExpression("appId = :a")
        .withExpressionAttributeValues(key)
        .withLimit(3000)
    );
    requests.add(new QueryRequest()
        .withTableName("vison-feed-db")
        .withKeyConditionExpression("appId = :a")
        .withExpressionAttributeValues(key)
        .withLimit(4000)
    );
    requests.add(new QueryRequest()
        .withTableName("vison-feed-db")
        .withKeyConditionExpression("appId = :a")
        .withExpressionAttributeValues(key)
        .withLimit(5000)
    );

    int iterators = 3;
    for (int i = 0; i < iterators; i++) {
      System.out.printf("==== Iterator %d ====\n", i + 1);
      for (QueryRequest request : requests) {
        long start = System.nanoTime();
        QueryResult result = client.query(request);
        long end = System.nanoTime();
        System.out.printf("Duration: %.3f ms\n", (end - start) / (1000000.0));
      }
    }

    // System.out.println(result.getCount());
  }
}
