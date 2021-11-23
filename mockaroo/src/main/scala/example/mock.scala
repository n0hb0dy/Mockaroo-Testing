package example
/*API Imports*/
import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.client.ClientProtocolException
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient




object mock {

implicit val apiKey: String = "$apiKey"
implicit var urlGetCount: Int = 1

def getRestContent(url: String): String = {

    val httpClient = new DefaultHttpClient()
    val httpResponse = httpClient.execute(new HttpGet(url))
    val entity = httpResponse.getEntity()
    
    var content = ""
    if (entity != null) {
        val inputStream = entity.getContent()
        content = scala.io.Source.fromInputStream(inputStream).getLines.mkString("\n")
        inputStream.close
    }

    httpClient.getConnectionManager().shutdown()
    return content
}



  def main(args: Array[String]) {

    val apiKey = "2f9538c0"

    // Arrays of strings for each document
    val recruiterStr = getRestContent(s"https://my.api.mockaroo.com/Recruiters?key=$apiKey").split("\n").toSeq
    val qlStr = getRestContent(s"https://my.api.mockaroo.com/Qualified_Lead?key=$apiKey").split("\n").toSeq
    val screenerStr = getRestContent(s"https://my.api.mockaroo.com/Screeners?key=$apiKey").split("\n").toSeq
    val offerStr = getRestContent(s"https://my.api.mockaroo.com/Offers?key=$apiKey").split("\n").toSeq
    val sceeningStr = getRestContent(s"https://my.api.mockaroo.com/Screening?key=$apiKey").split("\n").toSeq
    val caStr = getRestContent(s"https://my.api.mockaroo.com/Contact_Attempts?key=$apiKey").split("\n").toSeq

    //Checking
    recruiterStr.foreach(println)
    qlStr.foreach(println)
    screenerStr.foreach(println)
    offerStr.foreach(println)
    sceeningStr.foreach(println)
    caStr.foreach(println)

  }
}