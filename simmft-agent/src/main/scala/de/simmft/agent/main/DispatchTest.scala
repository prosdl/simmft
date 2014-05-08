package de.simmft.agent.main

import java.util.Date
import scala.concurrent._
import scala.concurrent.duration._
import scala.util._
import dispatch._
import Defaults._
import de.simmft.agent.rest.RestCallGetToken
import de.simmft.agent.rest.RestCallJobList

case class OauthResponse(access_token: String, token_type: String, expires_in: Integer)

object DispatchTest {
  def main(args: Array[String]) {
    
    println(new RestCallJobList().execute toString)
    
//     val req = url("http://localhost:8080/oauth/token")
//     val post = req << Map("client_id" -> "test.mft-agent-sender-1",
//                           "client_secret"->"test.mft-agent-sender-1",
//                           "grant_type" -> "client_credentials"
//                           )
     
     // client_id=test.mft-agent-sender-1&client_secret=test.mft-agent-sender-1&grant_type=client_credentials
//     val futureResult = Http  (post OK as.String)
//     println ("-" * 80)
//     
//     import JacksonJsonMapper._
//     futureResult onComplete {
//       case Success(oauthResponse) => println("OK" + oauthResponse); for (json <- futureResult) println(fromJson(json,classOf[OauthResponse]))
//       case Failure(x) => println("Failure: " + x)
//     }
     
//     val resultJson = Await.result(futureResult, 10 nanoseconds)
//     printf("Await: " + resultJson)
     
//     val json = ret()
//     printf("json: " + json)
//     val tokenResponse = JacksonJsonMapper.fromJson(json, classOf[OauthResponse])
//     printf(tokenResponse.toString)
  }
}