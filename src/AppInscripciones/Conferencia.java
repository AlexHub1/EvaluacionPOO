package AppInscripciones;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

public class Conferencia {

    public static HashMap<String, Asistente> listaAsistentes = new HashMap<>();
    public static List<Sesion> sesiones = new ArrayList<>();
    public static List<String> asistencia = new ArrayList<>();
    public static List<Asistente> asistentes = new ArrayList<>();

    public static void main(String[] args) {
        GestionArchivos();
        servicios();
    }

    public static void servicios() {

        for (Asistente asistente : asistentes) {
            if (asistente.getAsistenciaEventos() == 2) {
                JOptionPane.showMessageDialog(null, "Hay un certificado disponible para " + asistente.getNombre());
            }
        }

        String[] servicios = {"Inscripcion Sesiones", "Inscripcion Asistentes", "Registrar Asistencia", "Generar Certificado", "Listar Sesiones"};
        String opcion = (String) JOptionPane.showInputDialog(null, "Seleccione el servicio", "SERVICIOS", JOptionPane.QUESTION_MESSAGE, null, servicios, servicios[0]);

        switch (opcion) {
            case "Inscripcion Sesiones":
                inscribirSesiones();
                break;
            case "Inscripcion Asistentes":
                inscripcionAsistentes();
                break;
            case "Registrar Asistencia":
                registrarAsistencias();
                break;
            case "Generar Certificado":
                generacionCertificado();
                break;
            case "Listar Sesiones":
                listarSesiones();
                break;
            default:
                throw new AssertionError();
        }
    }

    public static void generacionCertificado() {

        int encontrado = 0;

        for (Asistente asistente : asistentes) {
            if (asistente.getAsistenciaEventos() == 2) {
                StringBuilder certificacion = new StringBuilder();
                for (Map.Entry<String, Asistente> info : listaAsistentes.entrySet()) {
                    if (info.getKey().equals(asistente.getCedula())) {
                        certificacion.append("NOMBRE -> ").append(info.getValue().getNombre()).append(" - Sesiones Completadas -> ").append(info.getValue().getListaSesionesAsistidas());
                        JOptionPane.showMessageDialog(null, certificacion.toString());
                        encontrado = 1;
                    }
                }
            }
        }
        if (encontrado == 0) {
            JOptionPane.showMessageDialog(null, "No hay Certificados disponibles");
            escribirArchivo("errores.txt", "Error no hay certificados disponibles", null, 2);
        }

        servicios();
    }

    public static void listarSesiones() {

        StringBuilder t = new StringBuilder();
        for (Sesion sesione : sesiones) {
            t.append("=====================").append(sesione.nombre()).append("=====================").append("\n");
            t.append("Asistentes ->").append(sesione.listaAsistentes()).append("\n");
        }

        JOptionPane.showMessageDialog(null, t.toString());
        servicios();
    }

    public static void inscripcionAsistentes() {

        if (!sesiones.isEmpty()) {
            int numeroAsistentes = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el numero de asistentes a ingresar para la sesiones"));
            for (int i = 0; i < numeroAsistentes; i++) {
                String nombreSesion = JOptionPane.showInputDialog("Ingrese el nombre de la sesion en la que desea inscribirse el asistente " + (i + 1));
                for (Sesion sesione : sesiones) {
                    if (sesione.nombre().equalsIgnoreCase(nombreSesion)) {
                        String nombreAsis = JOptionPane.showInputDialog("Ingrese el nombre del asistentente");
                        String documentoAsis = JOptionPane.showInputDialog("Ingrese el documento del asistentente");
                        List<String> sesionx = new ArrayList<>();
                        sesionx.add(nombreSesion);
                        Asistente nuevoAsistente = new Asistente(nombreAsis, documentoAsis, sesionx, 1);
                        sesione.listaAsistentes().add(nombreAsis);

                        nuevoAsistente.setListaSesionesAsistidas(nombreSesion);
                        asistentes.add(nuevoAsistente);
                        listaAsistentes.put(documentoAsis, nuevoAsistente);
                        escribirArchivo("Inscripciones.txt", "Inscripcion Asistente", nuevoAsistente, 1);

                    } else {
                        JOptionPane.showMessageDialog(null, "Sesion no encontrada");
                        escribirArchivo("errores.txt", "Sesion no encontrada", null, 2);
                    }
                }

            }
            for (int j = 0; j < numeroAsistentes; j++) {

                Thread hilo1 = new Thread(new Asistente("", "", j + 1));
                hilo1.start();

                try {
                    hilo1.join();
                } catch (InterruptedException e) {
                    System.out.println("El hilo principal fue interrumpido:");
                }
                System.out.println("la inscripcion " + "fue procesada");
            }

        } else {
            JOptionPane.showMessageDialog(null, "No hay sesiones a las que ingresar");
            escribirArchivo("errores.txt", "Error no hay sesiones a las que ingresar", null, 2);
        }
        servicios();
    }

