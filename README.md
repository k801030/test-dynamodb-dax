# Dynamodb DAX Testing

A repo used to test the performance in DynamoDB.

The example use DynamoDB and DAX to do the query for database.

## Run This Application

1. Setup
```
export TABLE_NAME=[your dynamodb table name]
export REGION=[your aws region]
mvn package
```

2. Run with DynamoDB
```
java -jar target/test-dynamodb-dax-1.0-SNAPSHOT-jar-with-dependencies.jar 
```

3. Run with DAX
```
java -jar target/test-dynamodb-dax-1.0-SNAPSHOT-jar-with-dependencies.jar [DAX cluster endpoint]
```
4. Compare the results

```
==== Iteration 1 ====
Duration: 2555.396 ms
Duration: 965.828 ms
Duration: 1301.714 ms
==== Iteration 2 ====
Duration: 1044.976 ms
Duration: 1242.509 ms
Duration: 1532.593 ms
==== Iteration 3 ====
...
```

## Reference

### What is DAX?

[DAX](https://aws.amazon.com/dynamodb/dax/?nc1=h_ls) is a fully-mananged, in-memory cached for DynamoDB.

### How to run a service using DAX?

DaAX can be deployed in EC2 only. [Here](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DAX.client.sample-app.html) is a tutorial about how to running a sample app.
