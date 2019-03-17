#!/usr/bin/env bash

$HADOOP_HOME/bin/spark-submit \
--class com.github.hibou107.SparkApp \
--master local[4] \
target/scala-2.11/clustering-assembly-0.1.jar \
action=training \
inputFile=data-test/sample.json \
nbIterations=10 \
nbClusters=5 \
modelPath=data-test/model
