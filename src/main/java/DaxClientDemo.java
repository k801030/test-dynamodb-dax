import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import java.util.HashMap;

public class DaxClientDemo {
  private static String endpoint = "vison-dax2.mkbllw.clustercfg.dax.use2.cache.amazonaws.com:8111";

  public static void main(String[] args) throws Exception {
    AmazonDynamoDB client;

    if (args.length >= 1) {
      client = DynamoDBHelper.getDaxClient(args[0]);
    } else {
      client = DynamoDBHelper.getDynamoDBClient();
    }

    HashMap<String, AttributeValue> key = new HashMap<>();
    key.put("appId", new AttributeValue().withS("com.yahoo.taipei.bus"));

    QueryRequest queryRequest = new QueryRequest()
        .withTableName("vison-feed-db")
        .withKeyConditionExpression("appId = :a")
        .withExpressionAttributeValues(key);
    QueryResult result = client.query(queryRequest);

    System.out.println(result);
  }
}
