"# clubeserver" 


mvn clean install -DskipTests

#Na pasta do aws-ibb.pem
#Copia arquivo
scp -i aws-ibb.pem polls-0.0.1-SNAPSHOT.jar ubuntu@ec2-18-231-160-235.sa-east-1.compute.amazonaws.com:~

#Entra no srvidor
ssh -i "aws-ibb.pem" ubuntu@ec2-18-231-160-235.sa-east-1.compute.amazonaws.com


#Lista arquivos
ls 
#Remove arquivo
 sudo  rm polls-0.0.1-SNAPSHOT.jar
