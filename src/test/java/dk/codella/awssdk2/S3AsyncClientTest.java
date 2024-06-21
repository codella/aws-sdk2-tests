package dk.codella.awssdk2;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.S3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Testcontainers
public class S3AsyncClientTest {

  DockerImageName localstackImage = DockerImageName.parse("localstack/localstack:3.5.0");

  @Container
  public LocalStackContainer localstack = new LocalStackContainer(localstackImage).withServices(S3);

  public S3AsyncClient s3AsyncClient;

  @BeforeEach
  public void setUp() {
     s3AsyncClient = S3AsyncClient
        .builder()
        .endpointOverride(localstack.getEndpoint())
        .credentialsProvider(
            StaticCredentialsProvider.create(
                AwsBasicCredentials.create(localstack.getAccessKey(), localstack.getSecretKey())
            )
        )
        .region(Region.of(localstack.getRegion()))
        .build();
  }

  @Test
  public void testSimplePutAndGet() throws Exception {
    var createBucketRes = s3AsyncClient.createBucket(CreateBucketRequest.builder()
        .bucket("test-bucket")
        .build()).get();

    assertNotNull(createBucketRes);

    var putObjectRes = s3AsyncClient.putObject(PutObjectRequest.builder()
        .bucket("test-bucket")
        .key("test-string")
        .build(), AsyncRequestBody.fromString("this is a test")).get();

    assertNotNull(putObjectRes);
  }

}