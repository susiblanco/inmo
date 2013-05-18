package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Owner extends Model { 
  static{
      validatePresenceOf("nombre","direccion","telefono","email");
      validateEmailOf("email");
 }
}
