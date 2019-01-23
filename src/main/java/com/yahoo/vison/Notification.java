package com.yahoo.vison;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

public class Notification {

  @DynamoDBHashKey
  @DynamoDBIndexHashKey(globalSecondaryIndexName = "t-index")
  String appId;

  @DynamoDBRangeKey
  String notificationId;

  @DynamoDBIndexRangeKey(globalSecondaryIndexName = "t-index")
  Long createdAt;
  String osName;
  String osVersion;
  String osLang;
  String appVersion;

  public String getAppId() {
    return appId;
  }

  public String getNotificationId() {
    return notificationId;
  }

  public Long getCreatedAt() {
    return createdAt;
  }

  public String getOsName() {
    return osName;
  }

  public String getOsVersion() {
    return osVersion;
  }

  public String getOsLang() {
    return osLang;
  }

  public String getAppVersion() {
    return appVersion;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public void setNotificationId(String notificationId) {
    this.notificationId = notificationId;
  }

  public void setCreatedAt(Long createdAt) {
    this.createdAt = createdAt;
  }

  public void setOsName(String osName) {
    this.osName = osName;
  }

  public void setOsVersion(String osVersion) {
    this.osVersion = osVersion;
  }

  public void setOsLang(String osLang) {
    this.osLang = osLang;
  }

  public void setAppVersion(String appVersion) {
    this.appVersion = appVersion;
  }
}
