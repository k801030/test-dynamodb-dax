import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
  static DynamoDBMapper mapper;
  static int threads = 20;

  public static void main(String[] args) {

    AWSCredentialsProvider provider = new ProfileCredentialsProvider("federate");
    AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
        .withCredentials(provider)
        .withRegion("us-east-2")
        .build();
    mapper = new DynamoDBMapper(client);

    Long createdAt;
    List<Notification> notifications = new ArrayList<Notification>();

    for (int i = 0; i < 10000; i++) {
      createdAt = new Date().getTime();
      Notification n = getNotification(createdAt, i);
      notifications.add(n);
    }

    // mapper.batchSave(notifications);
    // mapper.save(getNotification(createdAt, 100));

    // 1547800425450 -> 5000
    // 1547800425475 -> 813
    // 1547800425477 -> 239

    Runnable r = new Runnable() {
      public void run() {
        String[] times = new String[]{"1547800425450", "1547800425475", "1547800425477"};
        Notification n = new Notification();
        n.appId = "com.yahoo.taipei.bus";

        for (int i = 0; i < times.length; i++) {
          AttributeValue a = new AttributeValue()
              .withN(times[i]);
          Condition c = new Condition()
              .withComparisonOperator(ComparisonOperator.GT)
              .withAttributeValueList(a);
          DynamoDBQueryExpression expression = new DynamoDBQueryExpression()
              .withIndexName("t-index")
              .withHashKeyValues(n)
              .withRangeKeyCondition("createdAt", c)
              .withConsistentRead(false);

          long s = System.currentTimeMillis();
          List<Notification> results = new ArrayList<Notification>();
          try {
            results = mapper.query(Notification.class, expression);
          } catch (AmazonDynamoDBException e) {
            System.out.println(e.getErrorCode());
          }
          long duration = System.currentTimeMillis() - s;

          System.out.println("[" + Thread.currentThread().getId() + "] " + results.size() + ":" + duration);

        }
      }
    };

    ExecutorService executor = Executors.newFixedThreadPool(3);
    for (int i = 0; i < threads; i++) {

      executor.execute(r);
    }
  }

  private static String getAppId(int i) {
    if (i < 5000) {
      return "tw.auction";
    } else {
      return "com.yahoo.taipei.bus";
    }
  }

  private static Notification getNotification(long createdAt, int i) {
    Notification n = new Notification();
    n.notificationId = UUID.randomUUID().toString();
    n.createdAt = createdAt;
    n.osName = "oreo";
    n.osVersion = "19.4.2";
    n.osName = "android";
    n.osLang = "en-US";
    n.appId = getAppId(i);
    n.appVersion = "1.0.0";
    return n;
  }

}

