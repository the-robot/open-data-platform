# Use the official Apache Spark base image
FROM apache/spark:3.5.0

# Copy your JAR files from the host to /opt/libs in the container
COPY ./libs/* /opt/spark/jars/

# Entry command to start the Spark worker
CMD ["/opt/spark/bin/spark-class", "org.apache.spark.deploy.worker.Worker", "spark://odp-spark-master:7077"]
