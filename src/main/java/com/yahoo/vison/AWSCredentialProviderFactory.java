package com.yahoo.vison;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;

public class AWSCredentialProviderFactory {
  public static AWSCredentialsProvider get() {
    return DefaultAWSCredentialsProviderChain.getInstance();
  }
}
