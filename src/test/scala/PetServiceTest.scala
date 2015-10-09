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
package scala
import akka.actor.ActorSystem
import com.escalatesoft.subcut.inject.BindingModule
import hms.petstore.PetStoreRoutes
import hms.petstore.json.Pet
import hms.petstore.service.PetStoreService
import spray.http.{ContentTypes, HttpEntity}
import spray.http.StatusCodes._
import org.scalatest._
import spray.httpx.SprayJsonSupport
import spray.testkit.ScalatestRouteTest


class PetServiceTest (implicit val bindingModule: BindingModule)extends FreeSpec with Matchers with ScalatestRouteTest with PetStoreRoutes {
  def actorRefFactory =system

  "The pet Route" - {
    "when listing entities" - {
      "returns a JSON list" in {
        //Mix in Json4s, but only for this test
        import hms.petstore.json.PetProtocol._
        import SprayJsonSupport._

        Get("/pet/findByStatus/available") ~> petRoute ~> check {
          assert(contentType.mediaType.isApplication)

          //Check content type
          contentType.toString should include("application/json")


          //serialize
          val response = responseAs[List[Pet]]

          response.size should equal(2)
          response(0).id should equal(0)

          //Check http status
          status should equal(OK)
        }
      }
    }

  }


}
