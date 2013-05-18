package com.unrc.app;

import com.unrc.app.models.Location;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.javalite.test.jspec.JSpec.the;

public class LocationSpec{

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

        Location location = new Location();

        //check errors
        the(location).shouldNotBe("valid");
        the(location.errors().get("nombre")).shouldBeEqual("value is missing");
        the(location.errors().get("codigo_postal")).shouldBeEqual("value is missing");

        //set missing values
        location.set("nombre", "rio cuarto", "codigo_postal", 5800);

        //all is good:
        the(location).shouldBe("valid");
    }
}
