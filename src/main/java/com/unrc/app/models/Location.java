package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Location extends Model { 
static{
      validatePresenceOf("nombre","codigo_postal");
 }
}
