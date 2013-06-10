package com.unrc.app;

import com.unrc.app.models.Owner;

import com.unrc.app.models.Location;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.javalite.test.jspec.JSpec.the;

public class OwnerSpec{

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

        Owner owner = new Owner();

        //check errors
        the(owner).shouldNotBe("valid");
        the(owner.errors().get("dni")).shouldBeEqual("value is missing");
        the(owner.errors().get("nombre")).shouldBeEqual("value is missing");
        the(owner.errors().get("direccion")).shouldBeEqual("value is missing");
        the(owner.errors().get("telefono")).shouldBeEqual("value is missing");
        the(owner.errors().get("email")).shouldBeEqual("value is missing");


        //set missing values
        owner.set("dni", 21407422,"nombre", "Juan","direccion", "mendoza 485","telefono", 49785566,"email", "Juanfer@gmail.com");


        //all is good:
        the(owner).shouldBe("valid");
    }

    @Test
    public void shouldSaveAssociation(){


	Location location = new Location();
	location.set("nombre","rio tercero");
	location.set("codigo_postal",5874);
	location.saveIt();
        
        Owner owner = new Owner();

	 //set missing values
    owner.set("dni", 21407489, "nombre", "Juan","direccion", "mendoza 485","telefono", 49785566,"email", "Juanfer@gmail.com");
    owner.saveIt();
    
    location.add(owner);  
    owner.saveIt();

    Owner o = Owner.findFirst("location_id = ?", location.getId());
    the(owner.getId()).shouldBeEqual(o.getId());
        
    }
}
