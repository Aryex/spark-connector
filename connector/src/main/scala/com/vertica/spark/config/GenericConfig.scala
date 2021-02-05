// (c) Copyright [2018-2021] Micro Focus or one of its affiliates.
// Licensed under the Apache License, Version 2.0 (the "License");
// You may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.vertica.spark.config

import com.typesafe.scalalogging.Logger
import ch.qos.logback.classic.Level
import ch.qos.logback.classic

import scala.util.Try
import scala.util.Success
import scala.util.Failure

final case class LogProvider(logLevel: Level) {
  def getLogger(c: Class[_]): Logger = {
    val logger = Logger(c)
    Try{logger.underlying.asInstanceOf[classic.Logger].setLevel(logLevel) } match {
      case Failure(_) => logger.error("Could not set log level based on configuration.")
      case Success(_) => ()
    }

    logger
  }
}

/**
  * Generic config that all operations (read and write) share
  */
trait GenericConfig {
  val logLevel: Level = Level.ERROR
  val logProvider: LogProvider = LogProvider(logLevel)

  def getLogger(c: Class[_]): Logger = logProvider.getLogger(c)
}


