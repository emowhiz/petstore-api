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

import akka.actor.{Props, ActorSystem}
import akka.testkit.{ImplicitSender, TestActorRef, TestKit}
import hms.petstore.{ServiceModule, PetStoreRoutesActor}
import org.scalatest.{BeforeAndAfterAll, FreeSpecLike, Matchers}

class PetSpec extends TestKit(ActorSystem("on-spray-can"))
    with ImplicitSender
    with FreeSpecLike
    with Matchers
    with BeforeAndAfterAll {

  val actorRef = TestActorRef(Props(classOf[PetStoreRoutesActor],ServiceModule))

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "Actor" - {
    "creates" - {
      "receive Ok" - {
        val future = actorRef ! "pet/findByStatus/available"

      }
    }
  }
}
