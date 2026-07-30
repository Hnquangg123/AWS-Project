package aws;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.encryption.s3.S3EncryptionClient;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class S3ClientSideEncryptionDemoTest {

    private static final String BUCKET_NAME = "encrypt-client-dylan-225";
    private static final Region REGION = Region.AP_SOUTHEAST_2;

    private S3Client s3EncryptionClient;
    private S3Client standardS3Client;
    private String testObjectKey;

    @BeforeEach
    public void setUp() throws Exception {
        // Generate a unique object key for each test run
        testObjectKey = "test-secret-" + UUID.randomUUID() + ".txt";

        // Generate RSA Key Pair
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(2048);
        KeyPair rsaKeyPair = keyPairGen.generateKeyPair();

        // Initialize S3 Encryption Client
        s3EncryptionClient = S3EncryptionClient.builderV4()
                .rsaKeyPair(rsaKeyPair)
                .region(REGION)
                .build();

        // Initialize Standard S3 Client
        standardS3Client = S3Client.builder()
                .region(REGION)
                .build();
    }

    @AfterEach
    public void tearDown() {
        // Clean up S3 test object after each test
        try {
            standardS3Client.deleteObject(DeleteObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(testObjectKey)
                    .build());
        } catch (Exception ignored) {
            // Ignore if object was not created
        }

        // Close client resources
        if (s3EncryptionClient != null) {
            s3EncryptionClient.close();
        }
        if (standardS3Client != null) {
            standardS3Client.close();
        }
    }

    @Test
    @DisplayName("Should encrypt locally on upload and decrypt locally on download using S3EncryptionClient")
    public void givenPlaintextData_whenUploadedAndDownloadedWithEncryptionClient_thenContentIsDecryptedSuccessfully() {
        // GIVEN
        String secretData = "secretdata-unit-test";

        // WHEN
        s3EncryptionClient.putObject(
                PutObjectRequest.builder()
                        .bucket(BUCKET_NAME)
                        .key(testObjectKey)
                        .build(),
                RequestBody.fromString(secretData)
        );

        String decryptedContent = s3EncryptionClient.getObject(
                GetObjectRequest.builder()
                        .bucket(BUCKET_NAME)
                        .key(testObjectKey)
                        .build(),
                ResponseTransformer.toBytes()
        ).asUtf8String();

        // THEN
        assertNotNull(decryptedContent, "Decrypted content should not be null");
        assertEquals(secretData, decryptedContent, "Downloaded content should match the original plaintext data");
    }

    @Test
    @DisplayName("Should return encrypted ciphertext when downloaded with standard S3 client")
    public void givenEncryptedObjectInS3_whenDownloadedWithStandardClient_thenDataIsEncrypted() {
        // GIVEN
        String secretData = "secretdata-unit-test";

        s3EncryptionClient.putObject(
                PutObjectRequest.builder()
                        .bucket(BUCKET_NAME)
                        .key(testObjectKey)
                        .build(),
                RequestBody.fromString(secretData)
        );

        // WHEN
        ResponseBytes<GetObjectResponse> responseBytes = standardS3Client.getObject(
                GetObjectRequest.builder()
                        .bucket(BUCKET_NAME)
                        .key(testObjectKey)
                        .build(),
                ResponseTransformer.toBytes()
        );

        String base64CipherText = Base64.getEncoder().encodeToString(responseBytes.asByteArray());

        // THEN
        assertNotNull(base64CipherText, "Ciphertext should not be null");
        assertNotEquals(secretData, base64CipherText, "Standard client should retrieve encrypted ciphertext, not original plaintext");
    }
}