package de.simmft.agent.rest

import scala.collection.immutable.Map
import scala.concurrent.Await
import scala.concurrent.duration.DurationInt
import de.simmft.agent.json.JacksonJsonMapper
import dispatch.Defaults.executor
import dispatch.Http
import dispatch.as
import dispatch.implyRequestHandlerTuple
import dispatch.url
import dispatch.Req
import de.simmft.agent.config.MftAgentConfig
import MftAgentConfig._

class RestCall {
  def baseReq: Req = url($("mft-rest-url"))
}

trait AuthenticationViaOauth extends RestCall {
  private[this] def authHeader: Map[String,String] = {
    val token = OauthTokenManager.getValidToken
    return Map("Authorization" -> ("Bearer " + token))
  }

  abstract override def baseReq:Req = super.baseReq <:< authHeader
}

case class JobListEntry(uuid: String, name: String)

class RestCallJobList extends RestCall with AuthenticationViaOauth {
  def execute(): List[JobListEntry] =  { 
    val req = baseReq / "jobs" <<? Map ("mft-agent" -> $("mft-agent-id"))
    val futureResult = Http(req OK as.String)
    
    val resultJson = Await.result(futureResult, $("http-timeout-seconds").toInt seconds)

    val list = JacksonJsonMapper.mapper.readValue[List[JobListEntry]](resultJson)
    return list
  }
}

class RestCallPostTransfer

class RestCallPostFile

class RestCallRetrieveFile

