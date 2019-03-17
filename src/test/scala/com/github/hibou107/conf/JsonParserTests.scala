package com.github.hibou107.conf

import com.github.hibou107.models.BikeStation
import org.scalatest.{FlatSpec, Matchers}

class JsonParserTests extends FlatSpec with Matchers {
  it should "parse correctly when coordinates are in flat position" in {
    val input = "{\"id\":106,\"name\":\"106 - ORLEIGH ST / HOOGLEY ST\",\"address\":\"Orleigh St / Hoogley St\",\"latitude\":-27.489807,\"longitude\":153.00241,\"position\":\"France\"}"
    BikeStation.parse(input) should matchPattern {
      case Right(BikeStation(106, _, _, -27.489807, 153.00241, Some("France"))) =>
    }
  }

  it should "parse correctly when coordinates are in nested position" in {
    val input = "{\"id\":32,\"name\":\"32 - TANK ST / GEORGE ST\",\"address\":\"Tank St / George St\",\"coordinates\":{\"latitude\":-27.468678,\"longitude\":153.0198}}"
    BikeStation.parse(input) should matchPattern {
      case Right(BikeStation(32, _, _, -27.468678, 153.0198, None)) =>
    }
  }

}