    public static void registrarAsistencias() {

        String documentoAsistente = JOptionPane.showInputDialog("Ingrese el documento del asistente");

        if (listaAsistentes.containsKey(documentoAsistente)) {
            String nombreSesion = JOptionPane.showInputDialog("Ingrese la sesion a la que asiste");
            JOptionPane.showMessageDialog(null, "Asistencia Registrada");
            Asistente asi = listaAsistentes.get(documentoAsistente);
            escribirArchivo("asistencias.txt", "Confirmacion Asistencia", asi, 0);
            for (Sesion sesione : sesiones) {
                if (sesione.nombre().equalsIgnoreCase(nombreSesion)) {
                    for (Asistente asist : asistentes) {
                        if (asist.getCedula().equals(documentoAsistente)) {
                            sesione.listaAsistentes().add(asist.getNombre());
                            asist.setAsistenciaEventos(asist.getAsistenciaEventos()+1);
                            
                        }

                    }

                }
            }

        } else {
            JOptionPane.showMessageDialog(null, "Verifique la informacion");
            escribirArchivo("errores.txt", "Error en la informacion", null, 2);
        }
        servicios();
    }

    public static void inscribirSesiones() {

        int numeroSesiones = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el numero de sesiones a ingresar"));

        if (numeroSesiones > 0) {
            for (int i = 0; i < numeroSesiones; i++) {
                int idSesion = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el id de la sesion"));
                String nombreSesion = JOptionPane.showInputDialog("Ingrese el nombre de la sesion");
                String tipoSesion = JOptionPane.showInputDialog("Ingrese el tipo de la sesion");
                List<String> sesionx = new ArrayList<>();
                sesionx.add("");
                Sesion nuevaSesion = new Sesion(i, nombreSesion, tipoSesion, sesionx);
                sesiones.add(nuevaSesion);

            }
            JOptionPane.showMessageDialog(null, "Inscripciones exitosas");
        } else {
            JOptionPane.showMessageDialog(null, "Debe ser mayor a 0");
            escribirArchivo("errores.txt", "Numero de inscripcion Invalido", null, 2);
        }

        servicios();
    }

    public static void escribirArchivo(String nombreArchivo, String cambioRealizado, Asistente infou, int formatoE) {

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String fechaA = formato.format(LocalDateTime.now());

        try (FileWriter fw = new FileWriter(nombreArchivo, true); BufferedWriter bw = new BufferedWriter(fw); PrintWriter mostrar = new PrintWriter(bw)) {
            if (formatoE == 1) {
                String formatoRegistroU = fechaA + " Nombre Usuario: " + infou.getNombre() + " - Identificacion: " + infou.getCedula() + " - Asistencia a: " + infou.getListaSesionesAsistidas() + " \n";
                mostrar.print(formatoRegistroU);
            } else if (formatoE == 2) {
                String formatoErrores = fechaA + " - " + cambioRealizado + "\n";
                mostrar.print(formatoErrores);
            } else {
                String formatoRegistroCambios = fechaA + " - " + cambioRealizado + " -  Nombre Usuario: " + infou.getNombre() + " - Identificacion: " + infou.getCedula() + " \n";
                mostrar.print(formatoRegistroCambios);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al escribir en el archivo" + e.getMessage());

        }
    }

    public static void creacionArchivo(String nombreArchivo) {
        File archivo = new File(nombreArchivo);
        try {
            if (archivo.createNewFile()) {
                JOptionPane.showMessageDialog(null, "Archivo Creado Exitosamente");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al crear el archivo: " + e.getMessage());
        }
    }

    public static void GestionArchivos() {

        creacionArchivo("Inscripciones.txt");
        creacionArchivo("asistencias.txt");
        creacionArchivo("errores.txt");
    }
}
