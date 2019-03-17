#!/usr/bin/env bash

$HADOOP_HOME/bin/spark-submit \
--class com.github.hibou107.SparkApp \
--master local[4] \
target/scala-2.11/clustering-assembly-0.1.jar \
action=predict \
inputFile=data-test/sample.json \
modelPath=data-test/model \
outputFile=data-test/result.json \
