package de.simmft.agent.json

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import scala.reflect.runtime.universe._
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import com.fasterxml.jackson.databind.DeserializationFeature

object JacksonJsonMapper {
     val mapper = new ObjectMapper with ScalaObjectMapper
     mapper.registerModule(DefaultScalaModule)
     mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false)
     
     def fromJson[T](json: String, clazz: Class[T]): T = {
       mapper.readValue(json, clazz)
     }
     
     def fromJson(json: String) : JsonNode = {
       mapper.readTree(json)
     }
}

trait JsonSerializable {
   def toJson() : String = JacksonJsonMapper.mapper.writeValueAsString(this)
}

case class Foo(uuid:String,name:String) extends JsonSerializable
