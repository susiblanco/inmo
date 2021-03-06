package com.unrc.app;

import com.unrc.app.models.Building;
import com.unrc.app.models.RealEstate;
import com.unrc.app.models.Owner;
import com.unrc.app.models.Location;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.javalite.test.jspec.JSpec.the;

public class BuildingSpec{

    @Before
    public void before(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/inmoapp_development", "root", "root");
        Base.openTransaction();
    }

    @After
    public void after(){
        Base.rollbackTransaction();
        Base.close();
    }

    @Test
    public void shouldValidateMandatoryFields(){


      Building  building = new Building();

        //check errors
        the(building).shouldNotBe("valid");
        the(building.errors().get("tipo")).shouldBeEqual("value is missing");
        the(building.errors().get("descripcion")).shouldBeEqual("value is missing");
        the(building.errors().get("superficie")).shouldBeEqual("value is missing");
	the(building.errors().get("operacion")).shouldBeEqual("value is missing");
	the(building.errors().get("cant_habitaciones")).shouldBeEqual("value is missing");
	the(building.errors().get("cant_banios")).shouldBeEqual("value is missing");
	the(building.errors().get("direccion")).shouldBeEqual("value is missing");
	the(building.errors().get("precio")).shouldBeEqual("value is missing");
      


	//set missing values
        building.set("tipo", "casa");
        building.set("descripcion", "quinta");
        building.set("superficie", 500);
        building.set("operacion", "venta");
        building.set("cant_habitaciones", 6);
        building.set("cant_banios", 2);
        building.set("direccion", "cordoba 73");
        building.set("precio", 120000);
       

        //all is good:
        the(building).shouldBe("valid");
    }

    @Test
    public void shouldSaveAssociation(){
	
	Owner owner = new Owner();
	owner.set("dni",21407489);
	owner.set("nombre","pedro galitu");
	owner.set("direccion","estrada 847");
	owner.set("telefono",46895789);
	owner.set("email","galitupe@hotmail.com");
	owner.saveIt();

	Location location = new Location();
	location.set("nombre","rio tercero");
	location.set("codigo_postal",5874);
	location.saveIt();
        
    Building building = new Building();
    
    //set missing values
    building.set("tipo", "casa");
    building.set("descripcion", "quinta");
    building.set("superficie", 500);
    building.set("operacion", "venta");
    building.set("cant_habitaciones", 6);
    building.set("cant_banios", 2);
    building.set("direccion", "cordoba 73");
    building.set("precio", 120000);
    building.saveIt();

	owner.add(building);
	location.add(building);

	Building b = Building.findFirst("location_id = ?", location.getId());
        the(building.getId()).shouldBeEqual(b.getId());

	b = Building.findFirst("owner_id = ?", owner.getId());
        the(building.getId()).shouldBeEqual(b.getId());
        
    }

}
