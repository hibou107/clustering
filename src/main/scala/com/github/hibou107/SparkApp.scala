package com.github.hibou107

import org.apache.spark.{SparkConf, SparkContext}

object SparkApp {
  def main(args: Array[String]) {
    val logFile = "/mnt/c/Users/namng/workspaces/apps/spark-2.4.0-bin-hadoop2.7/README.md" // Should be some file on your system
    val conf = new SparkConf().setAppName("Simple Application")
    val sc = new SparkContext(conf)
    val logData = sc.textFile(logFile, 2).cache()
    val numAs = logData.filter(line => line.contains("a")).count()
    val numBs = logData.filter(line => line.contains("b")).count()
    println("Lines with a: %s, Lines with b: %s".format(numAs, numBs))
  }
}