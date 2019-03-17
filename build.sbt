name := "clustering"

version := "0.1"

scalaVersion := "2.11.11"
val sparkVersion = "2.4.0"

libraryDependencies += "org.apache.spark" %% "spark-core" % sparkVersion % "provided"
libraryDependencies += "org.apache.spark" %% "spark-sql" % sparkVersion % "provided"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.5"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"
libraryDependencies += "org.typelevel" %% "cats-core" % "1.6.0"

scalacOptions += "-Ypartial-unification"

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}


val circeVersion = "0.10.0"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

// https://mvnrepository.com/artifact/org.apache.spark/spark-mllib
libraryDependencies += "org.apache.spark" %% "spark-mllib" % sparkVersion % "provided"

//libraryDependencies += "org.apache.logging.log4j" % "log4j-api" % "2.11.2"
//libraryDependencies += "org.apache.logging.log4j" % "log4j-core" % "2.11.2"

libraryDependencies += "org.slf4j" % "slf4j-log4j12" % "1.2"
