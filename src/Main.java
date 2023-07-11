import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        String url = "C:\\Users\\wdcamacho\\Desktop\\Cositas\\Localizaciones\\Listado de provincias";
        String urlDestino = "C:\\Users\\wdcamacho\\Desktop\\Cositas\\Localizaciones\\Listado provincias no repetidas";

        File carpetaRaiz = new File(url);
        File elementosSeleccionar = null;
        String[] listado = carpetaRaiz.list();
        String urlActual = "";

        /*
        for (int i = 0; i < listado.length; i++) {

            List<String> listaLocalizaciones = new ArrayList<>();
            urlActual = url + "\\" + listado[i];
            elementosSeleccionar = new File(urlActual + "\\Lista entera " + listado[i] + ".txt");
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(elementosSeleccionar), "ISO-8859-1"));
                String linea;
                System.out.println("Estoy ahora mismo en: " + listado[i]);
                while ((linea = br.readLine()) != null) {
                    listaLocalizaciones.add(linea.substring(6, linea.length()));
                }
                System.out.println("Cantidad lista actual: " + listaLocalizaciones.size());
                listaLocalizaciones.remove(0);

                listaLocalizaciones = listaLocalizaciones.stream().distinct().collect(Collectors.toList());

                System.out.println("Despues de limpieza: " + listaLocalizaciones.size());

                //System.out.println("Total de registros: " + contadorTotalRegistros);

                //String ruta = urlActual + "\\Lista entera " + listado[i] + ".txt";
                File file = new File(urlDestino + "\\Lista entera " + listado[i] + ".csv");

                try {
                    String contenido = "Tipo Via;Calle;Codigo Postal;Municipio;Provincia\n";
                    if (!file.exists()) {
                        file.createNewFile();
                    }

                    Writer escribe = null;

                    escribe = new BufferedWriter(new OutputStreamWriter(
                            new FileOutputStream(file), "ISO-8859-1"));

                    for (String direccion : listaLocalizaciones) {
                        contenido += direccion + "\n";
                    }

                    listaLocalizaciones.clear();

                    System.out.println("Se ha creado " + listado[i]);
                    escribe.write(contenido);

                    escribe.flush();
                    escribe.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        */

        for (int i = 0; i < listado.length; i++) {

            urlActual = url + "\\" + listado[i];

            ExtraccionFichero extraccionFichero = new ExtraccionFichero();
            elementosSeleccionar = new File (urlActual);

            String[] listadoStrings = elementosSeleccionar.list();
            String urlSinCP = "";
            String urlConCP = "";

            for (int j = 0; j < listadoStrings.length; j++) {
                if (listadoStrings[j].contains("v"))  urlSinCP = urlActual + "\\" + listadoStrings[j];
                if (listadoStrings[j].contains("t"))  urlConCP = urlActual + "\\" + listadoStrings[j];
            }

            extraccionFichero.gestionFichero(urlConCP, 1, listado[i]);
            extraccionFichero.gestionFichero(urlSinCP, 0, listado[i]);

            ArrayList<Direccion> listaDirecciones = extraccionFichero.obtenerCodigoPostal(extraccionFichero.listaDirecciones, extraccionFichero.listaDireccionesCP);

            extraccionFichero.crearDireccionFormateada(listaDirecciones, listado[i], urlActual);
            System.out.println("Archivo creado: " + listado[i]);
        }
    }

}