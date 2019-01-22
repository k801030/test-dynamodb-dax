import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;

public class AWSCredentialProviderFactory {
  public static AWSCredentialsProvider get() {
    return new ProfileCredentialsProvider("federate");
  }
}
