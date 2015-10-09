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

import com.escalatesoft.subcut.inject.NewBindingModule
import hms.petstore.service.{ActualPetStoreService, PetStoreService}

object ServiceModule extends NewBindingModule(module => {
  import module._

  bind[PetStoreService] toSingle new ActualPetStoreService
})