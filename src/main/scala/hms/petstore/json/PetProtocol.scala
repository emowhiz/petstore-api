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

package hms.petstore.json

import spray.json.DefaultJsonProtocol

object PetProtocol extends DefaultJsonProtocol {
  implicit val tagFormat = jsonFormat2(Tag)
  implicit val categoryFormat = jsonFormat2(Category)
  implicit val petFormat = jsonFormat6(Pet)
}
