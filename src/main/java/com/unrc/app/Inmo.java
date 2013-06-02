package com.unrc.app;

import com.unrc.app.models.User;
import com.unrc.app.models.Building;
import com.unrc.app.models.Owner;
import com.unrc.app.models.RealEstate;
import com.unrc.app.models.Location;
import static spark.Spark.*;
import spark.*;
import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


 public class Inmo {

   public static void main(String[] args) {
      
      get(new Route("/") {
         @Override
         public Object handle(Request request, Response response) {
            return "Hello World!";
         }
      });

   }

}
  

