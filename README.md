# TrafficLight 🚦

Personal challenge to implement *Fs2* & *Akka Streams*.

## Functional Requirements

The main system is thought for domain data that needs a state system.

*This specific ms is for normalize the data from external system into the DB.*

A Street represents the domain data.

System get the saved street with YELLOW Traffic Light from DB and ask external api the new status. 
If the YELLOW status has changed, produce a message in Kafka with the new status.

A consumer runs in parallel to grab the change and update the state of the semaphore.

## Project structure

    ├── infrastructure
    │   ├── external_data_mock
    │   │   ├── initializerJson.json
    │   │   └── mockserver.properties
    │   ├── mysql
    │   │   ├── createTrafficLight.sql
    │   │   └── mysqld.cnf
    │   ├── TrafficLight-fs2
    │   │   └── src
    │   │   │   ├── main
    │   │   │   └── test
    │   ├── TrafficLight-akkaStream
    │   │   └── src
    │   │   │   ├── main
    │   │   │   │   └── scala
    │   │   │   │   │   ├── application
    │   │   │   │   │   ├── domain
    │   │   │   │   │   ├── infrastructure
    │   │   │   │   │   └── Main.scala
    │   │   │   └── test
    └── README.md

## How to use

**1- Set environment**:

    docker-compose -f ./infrastructure/docker-compose.traffic_light.yml down --remove-orphans &&
    docker-compose -f ./infrastructure/docker-compose.traffic_light.yml up -d

**2- Start Consumer**:

    sbt ...

**3- Run Review Process with Fs2**:

    sbt trafficLightsFs2/run

**4- Run Review Process with Akka Stream**:

    sbt trafficLightsAkkaStreams/run