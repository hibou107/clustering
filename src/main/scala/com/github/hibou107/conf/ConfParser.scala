package com.github.hibou107.conf

import cats.data.ValidatedNec
import cats.implicits._

import scala.language.implicitConversions
import scala.util.{Failure, Success, Try}

sealed trait AppConfig
case class TrainingConf(inputFile: String, nbIterations: Int, nbClusters: Int, modelPath: String) extends AppConfig
case class PredictionConf(inputFile: String, modelPath: String, outputFile: String) extends AppConfig

case class ConfigError(err: String)


object ConfParser {

  type Validation[A] = ValidatedNec[ConfigError, A]

  private implicit def readString(input: String): Validation[String] = input.validNec
  private implicit def readInt(input: String): Validation[Int] = Try(input.toInt) match {
    case Success(int) => int.validNec
    case Failure(exception) => ConfigError(s"Cannot read $input: ${exception.toString}").invalidNec
  }


  private def read[A](args: Map[String, String], fieldName: String)(implicit validated: String => Validation[A]): Validation[A] = {
    args get fieldName match {
      case None => ConfigError(s"Field $fieldName not found").invalidNec
      case Some(field) => validated(field)
    }
  }

  private def readTrainingConf(args: Map[String, String]): Validation[TrainingConf] = {
    (
      read[String](args, "inputFile"),
      read[Int](args, "nbIterations"),
      read[Int](args, "nbClusters"),
      read[String](args, "modelPath")
    ).mapN(TrainingConf)
  }

  private def readPredictConf(args: Map[String, String]): Validation[PredictionConf] = {
    (
      read[String](args, "inputFile"),
      read[String](args, "modelPath"),
      read[String](args, "outputFile")
    ).mapN(PredictionConf)
  }

  private def readConf(input: Map[String, String]): Validation[AppConfig] = {
    input get "action" match {
      case Some("training") => readTrainingConf(input)
      case Some("predict") => readPredictConf(input)
      case Some(unknownAction) => ConfigError(s"Unknown action $unknownAction, should be 'training' or 'predict'").invalidNec
      case None => ConfigError(s"You must provide action (training or predict)").invalidNec
    }
  }

  def parseConf(args: List[String]): Validation[AppConfig] = {
    val argsList = args.map { arg =>
      val lists = arg.split("=").toList
      lists.map(_.trim) match {
        case first :: second :: Nil => (first, second).validNec
        case invalidConfig => ConfigError(s"Invalid config format $invalidConfig").invalidNec
      }
    }.sequence
    val argsMap = argsList.map(_.toMap)
    argsMap.andThen(readConf)
  }
}
