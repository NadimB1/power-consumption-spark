import java.util.Properties
import org.apache.kafka.clients.producer._
import scala.io.Source

object KafkaProducerApp {
  def main(args: Array[String]): Unit = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](props)
    val topic = "first"

    val sourceFile = "resources/power_consumption_data.csv"
    val lines = Source.fromFile(sourceFile).getLines().toList

    //var mlk = 0
    for (line <- lines) {
      //mlk = mlk + 1

      val record = new ProducerRecord[String, String](topic, "key", line)
      producer.send(record)

      //if (mlk % 10 == 0) {
      Thread.sleep(1000) // sleep for 1 second
      //}
    }

    producer.close()
  }
}
