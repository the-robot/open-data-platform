# Open Data Platform
Open-source data platform and completely free for anyone to use under GNU GPL v3

<br/>

## Setup

You can simply run docker compose up to up development services.

- Spark
- Hadoop
- Redis

For Hadoop, once it is up, you need to go inside container, and run the following.

- Username is the your system user that will be running the Spring application.

```bash
# Create the /user/odp directory in HDFS
/usr/local/hadoop/bin/hadoop fs -mkdir /user/odp

# Change ownership of the /user/odp directory
/usr/local/hadoop/bin/hadoop fs -chown -R <username>:supergroup /user/odp
```

