resource "aws_s3_bucket" "dylan-s3-bucket" {
  tags = {
    Name        = "My bucket"
    Environment = "Dev"
  }
}