# Create database 
create database **eminance**

# Created two roles USER and ADMIN. I have provide you the curl of to create user and admin. admin is able to access both the task request and user is access only draw-match count.

**User Creation**

curl --location 'http://localhost:8080/auth/signin' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name":"user",
    "email":"user@example.com",
    "password":"12345",
    "role":"USER"
}'


**Admin Creation **


curl --location 'http://localhost:8080/auth/signin' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name":"admin",
    "email":"admin@example.com",
    "password":"12345",
    "role":"ADMIN"
}'

**Login Curl**

curl --location 'http://localhost:8080/auth/signup' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email":"admin@example.com",
    "password":"12345"
}'

## Check the match- draw count curl

curl --location 'http://localhost:8080/football/fetch-draw-matches/2013' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQGV4YW1wbGUuY29tIiwiaWF0IjoxNzEzNzYwMTM0LCJleHAiOjE3MTM3NjA3MzR9.O06h59Mm41LqllojWrLAjS33hzeLU_puW9oSRg_ni8g'

**##Check the Token Parameter curl**

curl --location 'http://localhost:8080/api/token-decode/eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBleGFtcGxlLmNvbSIsImlhdCI6MTcxMzc1NzA0MywiZXhwIjoxNzEzNzU3NjQzfQ._nSoaW9z5yIjYsfYlLhTrX8UMN74TtPL4HOYaABrWHY' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQGV4YW1wbGUuY29tIiwiaWF0IjoxNzEzNzYwMTM0LCJleHAiOjE3MTM3NjA3MzR9.O06h59Mm41LqllojWrLAjS33hzeLU_puW9oSRg_ni8g'




