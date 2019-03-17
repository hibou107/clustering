package com.github.hibou107.process

import com.github.hibou107.SparkApp
import com.github.hibou107.conf.TrainingConf
import com.github.hibou107.models.BikeStation
import org.apache.log4j.Logger
import org.apache.spark.mllib.clustering.KMeans
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

class DataTrainer(conf: TrainingConf) extends Serializable {

  @transient private lazy val logger = Logger.getLogger(SparkApp.getClass.toString)

  private def processInvalidData(invalidData: RDD[io.circe.Error]): Unit = {
    val totalErrors = invalidData.count()
    logger.error(s"There are $totalErrors in input")
  }
  def trainData(): Unit = {
    val spark = SparkSession
      .builder()
      .appName("Clustering program")
      .getOrCreate()

    val parsedBikeStations = spark.read.textFile(conf.inputFile).rdd.map(BikeStation.parse)
    parsedBikeStations.foreach(println)
    val validBikeStations = parsedBikeStations.collect {
      case Right(station) =>
        logger.info(station.toString)
        station
    }

    val parsedData = validBikeStations.map(station => Vectors.dense(Array(station.latitude, station.longitude)))

    val clusters = KMeans.train(parsedData, conf.nbClusters, conf.nbIterations)
    clusters.save(parsedData.sparkContext, conf.modelPath)
    val invalidData = parsedBikeStations.collect {
      case Left(error) => error
    }
    processInvalidData(invalidData)
  }
}
