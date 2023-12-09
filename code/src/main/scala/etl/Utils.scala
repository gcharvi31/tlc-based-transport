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

  val keyNeighborsDistanceInput = "ns-dis-input"
  val optNeighborsDistanceInput = "--" + keyNeighborsDistanceInput
  val keyTLCInput = "tlc-input"
  val optTLCInput = "--" + keyTLCInput
  val keyPathFreqOutput = "path-freq-output"
  val optPathFreqOutput = "--" + keyPathFreqOutput

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

  def parseMainOpts(map: Map[String, Any], list: List[String]): Map[String, Any] = {
    list match {
      case Nil => map
      case `optNeighborsDistanceInput` :: value :: tail =>
        parseMainOpts(map ++ Map(keyNeighborsDistanceInput -> value), tail)
      case `optTLCInput` :: value :: tail =>
        parseMainOpts(map ++ Map(keyTLCInput -> value), tail)
      case `optPathFreqOutput` :: value :: tail =>
        parseMainOpts(map ++ Map(keyPathFreqOutput -> value), tail)
      case unknown :: _ =>
        println("Unknown option " + unknown)
        map
    }
  }

  def loadRawDataCSV(spark: SparkSession, path: String, headers: Boolean = true, inferSchema: Boolean = true,
                     delimiter: String = ","): DataFrame = {
    spark.read
      .option("header", headers)
      .option("inferSchema", inferSchema)
      .option("delimiter", delimiter)
      .csv(path)
  }

  //  Making changes for reading parquet file
  def loadRawDataParquet(spark: SparkSession, path: String, year: Int): DataFrame = {
    spark.read.parquet(s"$path/$year/*/*.parquet")
  }

  def loadintermediateData(spark: SparkSession, path: String, year: Int, cab: String): DataFrame={
    spark.read.parquet(s"$path/${cab}/${year}/*.parquet")
  }
}
