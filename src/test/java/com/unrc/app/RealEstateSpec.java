package com.unrc.app;

import com.unrc.app.models.RealEstate;

import com.unrc.app.models.Location;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.javalite.test.jspec.JSpec.the;

public class RealEstateSpec{

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

        RealEstate realestate = new RealEstate();

        //check errors
        the(realestate).shouldNotBe("valid");
        the(realestate.errors().get("nombre")).shouldBeEqual("value is missing");
        the(realestate.errors().get("direccion")).shouldBeEqual("value is missing");
	the(realestate.errors().get("telefono")).shouldBeEqual("value is missing");
	the(realestate.errors().get("email")).shouldBeEqual("value is missing");
	the(realestate.errors().get("sitio_web")).shouldBeEqual("value is missing");

        //set missing values
        realestate.set("nombre", "inmobiliaria");
	realestate.set("direccion", "san martin 457");
	realestate.set("telefono", 155489756);
	realestate.set("email", "inmobi@gmail.com");
	realestate.set("sitio_web", "inmoweb");

        //all is good:
        the(realestate).shouldBe("valid");
    }

    @Test
    public void shouldSaveAssociation(){


	Location location = new Location();
	location.set("nombre","rio tercero");
	location.set("codigo_postal",5874);
	location.saveIt();
        
        RealEstate realestate = new RealEstate();

	//set missing values
        realestate.set("nombre", "inmobiliaria");
	realestate.set("direccion", "san martin 457");
	realestate.set("telefono", 155489756);
	realestate.set("email", "inmobi@gmail.com");
	realestate.set("sitio_web", "inmoweb");
	realestate.saveIt();
        location.add(realestate);
        realestate.saveIt();

	RealEstate r = RealEstate.findFirst("location_id = ?", location.getId());
        the(realestate.getId()).shouldBeEqual(r.getId());
        
        
    }
}
