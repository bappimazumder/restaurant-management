# Restaurant Management API Exposes

This repo will contain restaurant management APIs.


**Prerequisite: **
*  openjdk 17
*  Postgresql
*  git client
*  Docker Desktop 
*  ElasticSearch
*  Logstash
*  Kibana


**Prepare Database: **

    psql -U postgres -d postgres

    create user restaurant_management_user with encrypted password '123456';

    create database restaurant_management_db owner restaurant_management_user;



now as we have our db created we need to run the db script

1. resources/db_script/s1_create_customer_order_table.sql

2.  resources/db_script/s2_insert_customer_order_table_data.sql

Build the war file using this command

./gradlew clean bootWar

The war will found on \build\libs\restaurant-management.war

Finally, copy the generated war to /path/to/tomcat10/webapps/ directory as customer-management.war


For test please export the postman collection from this location
resources/postman_collection/restaurant-managment.postman_collection.json

Here is curl
===============
1. API to return all of the registered customer list.
   curl --location 'http://localhost:8085/api/v1/restaurant/customer/getAllCustomer'

2. API the order list of the current day.
   curl --location 'http://localhost:8085/api/v1/customer/account/transferAmount' \
   --header 'Content-Type: application/json' \
   --data '{
   "senderAccountNo":"11111111111",
   "receiverAccountNo":"1111111112",
   "amount": 500
   }'

3. API to return the total sale amount of the current day.
   curl --location 'http://localhost:8085/api/v1/restaurant/order/todaySale'

4. API to return the entire order list of a customer.
   curl --location 'http://localhost:8085/api/v1/restaurant/order/customersOrder?customerCode=C-001'

5. API to return the max sale day of a certain time range.
   curl --location 'http://localhost:8085/api/v1/restaurant/order/maxSaleInADay?start=2024-10-01&end=2024-10-09'

Dockerized an application
===========================
 For Dockerized the application run those following command: 
   Before build docker image change the datasource url in application.yaml file
   datasource:
      url: jdbc:postgresql://host.docker.internal:5432/restaurant_management_db
   
   1) Step 1 : install Docker
   2) Step 2 : Build the Docker Image
   Open your terminal, navigate to your project directory, and run:
     D:\Incognito\restaurant-management> docker build -t restaurant-management-docker-img .
     D:\Incognito\restaurant-management> docker image ls

   3) Step 3: Run the Docker Container
      After the image is built, you can run your application in a container:
     D:\Incognito\restaurant-management> docker run -p 8080:8085 restaurant-management-docker-img
     This maps port 8080 of your container to port 8085 on your host machine.

   4) Step 4: Access Your Application
      You can now access your application in a web browser/postman at http://localhost:8080.

Log Visualization
==================
Using the ELK stack (Elasticsearch, Logstash, and Kibana) for log visualization involves several steps. 
Here’s a simplified guide to help you get started:
1. Set Up the ELK Stack
   i) Elasticsearch: This is where your logs will be stored. Install it and ensure it’s running.
      Download ElasticSearch: https://www.elastic.co/downloads/elasticsearch

   ii) Logstash: This is the data processing pipeline that ingests logs and sends them to Elasticsearch. 
   Install and configure it.
      Download Logstash: https://www.elastic.co/downloads/logstash

   iii) Kibana: This is the visualization tool. Install it and connect it to your Elasticsearch instance.
      Download Kibana: https://www.elastic.co/downloads/kibana

2. Configure Logstash
   Create a configuration file (e.g., logstash.conf) and put it on the \logstash-8.15.2\bin\ folder
   input {
      file {
         path => "C:/Users/User/Desktop/logs/restaurant-management.log"
         start_position => "beginning"
      }
   }

   output {
      elasticsearch {
         hosts => ["localhost:9200"]
          index => "logstash-%{+YYYY.MM.dd}"
      }
      stdout {
         codec => rubydebug
      }
   }

   Note: This path value is also defined in the project directory \restaurant-management\src\main\resources\logback-spring.xml
 
3. Configure Kibana
      The kibana.yml file is usually located in the config directory of your Kibana installation. 
      Common paths include: \kibana-8.15.2\config\
   Use a text editor of your choice to open kibana.yml and remove comment from below line.
     elasticsearch.hosts: ["http://localhost:9200"]
      

4. Run ELK:
   i) Run Elasticsearch: => \elasticsearch-8.15.2\bin\elasticsearch.bat
      You can now access your elasticsearch in a web browser http://localhost:9200
   ii) Run Kibana: => \kibana-8.15.2\bin\elasticsearch.bat
      You can now access your kibana in a web browser http://localhost:5601
   iii) Run Logstash using this command : \logstash-8.15.2\bin> logstash -f logstash.conf 
      You can now access your Logstash in a web browser http://localhost:9600

5. Get log indices from Elastic search :
   
   Access on http://localhost:9200/_cat/indices
   ![](C:\Users\User\Desktop\elastic_indices_png.png)
   To access logs in Elasticsearch using a specific index
   http://localhost:9200/logstash-2024.10.13/_search
   
   
6. Kibana Dashboard Configuration:
   Go to the Kibana Dashboard , Then go to the Management Option.
   Then click on Index pattern > Create Index pattern > Define your index pattern here using 
   this log index that get from step 5.

   Now click on discover , it will show all log related to this index pattern.
    It will show logs look like this 

![](C:\Users\User\Desktop\kibana_log_view.png)
   