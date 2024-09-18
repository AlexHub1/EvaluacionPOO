
package AppInscripciones;

import java.util.List;


public record Sesion (int id, String nombre, String tipoSesion, List<String> listaAsistentes) {

    @Override
    public String nombre() {
        return nombre; 
    }

    
}
