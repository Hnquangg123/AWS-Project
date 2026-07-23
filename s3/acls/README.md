## Create a new bucket

```sh
aws s3api create-bucket \
--bucket acls-dylan-225 \
--region ap-southeast-1 \
--create-bucket-configuration LocationConstraint=ap-southeast-1 \
--query Location \
--output text
```

## Turn of Block Public Access for ACLs

```sh
aws s3api put-public-access-block \
--bucket acls-dylan-225 \
--public-access-block-configuration "BlockPublicAcls=false,IgnorePublicAcls=false,BlockPublicPolicy=true,RestrictPublicBuckets=true"
```

```sh
aws s3api get-public-access-block --bucket acls-dylan-225 
```

## Change Bucket Ownership

```sh
aws s3api put-bucket-ownership-controls \
--bucket acls-dylan-225 \
--ownership-controls="Rules=[{ObjectOwnership=BucketOwnerPreferred}]"
```

## Change ACLs to allow for a user in another AWS Account

```sh
aws s3api put-bucket-acl \
--bucket acls-dylan-225 \
--access-control-policy file://AWS-Project/s3/acls/policy.json
```

## Access Bucket from other account

```sh
touch test.txt
aws s3 cp test.txt s3://acls-dylan-225
aws s3 ls s3://acls-dylan-225
```

## Cleanup

aws s3 rm s3://acls-dylan-225/test.txt
aws s3 rb s3://acls-dylan-225