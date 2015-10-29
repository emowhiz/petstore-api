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

  override def is = sequential^s2"""
  pet store service

  create a pet and return that pet              $e1
  modify a pet                                  $e2
  find a pet by id and returns specific pet     $e3
  find pets by status and returns a list"       $e4
  find pets by tag and returns a list           $e5

  """

  def e1= petStoreService.create(pet) == pet
  def e2=petStoreService.put(petCopy) == petCopy
  def e3=petStoreService.findById(petCopy.id) == petCopy
  def e4=petStoreService.findByStatus("available").head == petCopy
  def e5=petStoreService.findByTag("xxx").head == petCopy
}
