## Create a bucket

aws s3 mb s3://dylan-encryption-bucket-225

## Create a file

echo "Hello World!" > hello.txt
aws s3 cp hello.txt s3://dylan-encryption-bucket-225

## Put Object with encryption of SS3-KMS

aws s3api put-object \
--bucket dylan-encryption-bucket-225 \
--key hello.txt \
--body hello.txt \
--server-side-encryption "aws:kms" \
--ssekms-key-id "your-key-kms-here"

## Put Object with SSE-C

export BASE64_ENCODED_KEY=$(openssl rand -base64 32)
echo $BASE64_ENCODED_KEY

export MD5_VALUE=$(echo $BASE64_ENCODED_KEY | md5sum | awk '{print $1}' | base64 -w0)
echo $MD5_VALUE

aws s3api put-object \
--bucket dylan-encryption-bucket-225 \
--key hello.txt \
--body hello.txt \
--sse-customer-algorithm AES256\
--sse-customer-key $BASE64_ENCODED_KEY \
--sse-customer-key-md5 $MD5_VALUE 

## ERROR HAPPEN try s3 instead
## Put Object with SSE-C via aws s3

openssl rand -out ssec.key 32
aws s3 cp hello.txt s3://dylan-encryption-bucket-225/hello.txt \
--sse-c AES256 \
--sse-c-key fileb://ssec.key

aws s3 cp s3://dylan-encryption-bucket-225/hello.txt hello.txt\
--sse-c AES256 \
--sse-c-key fileb://ssec.key