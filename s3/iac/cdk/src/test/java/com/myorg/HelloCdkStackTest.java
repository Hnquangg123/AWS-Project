package com.myorg;

import software.amazon.awscdk.App;
import software.amazon.awscdk.assertions.Template;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class HelloCdkStackTest {
    @Test
    public void givenCdkStack_whenStackSynthesized_thenS3BucketCreatedWithCorrectName() throws IOException {
        // Given
        App app = new App();

        // When
        HelloCdkStack stack = new HelloCdkStack(app, "test");
        Template template = Template.fromStack(stack);

        // Then
        Map<String, Object> expectedProperties = new HashMap<>();
        expectedProperties.put("BucketName", "dylan-handsome-bucket");

        template.hasResourceProperties("AWS::S3::Bucket", expectedProperties);
    }
}
