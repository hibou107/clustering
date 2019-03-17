package com.github.hibou107


import cats.data.Validated.{Invalid, Valid}
import com.github.hibou107.conf.{ConfParser, PredictionConf, TrainingConf}
import com.github.hibou107.process.{DataPrediction, DataTrainer}
import org.apache.log4j.Logger


object SparkApp {

  @transient private lazy val logger = Logger.getLogger(SparkApp.getClass.toString)

  def main(args: Array[String]) {
    val validatedConf = ConfParser.parseConf(args.toList)
    validatedConf match {
      case Valid(conf: TrainingConf) =>
       val dataTrainerProcessor = new DataTrainer(conf)
        dataTrainerProcessor.trainData()
      case Valid(conf: PredictionConf) =>
        val dataPredictionProcessor = new DataPrediction(conf)
        dataPredictionProcessor.predict()
      case Invalid(e) => logger.error(s"Error: $e")
    }
  }
}