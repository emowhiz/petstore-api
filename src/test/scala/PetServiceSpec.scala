import hms.petstore.json.{Category, Tag, Pet}
import hms.petstore.service.ActualPetStoreService
import org.specs2.mutable.SpecificationWithJUnit

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

class PetServiceSpec extends SpecificationWithJUnit {
  val petStoreService = new ActualPetStoreService
  val pet = Pet(12, "dog", List(Tag(Some(1), Some("xxx"))), Some("available"), List("xxx"), Some(Category(Some(1), Some("category"))))
  val petCopy = pet.copy(name = "bad dog")
  sequential

  "pet store service" should {
    "create a pet and return that pet" in {
      petStoreService.create(pet) == pet
    }
    "modify a pet" in {
      petStoreService.put(petCopy) == petCopy
    }
    "find a pet by id and returns specific pet" in {
      petStoreService.findById(petCopy.id) == petCopy
    }
    "find pets by status and returns a list" in {
      petStoreService.findByStatus("available").head == petCopy
    }
    "find pets by tag and returns a list" in {
      petStoreService.findByTag("xxx").head == petCopy
    }
  }

}
