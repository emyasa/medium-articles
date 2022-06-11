# AWS: Run an S3 triggered Lambda Locally Using LocalStack

Medium Article: https://medium.com/@devintjcode

Have any questions or concerns about this project? Feel free to reach out directly via this email: emyasa.emmanuel.yasa@gmail.com

One can expect a response at around 11:00 GMT and onwards.

---
For those who want to support this, here's a link: https://ko-fi.com/alltimecode. (It is much appreciated, and it really makes a difference!)

---
# Quick Setup Guide (TLDR;)
1. In the terminal, cd into <b>lambda-src</b> directory:
<br/> <code>cd lambda-src/</code>
2. Install the dependencies:
<br/> <code>npm install</code>
3. Next, still inside the <b>lambda-src</b> directory, zip the files:
<br/> <code>zip -r function.zip .</code>
4. Navigate into <b>s3-triggered-lambda</b> directory:
<br/> <code>cd ..</code>
5. Run:
<br/> <code>docker-compose up</code>
6. On a new terminal (<b>s3-triggered-lambda directory</b>), create a lambda function using localstack's endpoint url: 
<br/><code>aws --endpoint-url=http://localhost:4566 \ <br/>
lambda create-function --function-name my-lambda \ <br/>
--zip-file fileb://lambda-src/function.zip \ <br/> 
--handler index.handler --runtime nodejs12.x \ <br/>
--role arn:aws:iam::000000000000:role/lambda-role</code>
7. Create the S3 bucket: <br/> 
<code>aws --endpoint-url=http://localhost:4566 s3 mb s3://my-bucket</code>
8. Create the S3 bucket notification configuration:<br/>
<code>aws --endpoint-url=http://localhost:4566 \ <br/> 
s3api put-bucket-notification-configuration --bucket my-bucket \ <br/> 
--notification-configuration file://s3-notif-config.json</code>
9. Upload an object into S3: <br/>
<code>aws --endpoint-url=http://localhost:4566 \ <br/>
s3api put-object --bucket my-bucket \ <br/> --key dummyfile.txt --body=dummyfile.txt</code>
10. Check the lambda's logs: <br/>
<code>aws --endpoint-url=http://localhost:4566 logs tail '/aws/lambda/my-lambda' --follow</code>

<br/>
Done.
<br/>
<br/>

# AWS CLI commands for debugging

<br/>

# S3
aws --endpoint-url=http://localhost:4566 s3 ls s3://my-bucket

aws --endpoint-url=http://localhost:4566 s3 mb s3://my-bucket

aws --endpoint-url=http://localhost:4566 s3api put-bucket-notification-configuration --bucket my-bucket --notification-configuration file://s3-notif-config.json

aws --endpoint-url=http://localhost:4566 s3api put-object --bucket my-bucket --key sample.text --body=sample.text

aws --endpoint-url=http://localhost:4566 s3api get-bucket-location --bucket my-bucket

<br/>

# Create the Lambda's Execution Role 
In order to run the ff commands, iam must be part of the services config in docker-compose.yml

aws --endpoint-url=http://localhost:4566 iam create-role --role-name lambda-role --assume-role-policy-document file://trust-policy.json

aws --endpoint-url=http://localhost:4566 iam attach-role-policy --role-name lambda-role --policy-arn arn:aws:iam::aws:policy/service-role/AWSLambdaBasicExecutionRole

aws --endpoint-url=http://localhost:4566 iam attach-role-policy --role-name lambda-role --policy-arn arn:aws:iam::aws:policy/AmazonS3ReadOnlyAccess

aws --endpoint-url=http://localhost:4566 iam get-role --role-name lambda-role

aws --endpoint-url=http://localhost:4566 iam get-policy --policy-arn arn:aws:iam::aws:policy/service-role/AWSLambdaBasicExecutionRole

aws --endpoint-url=http://localhost:4566 iam get-policy-version --policy-arn arn:aws:iam::aws:policy/service-role/AWSLambdaBasicExecutionRole --version-id v1

<br/>

# Lambda
 aws --endpoint-url=http://localhost:4566 lambda create-function --function-name my-function \
--zip-file fileb://lambda/function.zip --handler index.handler --runtime nodejs12.x \
--role arn:aws:iam::000000000000:role/lambda-ex

aws --endpoint-url=http://localhost:4566 lambda get-function --function-name my-function

aws --endpoint-url=http://localhost:4566 lambda update-function-code --function-name my-function \
--zip-file fileb://function.zip

<br/>

# Logs
aws --endpoint-url=http://localhost:4566 logs describe-log-groups --query logGroups[*].logGroupName

aws --endpoint-url=http://localhost:4566 logs describe-log-streams --log-group-name '/aws/lambda/my-lambda' --query logStreams[*].logStreamName

aws --endpoint-url=http://localhost:4566 logs get-log-events --log-group-name '/aws/lambda/my-lambda' --log-stream-name '2022/01/29/[LATEST]2f4818ff'

aws --endpoint-url=http://localhost:4566 logs tail '/aws/lambda/my-lambda' --follow
