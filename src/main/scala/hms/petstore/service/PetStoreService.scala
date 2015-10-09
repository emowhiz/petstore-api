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

package hms.petstore.service


import com.mongodb.casbah.Imports._
import com.novus.salat._
import com.novus.salat.global._
import hms.petstore.json.Pet
import org.slf4j.{LoggerFactory, Logger}

trait PetStoreService {

  /**
   * Inserts a pet to the database
   * @param pet pet object to be inserted
   * @return inserted object
   */
  def create(pet: Pet): Pet

  /** Updates an existing pet
    *
    * @param pet pet object to update
    * @return updated pet
    */
  def put(pet: Pet): Pet

  /**
   * Deletes a pet with specific id
   * @param id id of the pet to be deleted
   * @return status of the operation
   */
  def delete(id: Int): String

  /**
   * Find all pets with given status
   * @param status status to search by
   * @return List containing relevant pets
   */
  def findByStatus(status: String): List[Pet]

  /**
   * Find pets by given tags
   * @param tag string containing tags to be searched seperated by commas
   * @return
   */
  def findByTag(tag: String): List[Pet]

  /**
   * Find all details of an specific pet given the id.
   * @param id id of the pet
   * @return pet object of specific pet
   */
  def findById(id: Int): Pet

}

object PetDBO {
  val collection = MongoConnection()("salat_test")("pet")
}
object ActualPetStoreService
class ActualPetStoreService extends PetStoreService {

  val log: Logger = LoggerFactory.getLogger(ActualPetStoreService.getClass)

  def create(pet: Pet): Pet = {
    PetDBO.collection.insert(grater[Pet].asDBObject(pet))
    val found = PetDBO.collection.findOne("_id" $eq pet.id)
    log.info("Pet Created")
    found.map(o => grater[Pet].asObject(o)).get
  }

  def delete(id: Int): String = {
    PetDBO.collection.remove("_id" $eq id)
    if (PetDBO.collection.findOne("_id" $eq id).isEmpty) {
      log.info("Pet Deleted")
      "Deleted"
    } else {
      log.info("Internal error Occurred")
      "Internal error Occurred"
    }
  }

  def findById(id: Int): Pet = {
    val found = PetDBO.collection.findOne("_id" $eq id)
    log.info("Pet Found {}",id)
    found.map(o => grater[Pet].asObject(o)).get
  }

  def put(pet: Pet): Pet = {
    val query = "_id" $eq pet.id
    PetDBO.collection.update(query, grater[Pet].asDBObject(pet))
    log.info("Pet updated {}",pet.id)
    val found = PetDBO.collection.findOne("_id" $eq pet.id)
    found.map(o => grater[Pet].asObject(o)).get
  }

  def findByStatus(status: String): List[Pet] = {
    val found = PetDBO.collection.find("status" $eq status)
    log.info("Pet/s found {}",found.size)
    found.map(o => grater[Pet].asObject(o)).toList
  }

  def findByTag(tag: String): List[Pet] = {
    val found = tag.split("\\W+").flatMap(x => PetDBO.collection.find("tags.name" $eq x))
    log.info("Pet/s found {}",found.size)
    found.map(grater[Pet].asObject(_)).toList
  }
}