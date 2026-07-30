package com.myorg;

import software.constructs.Construct;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.s3.Bucket;

public class HelloCdkStack extends Stack {
    public HelloCdkStack(final Construct parent, final String id) {
        this(parent, id, null);
    }

    public HelloCdkStack(final Construct parent, final String id, final StackProps props) {
        super(parent, id, props);

        Bucket.Builder.create(this, "DylanBucket")
                .bucketName("dylan-handsome-bucket")
                .build();
    }
}
