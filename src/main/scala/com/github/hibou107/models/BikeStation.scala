package com.github.hibou107.models
import io.circe.Decoder.Result
import io.circe._
import io.circe.parser._
import cats.syntax.either._

case class BikeStation(id: Int, name: String, address: String, latitude: Double, longitude: Double, position: Option[String])

object BikeStation {

  implicit val docerderBikeStation: Decoder[BikeStation] = new Decoder[BikeStation] {
    override def apply(c: HCursor): Result[BikeStation] = {
      for {
        id <- c.downField("id").as[Int]
        name <- c.downField("name").as[String]
        address <- c.downField("address").as[String]
        position <- c.downField("position").as[Option[String]]
        coordinates <- if (c.downField("coordinates").succeeded) {
              val cursor = c.downField("coordinates")
              for {
                latitude <- cursor.downField("latitude").as[Double]
                longitude <- cursor.downField("longitude").as[Double]
              } yield (latitude, longitude)
            } else {
              for {
                latitude <- c.downField("latitude").as[Double]
                longitude <- c.downField("longitude").as[Double]
              } yield (latitude, longitude)
              }
        (latitude, longitude) = coordinates
      } yield BikeStation(id, name, address, latitude, longitude, position)
    }
  }

  def parse(input: String): Either[Error, BikeStation] = {
    decode[BikeStation](input)
  }
}