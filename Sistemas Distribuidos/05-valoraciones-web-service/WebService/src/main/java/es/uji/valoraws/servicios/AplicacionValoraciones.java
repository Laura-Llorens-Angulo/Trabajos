package es.uji.valoraws.servicios;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author 
 * Instancia los objetos que ofrecen los servicios web que queremos que despliegue el servidor (p.e. Tomcat)
 * Se almacenan en un conjunto que es devuelto por el método gestSingletons
 *
 */

@ApplicationPath("/servicios")
public class AplicacionValoraciones extends Application {
   private Set<Object> singletons = new HashSet<Object>();

   public AplicacionValoraciones() {
      singletons.add(new RecursoValoraciones());
   }

   @Override
   public Set<Object> getSingletons() {
      return singletons;
   }
}
