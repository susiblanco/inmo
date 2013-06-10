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

        final String  inicio_html = "<html><title>EIRW</title><body bgcolor='silver' text='navy'>"
                                    +"<center><h1>El Inmobilia.Rio Web</h1>"
                                    +" <a href='/'>inicio</a> |"
                                    +" <a href='/locations'>localidades</a> |"
                                    +" <a href='/realestates'>inmobiliarias</a> |"
                                    +" <a href='/owners'>propietarios</a> |"
                                    +" <a href='/buildings'>inmuebles</a></center><br>";

        final String  cerrar_html = "</body></html>";


        //-------------------------------------------------------------------------------------//
        // INICIO                                                                              //
        //-------------------------------------------------------------------------------------//

        //Pagina por defecto
        get(new Route("/") {
            @Override
            public Object handle(Request request, Response response) {
                response.type("text/html"); 
                String html = inicio_html	 
                            +"<h2>UNRC AYDS Proyecto 2013</h2>"
                            +"Integrantes:"
                            +"<ul>"
                            +"<li>Blanco, Susana DNI 21998099</li>"
                            +"<li>Oviedo, Silvia DNI 32139606</li>"
                            +"</ul>"
                            + cerrar_html;
                return html;
            }
        });


        //-----------------------------------------------------------------------------------//
        // GESTIONAR LOCALIDAD                                                               //
        //----------------------------------------------- -----------------------------------//

        //Muestra la lista de todas localidades y el formulario para agregar
        get(new Route("/locations") {	
            @Override
            public Object handle(Request request, Response response) {
                response.type("text/html"); 
                String html = inicio_html+"<h2>Localidades</h2>"
                            //formulario para agregar localidades
                            +"<form method='post' action='/location'>"
                            +" nombre: <input type='text' name='name'><br>"
                            +" codigo postal: <input type='text' name='cp'><br>"
                            +"<input type='submit' value='agregar'></form><hr>";

                Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/inmoapp_development", "root", "root");
                
                    //tabla de localidades
                    html+="<table border='1'>"
                        +"<tr><th>nombre</th><th>codigo postal</th></tr>";
                        
                    List<Location> localidades = Location.findAll();
                    for(Location l: localidades){  
                        html +=" <tr><td>"+l.get("nombre")+"</td><td>"+l.get("codigo_postal")+"</td></tr>";
                    }
                    
                    html+="</table>";
                Base.close();
                html += cerrar_html;
                return html;
            }
        });

        //crea una nueva localidad con los datos pasados por parametros	
        post(new Route("/location") {	
            @Override
            public Object handle(Request request, Response response) {
                String nombre = request.queryParams("name");
                int cp = Integer.parseInt(request.queryParams("cp"));
                Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/inmoapp_development", "root", "root");
                    Location l = new Location();
                    l.set("nombre",nombre,"codigo_postal",cp);
                    l.saveIt();
                Base.close();
                //regresa a gestionar localidades 
                response.redirect("/locations");
                return "creada";
            }
        });


        //------------------------------------------------------------------------------//
        // GESTIONAR INMOBILIARIA                                                       //
        //------------------------------------------------------------------------------//

        //Muestra la lista de todas inmobiliarias y el formulario para agregar
        get(new Route("/realestates") {	
            @Override
            public Object handle(Request request, Response response) {
                response.type("text/html"); 
                String html = inicio_html+"<h2>Inmobiliarias</h2>"
                            //formulario para agregar inmobiliarias
                            +"<form method='post' action='/realestate'>"
                            +" nombre:<input type='text' name='name'><br>"
                            +" telefono:<input type='text' name='tel'><br>"
                            +" email:<input type='text' name='email'><br>"
                            +" direccion:<input type='text' name='dir'><br>"
                            +" sitioweb:<input type='text' name='web'><br>";
                    
                Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/inmoapp_development", "root", "root");
                	
                	//selecion localidad
                    html+=" localidad:<select name='loc'><option></option>";
                    List<Location> localidades = Location.findAll();
                    for(Location l: localidades){  
                        html +="<option value='"+l.get("id")+"'>"+l.get("nombre")+"</option>";
                    }
                    html+="</select><br><input type='submit' value='agregar'></form><hr>";

                    //tabla de inmobiliarias
                    html+="<table border='1'>"
                        +"<tr><th>nombre</th><th>telefono</th><th>email</th><th>sitio_web</th><th>direccion</th><th>localidad</th></tr>";
                    List<RealEstate> inmobiliarias = RealEstate.findAll();
                    for(RealEstate i: inmobiliarias){  
                        html +="<tr><td>"+i.get("nombre")+"</td><td>"+i.get("telefono")
                             +"</td><td>"+i.get("email")+"</td><td>"+i.get("sitio_web")
                             +"</td><td>"+i.get("direccion")+"</td>";
                        Location loc = i.parent(Location.class);
                        html +="<td>"+loc.get("nombre")+"</td></tr>";
                    }
                    html+="</table>";
                Base.close();
                html += cerrar_html;
                return html;
            }
        });

        //crea una nueva inmobiliaria con los datos pasados por parametros
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
                    re.set("nombre", nombre,"email", email,"telefono", tel,"direccion", dir,"sitio_web", web);
                    re.saveIt();
                    Location l = Location.findFirst("id = ?", loc);
                    l.add(re);
                Base.close();
                //regresa a gestionar inmobiliarias	
                response.redirect("/realestates");
                return "creada";
            }
        });

        //-----------------------------------------------------------------------------------//
        // GESTIONAR INMUEBLES                                                               //
        //-----------------------------------------------------------------------------------//
        get(new Route("/buildings") {
            @Override
            public Object handle(Request request, Response response) {
                response.type("text/html"); 
                String html = inicio_html	 
                            +"<h2>Inmuebles</h2>"
                            +"<form method='post' action='building'>"
                            +" tipo:<select name='tipo'>"
                                +"<option value='casa' selected>casa</option>"
                                +"<option value='departamento'>departamento</option>"
                                +"<option value='oficina'>oficina</option>"
                                +"<option value='cochera'>cochera</option>"
                                +"<option value='campo'>campo</option>"
                                +"<option value='quinta'>quinta</option>"
                                +"</select><br>"
                            +" operacion:<select name='op'>"
                                +"<option value='venta' selected>venta</option>"
                                +"<option value='alquiler'>alquiler</option>"
                                +"</select><br>"
                            +" descripcion:<input type='text' name='desc'><br>"
                            +" direccion:<input type='text' name='dir'><br>"
                            +" cant_habitacion:<input type='text' name='ch'><br>"
                            +" cant_ba&ntilde;os:<input type='text' name='cb'><br>"
                            +" superficie:<input type='text' name='sup'><br>"
                            +" precio:<input type='text' name='precio'><br>";
                             
                    Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/inmoapp_development", "root", "root");
                	
                	//selecion localidad
                    html+=" localidad:<select name='loc'><option></option>";
                    List<Location> localidades = Location.findAll();
                    for(Location l: localidades){  
                        html +="<option value='"+l.get("id")+"'>"+l.get("nombre")+"</option>";
                    }
                    html+="</select><br>";
                                    
                    //selecion propietarios			
                    html+=" propietarios:<select name='prop'><option></option>";
                    List<Owner> propietarios = Owner.findAll();
                    for(Owner o : propietarios){  
                         html +="<option value='"+o.get("id")+"'>"+o.get("dni")+"</option>";
                    }
                    html+="</select><br>"
                        +"<input type='submit' value='agregar'></form><hr>";
                   

                    //tabla de inmuebles
                    html+="<table border='1'>"
                        +"<tr><th>tipo</th><th>operacion</th><th>descripcion</th><th>cant_habitaciones</th><th>cant_ba&ntilde;os</th>"
                        +"<th>superficie</th><th>precio</th><th>direccion</th><th>localidad</th><th>dni propietario</th></tr>";
                    List<Building> inmuebles = Building.findAll();
                    for(Building i: inmuebles){  
                        html +="<tr><td>"+i.get("tipo")+"</td><td>"+i.get("operacion")
                             +"</td><td>"+i.get("descripcion")+"</td><td>"+i.get("cant_habitaciones")
                             +"</td><td>"+i.get("cant_banios")+"</td><td>"+i.get("superficie")+"</td><td>"+i.get("precio")
                             +"</td><td>"+i.get("direccion")+"</td>";
                        Location loc = i.parent(Location.class);
                        html +="<td>"+loc.get("nombre")+"</td>";
                        Owner o = i.parent(Owner.class);
                        html +="<td>"+o.get("dni")+"</td></tr>";
                    }
                    html+="</table>";
                Base.close();
                html+=cerrar_html;	
                return html;
            }
        });
        //crea un nuevo inmueble con los datos pasados por parametros	
        post(new Route("/building") {	
            @Override
            public Object handle(Request request, Response response) {	
                String tipo = request.queryParams("tipo");
                String op = request.queryParams("op");
                String desc = request.queryParams("desc");
                String dir = request.queryParams("dir");
                String sup = request.queryParams("sup");
                String ch = request.queryParams("ch");
                String cb = request.queryParams("cb");
                String precio = request.queryParams("precio");
                String loc = request.queryParams("loc");
                String prop = request.queryParams("prop");
                
                Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/inmoapp_development", "root", "root");
                    Building i = new Building();
                    i.set("tipo",tipo,"operacion",op,"descripcion",desc,"direccion",dir,"superficie",sup);
                    i.set("cant_habitaciones",ch,"cant_banios",cb,"precio",precio);
                    i.saveIt();
                    Location l = Location.findFirst("id = ?", loc);
                    l.add(i);
                    Owner o = Owner.findFirst("id = ?", prop);
                    o.add(i);
                Base.close();
                //regresa a gestionar propietarios
                response.redirect("/buildings");
                return "creada";
            }
        });

        //----------------------------------------------------------------------------------//
        // GESTIONAR PROPIETARIOS                                                           //
        //----------------------------------------------------------------------------------//
        
        //Muestra la lista de todos propietarios y el formulario para agregar
        get(new Route("/owners") {
            @Override
            public Object handle(Request request, Response response) {
                response.type("text/html"); 
                String html = inicio_html	 
                            +"<h2>Propietarios</h2>"
                            //formulario para agregar inmobiliarias
                            +"<form method='post' action='/owner'>"
                            +" dni:<input type='text' name='dni'><br>"
                            +" nombre:<input type='text' name='nombre'><br>"
                            +" telefono:<input type='text' name='tel'><br>"
                            +" email:<input type='text' name='email'><br>"
                            +" direccion:<input type='text' name='dir'><br>";
                    
                Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/inmoapp_development", "root", "root");
                	
                	//selecion localidad
                    html+=" localidad:<select name='loc'><option></option>";
                    List<Location> localidades = Location.findAll();
                    for(Location l: localidades){  
                        html +="<option value='"+l.get("id")+"'>"+l.get("nombre")+"</option>";
                    }
                    html+="</select><br>";
                                    
                    //selecion inmobiliaria				
                    html+=" inmobiliaria:<select name='re'><option></option>";
                    List<RealEstate> inmobiliarias = RealEstate.findAll();
                    for(RealEstate i: inmobiliarias){  
                        html +="<option value='"+i.get("id")+"'>"+i.get("nombre")+"</option>";
                    }
                    html+="</select><br>"
                        +"<input type='submit' value='agregar'></form><hr>";

                    //tabla de propietarios
                    html+="<table border='1'>"
                        +"<tr><th>dni</th><th>nombre</th><th>telefono</th><th>email</th><th>direccion</th><th>localidad</th><th>inmobiliaria</th></tr>";
                    List<Owner> propietarios = Owner.findAll();
                    for(Owner o: propietarios){  
                        html +="<tr><td>"+o.get("dni")+"</td><td>"+o.get("nombre")
                             +"</td><td>"+o.get("telefono")+"</td><td>"+o.get("email")
                             +"</td><td>"+o.get("direccion")+"</td>";
                        Location loc = o.parent(Location.class);
                        html +="<td>"+loc.get("nombre")+"</td>";
                        RealEstate re = o.parent(RealEstate.class);
                        html +="<td>"+re.get("nombre")+"</td></tr>";
                    }
                Base.close();
                html+=cerrar_html;	
                return html;
            }
        });
        
        //crea un nuevo propietario con los datos pasados por parametros	
        post(new Route("/owner") {	
            @Override
            public Object handle(Request request, Response response) {	
                String dni = request.queryParams("dni");
                String nombre = request.queryParams("nombre");
                String tel = request.queryParams("tel");
                String email = request.queryParams("email");
                String dir = request.queryParams("dir");
                String loc = request.queryParams("loc");
                String re = request.queryParams("re");
                Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/inmoapp_development", "root", "root");
                    Owner o = new Owner();
                    o.set("dni",dni,"nombre",nombre,"email",email,"telefono",tel,"direccion",dir);
                    o.saveIt();
                    Location l = Location.findFirst("id = ?", loc);
                    l.add(o);
                    RealEstate r = RealEstate.findFirst("id = ?", re);
                    r.add(o);
                Base.close();
                //regresa a gestionar propietarios
                response.redirect("/owners");
                return "creada";
            }
        });

    }
}
