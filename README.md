# gcp-dataflow-with-java

https://medium.com/@mrayandutta/getting-started-with-google-cloud-dataflow-and-apache-beam-in-java-fcf3530a6e4b

## Activation:

To activate a specific profile during the Maven build, you can use the -P flag followed by the profile ID. For example:

For local development: 
```
mvn clean install -Pdirect
```

For cloud deployment: 
```
mvn clean install -Pdataflow
```
By using profiles, you can easily switch between different configurations and dependencies, making your development process more flexible and efficient.

---

## Local

set up environment variables for input and output file paths, main class name, and the runner type.

```
export INPUT_FILE=C:\\Users\\noppo\\IdeaProjects\\gcp-dataflow-with-java\\src\\main\\resources\\input\\input.csv \
export OUTPUT_FILE=C:\\Users\\noppo\\IdeaProjects\\gcp-dataflow-with-java\\src\\main\\resources\\output\\output.csv \
export MAIN_CLASS_NAME=dataflowsamples.SimpleFilterJob \
export RUNNER=DirectRunner
```

Create the project jar with the following maven command :

```
mvn compile exec:java \
-P direct \
-Dexec.mainClass=$MAIN_CLASS_NAME \
-Dexec.cleanupDaemonThreads=false \
-Dexec.args=" \
--runner=$RUNNER \
--inputFile=$INPUT_FILE \
--outputFile=$OUTPUT_FILE "
```

---

## GCP

set following variables with single command:

```
export PROJECT_ID=phd-e2d-gameanalytics \
export REGION='asia-southeast1' \
export BUCKET=gs://word-count-logs \
export MAIN_CLASS_NAME=dataflowsamples.SimpleFilterJob \
export RUNNER=DataflowRunner
```

Create the project jar with the following maven command :

```
mvn clean package -Pdataflow
```

We compile and run this program using Maven compile exec:java as before.

```
mvn compile exec:java \
-Pdataflow \
-Dexec.mainClass=$MAIN_CLASS_NAME \
-Dexec.cleanupDaemonThreads=false \
-Dexec.args=" \
--project=$PROJECT_ID \
--region=$REGION \
--stagingLocation=$BUCKET/stage \
--tempLocation=$BUCKET/temp \
--inputFile=$BUCKET/input/input.csv \
--outputFile=$BUCKET/output/output.csv \
--runner=$RUNNER"
```