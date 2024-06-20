# Real-Time Power Consumption Analysis System
## Project Description

This system is designed to capture and process real-time power consumption data using Apache Kafka and Apache Spark. The system consists of three main components:

* KafkaProducerApp: Generates data by reading from a CSV file and publishes it to a Kafka topic.
* KafkaConsumerApp: Consumes the data from Kafka, processes it, and stores it in an SQLite database.
* StructuredStreamingApp: Analyzes the data using Spark's structured streaming capabilities to perform real-time analytics.

# Getting Started

## Prerequisites
* Java 8 or later
* Scala
* Apache Kafka
* Apache Spark
* SQLite

##Installation
* Install the required software, then clone this repository to your local machine.

# Running the System
Start Kafka and create the necessary topics.
Run KafkaProducerApp to begin data production.
Start KafkaConsumerApp to consume and process the data.
Execute StructuredStreamingApp to perform real-time analytics.

# Usage
This system is ideal for monitoring and analyzing power consumption data in real-time. It can be extended or modified to fit various use cases involving large data streams.

# License
This project is proprietary and may require permissions to use or modify.

# Acknowledgments
The Spark and Kafka communities for their excellent resources and support.
