package com.unrc.app.models;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.Association;

//@BelongsTo(parent = Location.class, foreignKeyName = "location_id")
public class RealEstate extends Model {
  static{
      validatePresenceOf("nombre","direccion","telefono","email","sitio_web");
      validateEmailOf("email");
  }
}
