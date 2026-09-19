## Create a user with no permissions

We need to create a new user with no permissions and generate out access keys

```sh
aws iam create-user --user-name sts-machine-user
aws iam create-access-key --user-name sts-machine-user --output table
```

Copy the access key and secret here
```sh
aws configure
```

Then edit credentials file to change awy from default profile

```sh
open ~/.aws/credentials
```

Test who you are:

```sh
aws sts get-caller-identity
aws sts get-caller-identity --profile sts
```

> An error occurred (AccessDenied) when calling the ListBuckets operation: Access Denied

## Create a Role

We need to create a role that will access a new resource

```sh
chmod u+x bin/deploy
./bin/deploy
```

## Use new user credentials and assume role

```sh
aws iam put-user-policy \
    --user-name sts-machine-user \
    --policy-name StsAssumePolicy \
    --policy-document file://policy.json
```


```sh
aws sts assume-role \
--role-arn arn:aws:iam::937749308254:role/my-sts-dylan-stack-StsRole-rqF2c25UVn9X \
--role-session-name s3-sts-dylan \
--profile sts
```

```sh
aws sts get-caller-identity --profile assumed
```

```sh
aws s3 ls --profile assumed
```

## Cleanup

tear down your cloudformation stack via the AWS Management Console

```sh
aws iam delete-user-policy --user-name sts-machine-user --policy-name StsAssumePolicy
aws iam delete-access-key --access-key-id AKIA5UVRW4NPODCWKZ5U --user-name sts-machine-user
aws iam delete-user --user-name sts-machine-user
```