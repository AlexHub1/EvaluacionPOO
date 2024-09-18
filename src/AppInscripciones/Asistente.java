
package AppInscripciones;

import java.util.List;
import java.util.Objects;

public class Asistente implements Runnable{
   
    
    private String nombre;
    private String cedula;
    private List<String> listaSesionesAsistidas;
    private int asistenciaEventos;

    public Asistente(String nombre, String cedula) {
        this.nombre = nombre;
        this.cedula = cedula;
    }
    
    
    
    public Asistente(String nombre, String cedula, int asistenciaEventos) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.asistenciaEventos = asistenciaEventos;
    }

    public Asistente(String nombre, String cedula, List<String> listaSesionesAsistidas, int asistenciaEventos) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.listaSesionesAsistidas = listaSesionesAsistidas;
        this.asistenciaEventos = asistenciaEventos;
    }
    
  

    public int getAsistenciaEventos() {
        return asistenciaEventos;
    }

    public void setAsistenciaEventos(int asistenciaEventos) {
        this.asistenciaEventos = asistenciaEventos;
    }
    
    
    

    @Override
    public void run() {
        
        try {
            // Simular el procesamiento del pedido
            
            System.out.println("Se realizo la inscripcion del asistente " + asistenciaEventos);
            Thread.sleep(1000); //Simula el tiempo de procesamiento

        } catch (InterruptedException e) {
            System.err.println("El procesamiento del pedido fue interrumpido");

        }
    }
    
    

    public List<String> getListaSesionesAsistidas() {
        return listaSesionesAsistidas;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setListaSesionesAsistidas(String sesionesAsistidas) {
        this.listaSesionesAsistidas.add(sesionesAsistidas);
        
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.cedula);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Asistente other = (Asistente) obj;
        return Objects.equals(this.cedula, other.cedula);
    }
    

    @Override
    public String toString() {
        return "Asistente{" + "listaSesionesAsistidas=" + listaSesionesAsistidas + ", nombre=" + nombre + ", cedula=" + cedula + '}';
    }
    
    
    
}
