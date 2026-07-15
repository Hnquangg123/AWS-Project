## Create a new s3 bucket

```md
aws s3 mb s3://checksums-dylan-225
```

## Create a file that will we do a checksum on

```
echo "Hello World" > myfile.txt 
```
```md
## Get a checksum of a file for md5
e59ff97941044f85df5297e1c302d260 *myfile.tcrxt
```

## Upload our file to see

```
aws s3 cp myfile.txt s3://checksums-dylan-225
aws s3api head-object --bucket checksums-dylan-225 --key myfile.txt
```

## Upload a file with a different kind of checksums

```
rhash --crc32 --simple myfile.txt
echo "b095e5e3" | xxd -r -p | base64
```

```sh
aws s3api put-object \
--bucket checksums-dylan-225 \
--key myfilecrc32.txt \
--body myfile.txt \
--checksum-algorithm="CRC32" \
--checksum-crc32="sJXl4w=="
```

