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

import java.util.logging.Logger

import akka.actor.{ActorLogging, Actor}
import com.escalatesoft.subcut.inject.{Injectable, BindingModule}
import com.mongodb.casbah.Imports._
import hms.petstore.json.{PetProtocol, Tag, Pet, Category}
import hms.petstore.service.PetStoreService
import spray.http.HttpHeaders
import spray.httpx.SprayJsonSupport
import spray.httpx.marshalling.Marshaller
import spray.routing._

object PetStoreRoutesActor

class PetStoreRoutesActor(implicit val bindingModule: BindingModule) extends Actor with PetStoreRoutes with ActorLogging {

  def actorRefFactory = context

  def receive = {
    runRoute(petRoute)
  }
}

trait PetStoreRoutes extends HttpService with Injectable {

  val petOperations = inject[PetStoreService]

  val AccessControlAllowAll = HttpHeaders.RawHeader(
    "Access-Control-Allow-Origin", "*"
  )
  val AccessControlAllowHeadersAll = HttpHeaders.RawHeader(
    "Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept,Cache-Control, Pragma,X-Custom-Header"
  )
  val AccessControlAllowMethodsAll = HttpHeaders.RawHeader(
    "Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE,OPTIONS"
  )


  val petRoute = {
    import PetProtocol._
    import SprayJsonSupport._
    respondWithHeaders(AccessControlAllowAll, AccessControlAllowHeadersAll, AccessControlAllowMethodsAll) {
      path("pet") {
        post {
          entity(as[Pet]) { p =>
            complete(petOperations.create(p))
          }
        } ~
          put {
            entity(as[Pet]) { p =>
              complete(petOperations.put(p))
            }
          } ~
          options {
            complete("ok")
          }
      } ~
        path("pet" / "findByStatus" / Segment) { status =>
          get {
            complete(petOperations.findByStatus(status))
          }
        } ~
        path("pet" / "findByTag" / Segment) { tag =>
          get {
            complete(petOperations.findByTag(tag))
          }
        } ~
        path("pet" / IntNumber) { id =>
          delete {
            complete(petOperations.delete(id))
          } ~
            get {
              complete(petOperations.findById(id))
            } ~
            options {
              complete("ok")
            }
        }
    }
  }
}