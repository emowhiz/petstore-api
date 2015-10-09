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

import com.novus.salat.annotations._

case class Pet(@Key("_id") id: Int,
               name: String,
               tags: List[Tag] = List.empty,
               status: Option[String] = None,
               photoUrls: List[String]  = List.empty,
               category: Option[Category] = None)

case class Category(id:Option[Int]=None,
                    name:Option[String]=None)

case class Tag(id:Option[Int]=None,
               name:Option[String]=None)