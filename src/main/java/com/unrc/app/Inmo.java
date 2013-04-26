package com.unrc.app;

import com.unrc.app.models.User;
import com.unrc.app.models.Inmueble;
import com.unrc.app.models.Propietario;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Inmo {
    public static void main( String[] args )
    {
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/inmoapp_development", "root", "root");

        User e = new User();
        e.set("email", "user@email.com");
        e.set("first_name", "John");
        e.set("last_name", "Doe");
        e.saveIt();

	Propietario p = new Propietario();
        p.set("tipo", "particular");
	p.set("nombre", "pedro");
	p.set("apellido", "gomez");
	p.set("email", "pgomez@gmail.com");
	p.set("telefono", 4628374);
	p.set("direccion", "La Rioja 523");
	p.saveIt();
	
	Inmueble i = new Inmueble();
        i.set("tipo", "casa");
	i.set("localidad", "chucul");
	i.set("direccion", "mendoza 235");
	i.set("cant_ambientes",6);
	i.set("cant_banios", 3);
	i.set("precio", 650.000);
	i.set("descripcion", "zona rural, granja");
	i.set("operacion", "vende");
	i.set("propietario", p.get("id"));
	i.set("fecha", "2013-12-06");
        i.saveIt();
	
	
        System.out.println( "Hello World!" );
    }
}
