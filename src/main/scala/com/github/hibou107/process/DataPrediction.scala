package com.github.hibou107.process

import com.github.hibou107.conf.PredictionConf
import com.github.hibou107.models.{BikeStation, ClassifiedBikeStation}
import org.apache.spark.mllib.clustering.KMeansModel
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.sql.SparkSession
import java.io._
import io.circe.syntax._
class DataPrediction(conf: PredictionConf) extends Serializable {
  def predict(): Unit = {
    val spark = SparkSession
      .builder()
      .appName("Clustering program")
      .getOrCreate()
    val sameModel = KMeansModel.load(spark.sparkContext, conf.modelPath)
    val parsedBikeStations = spark.read.textFile(conf.inputFile).rdd.map(BikeStation.parse)
    val validBikeStations = parsedBikeStations.collect {
      case Right(station) =>
        station
    }

    val classifiedData = validBikeStations.map { station =>
      val vector = Vectors.dense(Array(station.latitude, station.longitude))
      val result = sameModel.predict(vector)
      ClassifiedBikeStation(station.id, result)
    }

    val collectedResult = classifiedData.collect()   // TODO find anything better to store result (database or HDFS)

    val w = new BufferedWriter(new FileWriter(conf.outputFile))
    try {
      collectedResult.foreach { result =>
        w.write(result.asJson.noSpaces + "\n")
      }

    } finally {
      w.close()
    }

  }
}
