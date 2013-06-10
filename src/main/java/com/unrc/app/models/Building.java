package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Building extends Model { 
  static{
      validatePresenceOf("tipo","descripcion","operacion","cant_banios","cant_habitaciones","precio","direccion","superficie");
      validateNumericalityOf("precio");
      validateNumericalityOf("superficie");
      validateNumericalityOf("cant_banios");
      validateNumericalityOf("cant_habitaciones");
  }
}
