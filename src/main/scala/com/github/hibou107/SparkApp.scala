package com.github.hibou107


import cats.data.Validated.{Invalid, Valid}
import com.github.hibou107.conf.ConfParser
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.log4j.Logger


object SparkApp {
  @transient private lazy val logger = Logger.getLogger(SparkApp.getClass.toString)
  def main(args: Array[String]) {
    val validatedConf = ConfParser.parseConf(args.toList)
    validatedConf match {
      case Valid(appConfig) =>
        val logFile = appConfig.fileName
        val conf = new SparkConf().setAppName("Clustering application")
        val sc = new SparkContext(conf)
        val logData = sc.textFile(logFile, 2).cache()
        val numAs = logData.filter(line => line.contains("a")).count()
        val numBs = logData.filter(line => line.contains("b")).count()
        logger.info("Lines with a: %s, Lines with b: %s".format(numAs, numBs))
      case Invalid(e) => logger.error(s"Error: $e")
    }
  }
}