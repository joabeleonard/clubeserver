"# clubeserver" 


mvn clean install -DskipTests
ls rm
#Na pasta do aws-ibb.pem
ssh -i "aws-ibb.pem" ubuntu@ec2-18-231-160-235.sa-east-1.compute.amazonaws.com

scp -i aws-ibb.pem polls-0.0.1-SNAPSHOT.jar ubuntu@ec2-18-231-160-235.sa-east-1.compute.amazonaws.com:~