package com.unrc.app;

import com.unrc.app.models.*;
import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static spark.Spark.*;
import spark.*;
import java.util.List;

public class Inmo {
 
  public static void main( String[] args ){

       final String  inicio_html = "<html><title>EIRW</title><body>"
                                +"<h1>El Inmobilia.Rio Web</h1>"
                                +" <a href='/'>inicio</a> |"
                                +" <a href='/locations'>localidades</a> |"
				+" <a href='/realestates'>inmobiliarias</a> |"
                                +" <a href='/buildings'>inmuebles</a> |"
                                +" <a href='/owners'>propietarios</a><br>";

       final String  cerrar_html = "</body></html>";


//----------------------------------------------------------------------------------------------------------------//
// INICIO                                                                                                         //
//----------------------------------------------------------------------------------------------------------------//

        //pagina por defecto
 	    get(new Route("/") {
	    @Override
         public Object handle(Request request, Response response) {
            String html = inicio_html	 
                        + "Hello World!"
                        + cerrar_html;
            return html;
         }
      	});


//------------------------------------------------------------------------------------------------------------//
// GESTIONAR LOCALIDAD                                                                                        //
//------------------------------------------------------------------------------------------------------------//
	
        //gestionar localidades muestra la lista de todas localidades y el enlace para agregar
        get(new Route("/locations") {	
         @Override
         public Object handle(Request request, Response response) {
	    	String html = inicio_html+"<h2>Localidades</h2>"
                         
                         //formulario para agregar localidades
                         +"<form method='post' action='/location'>"
                         +" nombre: <input type='text' name='name'><br>"
			 +" codigo postal: <input type='text' name='cp'><br>"
                         +"<input type='submit' value='agregar'></form><hr>";

            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/inmoapp_development", "root", "root");

            //lista de localidades
	    List<Location> localidades = Location.findAll();
	    for(Location l: localidades){  
   		html +=" id:"+l.get("id")+" nombre:"+l.get("nombre")+" codigo postal:"+l.get("codigo_postal")+"<hr>";
	    }
            Base.close();
            html += cerrar_html;
            return html;
         }
      	});

	    //crea una nueva localidad con los datos pasados por parametros y  regresa a getionar localidades 	
	    post(new Route("/location") {	
        @Override
        public Object handle(Request request, Response response) {	    
	    	String nombre = request.queryParams("name");
            int cp = Integer.parseInt(request.queryParams("cp"));
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/inmoapp_development", "root", "root");
              Location l = new Location();
	          l.createIt("nombre",nombre,"codigo_postal",cp);
            Base.close();
            response.redirect("/locations");
            return "creada";
         }
      	});


  
//--------------------------------------------------------------------------------------------------------------------//
// GESTIONAR INMOBILIARIA                                                                                             //
//--------------------------------------------------------------------------------------------------------------------//

        //gestionar inmobiliaria muestra la lista de todas inmobiliarias y el enlace para agregar
        get(new Route("/realestates") {	
        @Override
        public Object handle(Request request, Response response) {
	    	String html = inicio_html+"<h2>Inmobiliarias</h2>"

                         //formulario para agregar inmobiliarias
                         +"<form method='post' action='/realestate'>"
                         +" nombre:<input type='text' name='name'><br>"
			 +" telefono:<input type='text' name='tel'><br>"
                         +" email:<input type='text' name='email'><br>"
                         +" direccion:<input type='text' name='dir'><br>"
                         +" sitioweb:<input type='text' name='web'><br>"
                         +" id_localidad:<input type='text' name='loc'><br>"
                         +"<input type='submit' value='agregar'></form><hr>";

            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/inmoapp_development", "root", "root");
            
            //lista de inmobiliarias
	    	List<RealEstate> inmobiliarias = RealEstate.findAll();
	    	for(RealEstate i: inmobiliarias){  
   			html +="id:"+i.get("id")+" nombre:"+i.get("nombre")+" telefono:"+i.get("telefono")+" email:"+i.get("email")
                     +" sitioweb:"+i.get("sitio_web")+" direccion:"+i.get("direccion")+" id_localidad:"+i.get("location_id")+"<hr>";
	    	}
            Base.close();
            html += cerrar_html;
            return html;
         }
      	});

        //crea una nueva inmobiliaria con los datos pasados por parametros y  regresa a getionar inmobiliarias	
	    post(new Route("/realestate") {	
        @Override
        public Object handle(Request request, Response response) {	    
	    String nombre = request.queryParams("name");
            int tel = Integer.parseInt(request.queryParams("tel"));
            String email = request.queryParams("email");
            String dir = request.queryParams("dir");
            String web = request.queryParams("web");
            int loc = Integer.parseInt(request.queryParams("loc"));
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/inmoapp_development", "root", "root");
             	RealEstate re = new RealEstate();
				re.set("nombre", nombre);
       			re.set("email", email);
                re.set("telefono", tel);
                re.set("direccion", dir);
                re.set("sitio_web", web);
                re.saveIt();
                Location l = Location.findFirst("id = ?", loc);
                l.add(re);
            Base.close();
            response.redirect("/realestates");
            return "creada";
         }
        });

        //buildings
 	    get(new Route("/buildings") {
	    @Override
         public Object handle(Request request, Response response) {
            String html = inicio_html	 
                        + "<h2>Inmuebles</h2> falta inmplementar"
                        + cerrar_html;
            return html;
         }
      	});

        //owners
 	    get(new Route("/owners") {
	    @Override
         public Object handle(Request request, Response response) {
            String html = inicio_html	 
                        + "<h2>Propietarios</h2> falta inmplementar"
                        + cerrar_html;
            return html;
         }
      	});

  }
}
