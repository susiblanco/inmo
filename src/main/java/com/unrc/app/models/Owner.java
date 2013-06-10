package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Owner extends Model { 
  static{
      validatePresenceOf("dni","nombre","direccion","telefono","email");
      validateNumericalityOf("dni");
      validateNumericalityOf("telefono");
      validateEmailOf("email");
 }
}
