package de.simmft.agent.rest

import java.util.Date
import scala.concurrent._
import scala.concurrent.duration._
import de.simmft.agent.json.JacksonJsonMapper
import dispatch._
import dispatch.Defaults._
import de.simmft.agent.config.MftAgentConfig

case class OauthToken(access_token: String, token_type: String, expires_in: Integer)

class RestCallGetToken {
  def execute(clientId: String, clientSecret: String): OauthToken = {
    import MftAgentConfig._
    val req = url($("oauth-rest-url")) / "token"
    val post = req << Map(
      "client_id" -> clientId,
      "client_secret" -> clientSecret,
      "grant_type" -> "client_credentials")

    val futureResult = Http(post OK as.String)
    val resultJson = Await.result(futureResult, $("http-timeout-seconds").toInt seconds)

    return JacksonJsonMapper.fromJson(resultJson, classOf[OauthToken])
  }

}

object OauthTokenManager {
  var oauthToken: Option[OauthToken] = None;
  var retrieved: Date = null;

  def getValidToken: String = {
    if (!oauthToken.isDefined) {
      import MftAgentConfig._
      oauthToken = Some(new RestCallGetToken().execute($("oauth-client-id"), $("oauth-client-secret")))
      retrieved = new Date
    }
    // FIXME check expiration
    return oauthToken.get.access_token;
  }
}