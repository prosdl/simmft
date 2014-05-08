package de.simmft.agent.config

import com.typesafe.config.ConfigFactory

object MftAgentConfig {
  private val config = ConfigFactory.load()

  def apply() = config.getConfig("mft-agent")
  
  def $(x: String) = this.apply().getString(x)
}