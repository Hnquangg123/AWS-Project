## Create a Website 1

## Create a bucket

```sh
aws s3 mb s3://dylan-cors-bucket-225
```

## Change block public access

```sh
aws s3api put-public-access-block \
--bucket dylan-cors-bucket-225 \
--public-access-block-configuration "BlockPublicAcls=true,IgnorePublicAcls=true,BlockPublicPolicy=false,RestrictPublicBuckets=false"
```

## Create a bucket policy

```sh
aws s3api put-bucket-policy --bucket dylan-cors-bucket-225 --policy file://policy.json
```

## Turn on static website hosting

```sh
aws s3api put-bucket-website --bucket dylan-cors-bucket-225 --website-configuration file://website.json
```

## Upload our index.html file and include a resource that would be cross-origin

```sh
aws s3 cp index.html s3://dylan-cors-bucket-225
```

## View the website and see if the index.html is there.

```sh
http://dylan-cors-bucket-225.s3-website-ap-southeast-2.amazonaws.com
```

## Create a Website 2

```sh
aws s3 mb s3://dylan-cors-bucket2-225
```

## Change block public access

```sh
aws s3api put-public-access-block \
--bucket dylan-cors-bucket2-225 \
--public-access-block-configuration "BlockPublicAcls=true,IgnorePublicAcls=true,BlockPublicPolicy=false,RestrictPublicBuckets=false"
```

## Create a bucket policy

```sh
aws s3api put-bucket-policy --bucket dylan-cors-bucket2-225 --policy file://policy2.json
```

## Turn on static website hosting

```sh
aws s3api put-bucket-website --bucket dylan-cors-bucket2-225 --website-configuration file://website.json
```

## Upload our javascript file 

```sh
aws s3 cp hello.js s3://dylan-cors-bucket2-225
```

## View the website and see if the index.html is there.

```sh
http://dylan-cors-bucket2-225.s3-website-ap-southeast-2.amazonaws.com
```

## Create API Gateway with mock response and then test the endpoint

curl -X POST https://e1qdwmqmf5.execute-api.ap-southeast-2.amazonaws.com/prod/hello

## Set CORS on our bucket

```sh
aws s3api put-bucket-cors --bucket dylan-cors-bucket-225 --cors-configuration file://cors.json
```

