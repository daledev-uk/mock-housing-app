# mock-housing-app

# Summary
A mock housing application I created to aid testing of a system I worked on. Also gave me opportunity to play with Spring Boot some more and Angular.

# Mock Housing App
The application I worked on was a CRM application where contacts may or may not be held externally. To aid testing whereby contacts are held externally, I created a stub application which allows me to create tenancies, tennants and addresses.

# Running the application
In order to run the application set the connection details in the application.yml file. The maven package will produce the executeable jar.

# Docker
Dockerfile provided to create docker image, steps to run....

## Database
### Create the database
docker run -e 'ACCEPT_EULA=Y' -e 'SA_PASSWORD=y0urP4ss' -p 1433:1433 -d --name netcall-housing-db microsoft/mssql-server-linux

### Create the database 

### Connect using SQL tools
docker exec -it mock-housing-db /opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P y0urP4ss

### Create cxm database
CREATE DATABASE [mock-housing]
GO

#### Switch to mock-housing
USE [mock-housing]
GO

#### Create CXM user
CREATE LOGIN mockappuser WITH PASSWORD = 'y0urP4ss';
GO

### Create a new database user linked to the login name
CREATE USER mockappuser FOR LOGIN mockappuser;
GO

### Grant database ALTER permision to the user
GRANT ALTER To mockappuser
GO

### Grant database CONTROL permision to the user
GRANT CONTROL To mockappuser;
GO
EXIT

## Installing and running
### Create new image
docker commit -m "Base SQL Server database for mock housing app" -a "daleellis1983@gmail.com" mock-housing-db netcall/mock-housing-app-db

### Loging into docker

docker login

### Push

docker push netcall/mock-housing-app-db

## Run App
docker pull ellisd5/mock-housing-app:1.0

docker run -d --link mock-housing-db:netcall-housing-db -p 8088:8088 --name mock-housing-app ellisd5/mock-housing-app:1.0
