import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka010._
import org.apache.spark.SparkConf
import org.apache.kafka.common.serialization.StringDeserializer
import java.sql.{Connection, DriverManager, PreparedStatement}

object KafkaConsumerApp {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("KafkaConsumerApp").setMaster("local[*]")
    val ssc = new StreamingContext(sparkConf, Seconds(1))

    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "localhost:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "use_a_separate_group_id_for_each_stream",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )

    val topics = Array("first")
    val stream = KafkaUtils.createDirectStream[String, String](
      ssc,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](topics, kafkaParams)
    )

    val lines = stream.map(record => record.value)
        lines.foreachRDD { rdd =>
          rdd.foreachPartition { partitionOfRecords =>
            val conn = DriverManager.getConnection("jdbc:sqlite:/home/nadim/data.db")
            val statement = conn.prepareStatement("INSERT INTO power_data VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")
            partitionOfRecords.foreach { record =>
              val parts = record.split(",")
              for (i <- 0 until parts.length) {
                statement.setString(i + 1, parts(i))
              }
              statement.executeUpdate()
            }
            statement.close()
            conn.close()
          }
        }

        ssc.start()
        ssc.awaitTermination()
      }
    }
