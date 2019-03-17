# Introduction
Spark program that can do 2 things:
 * read data (the program can read 2 existing formats)
 * train a clustering algorithm
 * using an existing model to predict new data
 
# How to compile
In order to compile, make sure that you have already 
* JDK 8.0 installed
* sbt 
* If you have memory problem while compiling, make sure to set JAVA_OPTS=-Xmx2G

How to compile
Go the root path of the project
```
sbt assembly
```

# How to run the program

run `run_model.sh`

You can modify the script to change the default value

* action=training 
* inputFile=data-test/sample.json
* nbIterations=10 
* nbClusters=5 
* modelPath=data-test/model

When the program finishes, run `run_prediction.sh` to make prediction on new data

* action=predict 
* inputFile=data-test/sample.json 
* modelPath=data-test/model 
* outputFile=data-test/result.json 

# Backlogs

## Jenkins automation
The building process and test must be automated by Jenkins

## Deployment automation
This can be a step from Jenkins, the deployment of master branch should automated

## Better errors handling
For the moment the are only one log message to count the total number of invalid input data
There should be better way to persist these errors (kibana, database, hdfs ...) in order to be investigated later

## Result Persist
For the moment the result is collected to the driver and persisted in a simple text file. But this data should
be in a cluster (hdfs, database)

## Model updated
Find an automated process that can ease the updating of the model

