package com.github.hibou107.models

import io.circe.{Encoder, Json}

case class ClassifiedBikeStation(id: Int, group: Int)

object ClassifiedBikeStation {
  implicit val classCastExceptionEncoder: Encoder[ClassifiedBikeStation] = new Encoder[ClassifiedBikeStation] {
    override def apply(a: ClassifiedBikeStation): Json = Json.obj(
      ("id", Json.fromInt(a.id)),
      ("group", Json.fromInt(a.group))
    )
  }
}