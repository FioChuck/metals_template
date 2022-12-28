package example

import scala.math.random

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.StructField
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.IntegerType
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.types.StructType

object main {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder
      .appName("Spark Pi")
      .config("spark.master", "local[*]")
      .getOrCreate()

    // val bucket = "[bucket]"
    // spark.conf.set("temporaryGcsBucket", bucket)

    // Load data in from BigQuery. See
    // https://github.com/GoogleCloudDataproc/spark-bigquery-connector/tree/0.17.3#properties
    // for option information.
    val df =
      (spark.read
        .format("bigquery")

        // `bigquery-public-data.stackoverflow.users`
        .option("table", "bigquery-public-data.stackoverflow.users")
        .load()
        .cache())

    // print(df.count())
    val df2 = df.repartition(10)
    df2.show(30)

    print("total row count" + df2.count())

  }
}
