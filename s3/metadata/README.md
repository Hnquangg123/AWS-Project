
## Create a bucket
```sh
aws s3 mb s3://metadata-dylan-225
```

## Create a new file

echo "Hello Earth!" > hello.txt

## Upload file with metadata

```sh
aws s3api put-object --bucket metadata-dylan-225 --key hello.txt --body hello.txt --metadata Country=Germany
```

## Get Metadata through head object

```sh
aws s3api head-object --bucket metadata-dylan-225 --key hello.txt
```

## Cleanup or Rollback

```sh
aws s3 rm s3://metadata-dylan-225/hello.txt
aws s3 rb s3://metadata-dylan-225
```