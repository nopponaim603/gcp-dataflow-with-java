# gcp-dataflow-with-java

https://medium.com/@mrayandutta/getting-started-with-google-cloud-dataflow-and-apache-beam-in-java-fcf3530a6e4b

## Local

export INPUT_FILE=C:\\Users\\noppo\\IdeaProjects\\gcp-dataflow-with-java\\src\\main\\resources\\input\\input.csv \
export OUTPUT_FILE=C:\\Users\\noppo\\IdeaProjects\\gcp-dataflow-with-java\\src\\main\\resources\\output\\output.csv \
export MAIN_CLASS_NAME=dataflowsamples.SimpleFilterJob \
export RUNNER=DirectRunner

mvn compile exec:java \
-P direct \
-Dexec.mainClass=$MAIN_CLASS_NAME \
-Dexec.cleanupDaemonThreads=false \
-Dexec.args=" \
--runner=$RUNNER \
--inputFile=$INPUT_FILE \
--outputFile=$OUTPUT_FILE "

---

## GCP
export PROJECT_ID=phd-e2d-gameanalytics \
export REGION='asia-southeast1' \
export BUCKET=gs://word-count-logs \
export MAIN_CLASS_NAME=dataflowsamples.SimpleFilterJob \
export RUNNER=DataflowRunner

mvn clean package -Pdataflow

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