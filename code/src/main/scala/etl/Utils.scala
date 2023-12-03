package etl

import org.apache.spark.sql.{DataFrame, SparkSession}

object Utils {
  val keySource = "source"
  val optSource = "--" + keySource
  val keyCleanOutput = "clean-output"
  val optCleanOutput = "--" + keyCleanOutput
  val keyProfileOutput = "profile-output"
  val optProfileOutput = "--" + keyProfileOutput
  val keyIntermediateOutput = "intermediate-output"
  val optIntermediateOutput = "--" + keyIntermediateOutput

  def parseOpts(map: Map[String, Any], list: List[String]): Map[String, Any] = {
    list match {
      case Nil => map
      case `optSource` :: value :: tail =>
        parseOpts(map ++ Map(keySource -> value), tail)
      case `optCleanOutput` :: value :: tail =>
        parseOpts(map ++ Map(keyCleanOutput -> value), tail)
      case `optProfileOutput` :: value :: tail =>
        parseOpts(map ++ Map(keyProfileOutput -> value), tail)
      case `optIntermediateOutput` :: value :: tail =>
        parseOpts(map ++ Map(keyIntermediateOutput -> value), tail)
      case unknown :: _ =>
        println("Unknown option " + unknown)
        map
    }
  }

  def loadRawDataCSV(spark: SparkSession, path: String, headers: Boolean = true, inferSchema: Boolean = true): DataFrame = {
    spark.read
      .option("header", headers)
      .option("inferSchema", inferSchema)
      .csv(path)
  }
}

//  Making changes for reading parquet file
  def loadRawDataParquet(spark: SparkSession, path: String, year : Int): DataFrame= {
        spark.read.parquet(s"$path/$year/*/*.parquet")
  }