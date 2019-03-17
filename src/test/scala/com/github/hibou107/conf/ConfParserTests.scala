package com.github.hibou107.conf

import cats.data.Validated.{Invalid, Valid}
import org.scalatest.{FlatSpec, Matchers}


class ConfParserTests extends FlatSpec with Matchers {

  it should "read the valid configuration" in {
    val conf = List("datapath=/data/file.txt")
    ConfParser.parseConf(conf) should matchPattern {
      case Valid(Conf("/data/file.txt")) =>
    }
  }

  it should "read the valid configuration even with white spaces before and after" in {
    val conf = List("   datapath =   /data/file.txt  ")
    ConfParser.parseConf(conf) should matchPattern {
      case Valid(Conf("/data/file.txt")) =>
    }
  }

  it should "return invalid config when config is missing" in {
    val conf = List("   data =   /data/file.txt  ")
    ConfParser.parseConf(conf) should matchPattern {
      case Invalid(_) =>
    }
  }
}
