#!/usr/bin/env bash

/mnt/c/Users/namng/workspaces/apps/spark-2.4.0-bin-hadoop2.7/bin/spark-submit \
--class com.github.hibou107.SparkApp \
--master local[4] \
/mnt/c/Users/namng/workspaces/clustering/target/scala-2.11/clustering-assembly-0.1.jar \
action=predict \
inputFile=data-test/sample.json \
modelPath=data-test/model \
outputFile=data-test/result.json \
