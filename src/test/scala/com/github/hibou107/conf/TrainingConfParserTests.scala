package com.github.hibou107.conf

import cats.data.Validated.{Invalid, Valid}
import org.scalatest.{FlatSpec, Matchers}


class TrainingConfParserTests extends FlatSpec with Matchers {

  it should "read the valid training configuration" in {
    val conf = List(
      "action=training",
      "inputFile=/data/file.txt",
      "nbIterations=5",
      "nbClusters=6",
      "modelPath=/data/model"
    )
    ConfParser.parseConf(conf) should matchPattern {
      case Valid(TrainingConf("/data/file.txt", 5, 6, "/data/model")) =>
    }
  }

  it should "read the valid prediction configuration" in {
    val conf = List(
      "action=predict",
      "inputFile=/data/input",
      "modelPath=/data/model",
      "outputFile=/data/output"
    )
    ConfParser.parseConf(conf) should matchPattern {
      case Valid(PredictionConf("/data/input", "/data/model", "/data/output")) =>
    }
  }

  it should "read the valid configuration even with white spaces before and after" in {
    val conf = List(
      "    action  =training",
      "inputFile=  /data/file.txt",
      "nbIterations=  5",
      "nbClusters  =6  ",
      "  modelPath=/data/model"
    )
    ConfParser.parseConf(conf) should matchPattern {
      case Valid(TrainingConf("/data/file.txt", 5, 6, "/data/model")) =>
    }
  }

  it should "return invalid error when type is missing" in {
    val conf = List("   data =   /data/file.txt  ")
    ConfParser.parseConf(conf) should matchPattern {
      case Invalid(_) =>
    }
  }

}
