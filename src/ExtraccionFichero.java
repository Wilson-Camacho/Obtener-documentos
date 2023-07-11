import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class ExtraccionFichero {
    ArrayList<Direccion> listaDirecciones;
    ArrayList<Direccion> listaDireccionesCP;
    File archivo = null;
    public ExtraccionFichero(){
        listaDirecciones = new ArrayList<>();
        listaDireccionesCP =  new ArrayList<>();
    }

    public void gestionFichero(String urlFichero, int tipo, String provincia){
        try {
            archivo = new File (urlFichero);
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(archivo), "ISO-8859-1"));

            String linea;

            if (tipo == 1){
                while( (linea = br.readLine()) != null){
                    rellanarDireccionesConCP(linea);
                }
            }else{
                while( (linea = br.readLine()) != null){
                    rellenarDirecciones(linea);
                }
            }
            br.close();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Fallo en: " + provincia);
        }finally{
            try{

            }catch (Exception e2){
                e2.printStackTrace();
                System.out.println("Fallo en: " + provincia);
            }
        }
    }

    public void rellanarDireccionesConCP(String linea){
        String obtenerFormatoDireccion = linea.substring(110, linea.length());

        String municipio = obtenerFormatoDireccion.substring(0, 25);
        obtenerFormatoDireccion = obtenerFormatoDireccion.substring(50, obtenerFormatoDireccion.length());

        String obtenerCodigoDireccion = obtenerFormatoDireccion.substring(0, 30);
        obtenerFormatoDireccion = obtenerFormatoDireccion.substring(30, obtenerFormatoDireccion.length());

        String codigoPostal = obtenerFormatoDireccion.substring((obtenerFormatoDireccion.length() - 16), (obtenerFormatoDireccion.length() - 11));
        String codigo = "";
        String calle = "";

        //System.out.println(obtenerFormatoDireccion);

        if (obtenerCodigoDireccion.contains("00000")){
            codigo = obtenerFormatoDireccion.substring(5, 62);
            calle = obtenerFormatoDireccion.substring(0, 5);
        }else{
            codigo = obtenerCodigoDireccion.substring(0, 5);
            calle = obtenerCodigoDireccion.substring(5, obtenerCodigoDireccion.length());
        }

        Direccion direccion = new Direccion();

        direccion.setCalle(calle.trim());
        direccion.setCodigo(codigo.trim());
        direccion.setCodigoPostal(codigoPostal.trim());
        direccion.setMunicipio(municipio.trim());

        listaDireccionesCP.add(direccion);

    }

    public void rellenarDirecciones (String linea){

        String direccion = linea.substring(22, linea.length());

        String obtenerCodigo = direccion.substring(0, 5);
        direccion = direccion.substring(5, direccion.length());
        String obtenerTipoVia = direccion.substring(0, direccion.indexOf("0"));
        direccion = direccion.substring((direccion.length() - 25), direccion.length());

        Direccion direccion1 = new Direccion(
                direccion.trim(),
                obtenerCodigo.trim(),
                obtenerTipoVia.trim()
        );

        listaDirecciones.add(direccion1);
    }

    public ArrayList<Direccion> obtenerCodigoPostal(ArrayList<Direccion> listaDirecciones, ArrayList <Direccion> listaDireccionesCP){
        ArrayList <Direccion> listaConCP = new ArrayList<>();

        for (Direccion direccion : listaDirecciones) {
            String codDireccion = direccion.getCodigo() + direccion.getCalle();

            for (Direccion direccionCP : listaDireccionesCP) {
                String codDireccionCP = direccionCP.getCodigo() + direccionCP.getCalle();

                if(!codDireccion.equals(codDireccionCP)) continue;
                if(direccionCP.getCodigoPostal() == null) continue;
                if(Integer.parseInt(direccionCP.getCodigoPostal()) == 0) continue;

                direccion.setCodigoPostal(direccionCP.codigoPostal);
                direccion.setMunicipio(direccionCP.getMunicipio());

                listaConCP.add(direccion);
                break;
            }
        }

        Collections.sort(listaConCP);
        return listaConCP;
    }

    public void crearDireccionFormateada (ArrayList <Direccion> listaDirecciones, String provincia, String urlActual){
        String ruta = urlActual + "\\Lista entera " + provincia + ".txt";
        File file = new File(ruta);

        try {
            String contenido = "Codigo;Tipo Via;Calle;Codigo Postal;Municipio;Provincia\n";
            if (!file.exists()) {
                file.createNewFile();
            }

            Writer escribe = null;

            escribe = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file), "ISO-8859-1"));

            //System.out.println("Cantidad: " + listaDirecciones.size() +  " en " + provincia);

            for (Direccion direccion : listaDirecciones) {
                contenido += direccion.toString() + ";" + provincia + "\n";
            }

            escribe.write(contenido);

            escribe.flush();
            escribe.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
