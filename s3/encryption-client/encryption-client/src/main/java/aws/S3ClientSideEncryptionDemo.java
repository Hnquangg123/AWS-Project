package aws;

import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.encryption.s3.S3EncryptionClient;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;

public class S3ClientSideEncryptionDemo {

    public static void main(String[] args) throws Exception {
        String bucketName = "encrypt-client-dylan-225";
        String objectKey = "secret.txt";
        String secretData = "secretdata";

        // 1. Generate local RSA key pair (Client Master Key)
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(2048);
        KeyPair rsaKeyPair = keyPairGen.generateKeyPair();

        // 2. Initialize S3 Encryption Client with the local RSA key
        // AWS environment credentials are picked up automatically from your environment
        S3Client s3EncryptionClient = S3EncryptionClient.builderV4()
                .rsaKeyPair(rsaKeyPair)
                .region(Region.AP_SOUTHEAST_2)
                .build();

        // 3. Upload object (Encrypts data locally BEFORE sending to AWS)
        System.out.println("Uploading encrypted object...");
        s3EncryptionClient.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(objectKey)
                        .build(),
                RequestBody.fromString(secretData)
        );

        // 4. Download object using Encryption Client (Decrypts locally)
        String decryptedContent = s3EncryptionClient.getObject(
                GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(objectKey)
                        .build(),
                ResponseTransformer.toBytes()
        ).asUtf8String();

        System.out.println("Encryption Client Output -> " + decryptedContent);

        // 5. Download object using Standard S3 Client (AWS returns raw ciphertext)
        S3Client standardS3Client = S3Client.builder()
                .region(Region.AP_SOUTHEAST_2) // <--- CHANGE to your bucket's exact region
                .build();
        ResponseBytes<GetObjectResponse> responseBytes = standardS3Client.getObject(
                GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(objectKey)
                        .build(),
                ResponseTransformer.toBytes()
        );

        // Convert raw binary ciphertext to Base64 for save console printing
        String base64CipherText = Base64.getEncoder().encodeToString(responseBytes.asByteArray());

        System.out.println("Standard Client Output (Ciphertext) -> " + base64CipherText);

        // Close resource
        s3EncryptionClient.close();
        standardS3Client.close();
    }
}