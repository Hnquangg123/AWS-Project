## Create bucket policy

aws s3 mb s3://dylan-bucket-policy-225

## Create bucket policy

aws s3api put-bucket-policy --bucket dylan-bucket-policy-225 --policy file://policy.json

## In the other account access the bucket

touch bootcamp.txt
aws s3 cp bootcamp.txt s3://dylan-bucket-policy-225
aws s3 ls s3://dylan-bucket-policy-225

## Cleanup

aws s3 rm s3://dylan-bucket-225/bootcamp.txt
aws s3 rb s3://dylan-bucket-225