package com.yahoo.vison;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DaxClientDemo {

  private static String endpoint = "vison-dax2.mkbllw.clustercfg.dax.use2.cache.amazonaws.com:8111";
  static AmazonDynamoDB client;

  public static void main(String[] args) throws Exception {
    if (args.length >= 1) {
      client = DynamoDBHelper.getDaxClient(args[0]);
    } else {
      client = DynamoDBHelper.getDynamoDBClient();
    }

    MyRunnable.init();
    ExecutorService executorService = Executors.newFixedThreadPool(100);
    for (int i = 0; i < 100; i++) {
      executorService.execute(new MyRunnable(client, i + 1));
    }

    // System.out.println(result.getCount());
  }
}

class MyRunnable implements Runnable {

  static List<QueryRequest> requests = new ArrayList<QueryRequest>();
  int index;
  AmazonDynamoDB client;

  public MyRunnable(AmazonDynamoDB client, int index) {
    this.index = index;
    this.client = client;
  }

  public static void init() {
    HashMap<String, AttributeValue> key = new HashMap<String, AttributeValue>();
    key.put(":a", new AttributeValue().withS("com.yahoo.taipei.bus"));

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
  }

  public void run() {
    System.out.printf("==== Iterator %d ====\n", index);
    for (QueryRequest request : requests) {
      long start = System.nanoTime();
      QueryResult result = client.query(request);
      long end = System.nanoTime();
      System.out.printf("Duration: %.3f ms\n", (end - start) / (1000000.0));
    }
  }
}
