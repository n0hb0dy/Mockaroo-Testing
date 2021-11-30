package example

import org.apache.kafka.common.serialization.StringSerializer

// Kafka imports
import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

//Project imports
import example.API._
import example.definitions._

object mock extends App {

    // Declartion of properties
    val props2: Properties = new Properties();
    
    props2.put("bootstrap.servers", "sandbox-hdp.hortonworks.com:6667")
    props2.put(
      "key.serializer",
      "org.apache.kafka.common.serialization.StringSerializer"
    )
    props2.put(
      "value.serializer",
      "org.apache.kafka.common.serialization.StringSerializer"
    )
    props2.put("acks", "all")

    val producer = new KafkaProducer[String, String](props2);

    try {
      while(!stop) {
        println("Next Iteration:\n") // Testing
          var numMsg = rand.nextInt(40) + 10; // Random number stating the amount of msgs generated from 10 to 50 max
          for(i <- 0 until numMsg) {
            println("We are at : " + msgCounter) // Testing purposes
              if(typeMsg == 0) { // For QL
                println("Generating QL...")
                ql_id = rand.nextInt(10000000) + 400; // Offset will create the range of 400-10,000,400 (large number to decrease the odds)
                if(typeCounter(typeMsg) != maxSize(typeMsg)-1) {
                    stringOut = qlStrArr(typeCounter(typeMsg));
                    stringOut = stringOut.replace("\"id\":1","\"id\":" + ql_id)
                    typeCounter(typeMsg) = typeCounter(typeMsg) + 1; // Going down the array for each entry
                }
                else {
                  //qlStrArr = qlData(); // Reset the array
                  stringOut = qlStrArr(0);
                  stringOut = stringOut.replace("\"id\":1","\"id\":" + ql_id) // Replaces the current ID with the proper one
                  typeCounter(typeMsg) = 1; // resets the counter
                }
                typeMsg = typeMsg + 1;
              }
              else if(typeMsg == 1) { // For CA
                println("Generating CA...")
                if(typeCounter(typeMsg) != maxSize(typeMsg)-1) {
                  stringOut = messageGenerator(caStrArr)
                }else{
                  //caStrArr = caData();
                  typeCounter(typeMsg) = 0;
                  stringOut = messageGenerator(caStrArr);
                }
                typeMsg = chanceNextMessage(typeMsg)
              }
              else if(typeMsg  == 2) { // For Screening
                println("Generating Screening...")
                if(typeCounter(typeMsg) != maxSize(typeMsg)-1) {
                  stringOut = messageGenerator(screeningStrArr)
                }else{
                  //screeningStrArr = screeningData();
                 typeCounter(typeMsg) = 0;
                  stringOut = messageGenerator(screeningStrArr);
                }
                typeMsg = chanceNextMessage(typeMsg)
              }
              else if(typeMsg == 3) { // For offers
                println("Generating Offers...")
                if(typeCounter(typeMsg) != maxSize(typeMsg)-1) {
                  stringOut = messageGenerator(offerStrArr)
                } else{
                  //offerStrArr = offerData();
                  typeCounter(typeMsg) = 0;
                  stringOut = messageGenerator(offerStrArr);
                }
                typeMsg = chanceNextMessage(typeMsg)%4
              }

              //Sending Data (logic of sending needs change)
              if(typeMsg == 0) // Corrects the previous increment
                sentType = 3;
              else
                  sentType = typeMsg - 1;
              val msg = new ProducerRecord[String, String]( // Lets start over with this one. go for it
                  msgTypes(sentType), // Topic for the msg
                  msgCounter.toString, // ID for the msg
                  stringOut // Json string
              )
              producer.send(msg); // sends the msg to the topic
              producer.flush(); // Might fix the sending msg?
              // val metadata = producer.send(msg) // Accidentally sending repeated msgs
              // printf(
              //   s"sent record(key=%s value=%s)\n",
              //   msg.key(),
              //   msg.value()
              // )
              
              msgCounter = msgCounter + 1 // update global msg counter
          }
          println("Exit? yes(1) ") // Testin
          var exit = scala.io.StdIn.readInt();
          if(exit == 1)
            stop = true;

          Thread.sleep(2000); // Wrong side, increased runtime dramatically since it was inside the nested loop
      }
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      producer.close()
    }
}

/*
Left to do:
- Fix bugs
- Speed problems? (are we gonna meet the quota?)
- Linking each other for the IDS (necessary?)
- Logic to decide if they get an offer
- Linking dates with each other? (necessary?)

Bugs:
- Some data is missing
- Order misbehavement

*/


//Resting Corner (for tired cursors !!!) wtf
//+_+_+_| |_+_+_+//
//+_+_+_| |_+_+_+//
//+_+_+_| |_+_+_+//
//+_+_+_| |_+_+_+//
//+_+_+_| |_+_+_+//
//+_+_+_| |_+_+_+//

// --------------------
// ~(^_^)~   ~(^_^)~
//(0-o)
//(╯°□°)╯︵ ┻━┻