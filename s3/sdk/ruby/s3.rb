# frozen_string_literal: true

require 'aws-sdk-s3'
require 'pry'
require 'securerandom'
require 'tmpdir'

# This script automates the creation of an Amazon S3 bucket and populates it
# with a random number of text files (between 1 and 6). Each file contains
# a randomly generated UUID.
#
# @example Run the script
#   BUCKET_NAME="my-unique-bucket-name" ruby script.rb
#
# @note Requires AWS credentials to be configured in the environment, IAM role, or ~/.aws/credentials.
# @note The BUCKET_NAME environment variable must be set and globally unique.

# The name of the S3 bucket to create, fetched from the environment variables.
# @type [String]
bucket_name = ENV['BUCKET_NAME']

# The AWS region where the bucket will be created.
# @type [String]
region = 'ap-southeast-1'

puts "Target Bucket: #{bucket_name}"

# Initialize the Amazon S3 Client.
# AWS credentials are automatically resolved from the environment configuration.
client = Aws::S3::Client.new

# Create the S3 bucket with the specified location constraint.
resp = client.create_bucket({
  bucket: bucket_name,
  create_bucket_configuration: {
    location_constraint: region,
  }
})
# binding.pry

# Generate a random number of files to create and upload (1 to 6).
# @type [Integer]
number_of_files = 1 + rand(6)
puts "number_of_files: #{number_of_files}"

# Iterate and create the specified number of files.
number_of_files.times.each do |i|
  puts "Processing file index: #{i}"

  # The name of the file to be uploaded to S3.
  # @type [String]
  filename = "file_#{i}.txt"

  # The local temporary path where the file is stored before upload.
  # @type [String]
  output_path = File.join(Dir.tmpdir, filename)

  # Create a local file and write a randomly generated UUID to it.
  File.open(output_path, "w") do |f|
    f.write(SecureRandom.uuid)
  end

  # Open the newly created local file in read-binary mode and upload it to S3.
  #
  # @param bucket [String] The target S3 bucket.
  # @param key [String] The object key (filename) in S3.
  # @param body [File] The file stream to upload.
  # @return [Aws::S3::Types::PutObjectOutput]
  File.open(output_path, 'rb') do |file|
    client.put_object(
      bucket: bucket_name,
      key: filename,
      body: file
    )
  end
end