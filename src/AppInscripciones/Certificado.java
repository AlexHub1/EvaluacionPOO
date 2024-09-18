
package AppInscripciones;

import java.util.List;

class Certificado {
    private String id;
    private Asistente asistente;
    private List<Sesion> listaSesionesCompletadas;

    public Certificado(String id, Asistente asistente, List<Sesion> listaSesionesCompletadas) {
        this.id = id;
        this.asistente = asistente;
        this.listaSesionesCompletadas = listaSesionesCompletadas;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Certificado ID: ").append(id).append("\n");
        sb.append("Asistente: ").append(asistente.getNombre()).append("\n");
        sb.append("Sesiones Asistidas:\n");
        for (Sesion sesion : listaSesionesCompletadas) {
            sb.append("- ").append(sesion.nombre()).append("\n");
        }
        return sb.toString();
    }           
    
}
