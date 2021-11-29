package example
 //moving some code over to this file for neatness - Jared
import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.client.ClientProtocolException
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient

object API {

    def getRestContent(url: String): String = {

        val httpClient = new DefaultHttpClient()
        val httpResponse = httpClient.execute(new HttpGet(url))
        val entity = httpResponse.getEntity()
        
        var content = ""
        if (entity != null) {
            val inputStream = entity.getContent()
            content = scala.io.Source.fromInputStream(inputStream)
                        .getLines
                        .mkString("")
                        .replace("{", "")
                        .replace("[", "")
                        .replace("}]", "")          // hopefully no hierarchies
            inputStream.close           
        }       

        httpClient.getConnectionManager().shutdown()
        return content
    }

   
    final val apiKey = "2f9538c0"

    // Calls to Mockaroo API to generate new table
    
    def recruiterData(): Array[String] = return getRestContent(s"https://my.api.mockaroo.com/Recruiters?key=$apiKey").split("},")

    def qlData(): Array[String] = return getRestContent(s"https://my.api.mockaroo.com/Qualified_Lead?key=$apiKey").split("},")

    def screenerData(): Array[String] = return getRestContent(s"https://my.api.mockaroo.com/Screeners?key=$apiKey").split("},")

    def offerData(): Array[String] = return getRestContent(s"https://my.api.mockaroo.com/Offers?key=$apiKey").split("},")

    def screeningData(): Array[String] = return getRestContent(s"https://my.api.mockaroo.com/Screening?key=$apiKey").split("},")

    def caData(): Array[String] = return getRestContent(s"https://my.api.mockaroo.com/Contact_Attempts?key=$apiKey").split("},")



    //Functions from repeating lines of code

    // def caCall() : Unit = {
    //     if(typeCounter(typeMsg) != maxSize(typeMsg)) {
    //         stringOut = caStrArr(typeCounter(typeMsg));
    //         println(stringOut);
    //         typeCounter(typeMsg) = typeCounter(typeMsg) + 1; // Going down the array for each entry
    //         var nextQ = rand.nextInt(100);
    //         if(nextQ > 40) // 40% chance of another contact before proceeding
    //         typeMsg = typeMsg + 1;
    //     }else{
    //         //caStrArr = caData();
    //         stringOut = caStrArr(0);
    //         typeCounter(typeMsg) = 1;
    //         var nextQ = rand.nextInt(100);
    //         if(nextQ > 40) // 40% chance of another contact before proceeding
    //         typeMsg = typeMsg + 1;
    //     }
    // }


}








/* CODE PURGATORIO
    Arrays of strings for each document
    val recruiterArr = getRestContent(s"https://my.api.mockaroo.com/Recruiters?key=$apiKey").split("\n")
    val qlArr = getRestContent(s"https://my.api.mockaroo.com/Qualified_Lead?key=$apiKey").split("\n")
    val screenerArr = getRestContent(s"https://my.api.mockaroo.com/Screeners?key=$apiKey").split("\n")
    val offerArr = getRestContent(s"https://my.api.mockaroo.com/Offers?key=$apiKey").split("\n")
    val sceeningArr = getRestContent(s"https://my.api.mockaroo.com/Screening?key=$apiKey").split("\n")
    val caArr = getRestContent(s"https://my.api.mockaroo.com/Contact_Attempts?key=$apiKey").split("\n")
*/