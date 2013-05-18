package com.unrc.app;

import com.unrc.app.models.User;
import com.unrc.app.models.Building;
import com.unrc.app.models.Owner;
import com.unrc.app.models.RealEstate;
import com.unrc.app.models.Location;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Inmo {
    public static void main( String[] args )
    {
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/inmoapp_development", "root", "root");

        User e = new User();
        
        e.set("nombre_usuario", "John");
        e.set("contrasenia", "888");
        e.saveIt();

	Location l = new Location();
        
	l.set("nombre", "rio cuarto");
        l.set("codigo_postal", 5800);
	l.saveIt();

	RealEstate re = new RealEstate();
	re.set("nombre", "gutierrez");
        re.set("email", "gutierrez@gmail.com");
	re.set("telefono", 4628374);
	re.set("direccion", "La Rioja 523");
        re.set("sitio_web", "gutierrez.web");
	re.saveIt();

	//relaciones realestates
        l.add(re);
        re.saveIt();

	Owner p = new Owner();
	p.set("nombre", "pedro gomez");
        p.set("email", "pgomez@gmail.com");
	p.set("telefono", 4628374);
	p.set("direccion", "La Rioja 523");
	p.saveIt();
        
        //relaciones owner
	l.add(p);
        re.add(p);
        p.saveIt();
	

	

	Building b = new Building();
        b.set("tipo", "casa");
	b.set("direccion", "mendoza 235");
	b.set("cant_habitaciones",6);
        b.set("superficie",400);
	b.set("cant_banios", 3);
	b.set("precio", 650000);
	b.set("descripcion", "zona rural granja");
	b.set("operacion", "venta");
	b.set("fecha_fin", "2013-12-06");
        b.set("fecha_inicio","2013-01-02");
        b.saveIt();

        //relaciones building
	l.add(b);
        re.add(b);
        p.add(b);
        b.saveIt();
        

	
	
        System.out.println( "Hello World!" );
    }
}
