package com.github.hibou107.conf

import cats.data.Validated.Valid
import cats.data.ValidatedNec
import cats.implicits._

case class Conf(fileName: String)

case class ConfigError(err: String)


object ConfParser {
  type Validation[A] = ValidatedNec[ConfigError, A]

  private def read[A](args: Map[String, String], fieldName: String)(implicit validated: String => Validation[A]): Validation[A] = {
    args get fieldName match {
      case None => ConfigError(s"Field $fieldName not found").invalidNec
      case Some(field) => validated(field)
    }
  }
  def parseConf(args: List[String]): Validation[Conf] = {
    val argsList = args.map { arg =>
      val lists = arg.split("=").toList
      lists.map(_.trim) match {
        case first :: second :: Nil => (first, second).validNec
        case invalidConfig => ConfigError(s"Invalid config format $invalidConfig").invalidNec
      }
    }.sequence
    val argsMap = argsList.map(_.toMap)
    argsMap.andThen(am => read[String](am, "datapath")(Valid(_))).map(Conf)
  }
}
