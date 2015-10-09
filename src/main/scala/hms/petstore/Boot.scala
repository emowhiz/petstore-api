/*
 * (C) Copyright 2010-2015 hSenid Mobile Solutions (Pvt) Limited.
 * All Rights Reserved.
 *
 * These materials are unpublished, proprietary, confidential source code of
 * hSenid Mobile Solutions (Pvt) Limited and constitute a TRADE SECRET
 * of hSenid Mobile Solutions (Pvt) Limited.
 *
 * hSenid Mobile Solutions (Pvt) Limited retains all title to and intellectual
 * property rights in these materials.
 */

package hms.petstore

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import com.escalatesoft.subcut.inject.{BindingModule, Injectable}
import hms.petstore.service.PetStoreService
import org.slf4j.{LoggerFactory, Logger}
import spray.can.Http
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._

object Boot extends App {

  val log:Logger=LoggerFactory.getLogger(Boot.getClass)
  // we need an ActorSystem to host our application in
  implicit val system = ActorSystem("on-spray-can")

  // create and start our service actor
  val service = system.actorOf(Props(classOf[PetStoreRoutesActor],ServiceModule), "pet-service")

  implicit val timeout = Timeout(5.seconds)
  // start a new HTTP server on port 8080 with our service actor as the handler
  IO(Http) ? Http.Bind(service, interface = system.settings.config.getString("app.interface"), port = system.settings.config.getInt("app.port"))
  log.info(system.settings.config.getString("app.banner"))
  log.info("Server started")


}
