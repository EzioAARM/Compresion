package com.ed2.aleja.compresion;

import android.content.Context;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CompresionLzw {
    private String TextoArchivo;
    private Context Contexto;
    private Map<String, Integer> TablaCaracteres = new TreeMap<>();
    private ArrayList<String> TablaEscribir = new ArrayList<>();
    int contadorCaracteres = 2;

    public String NombreArchivoNuevo = "";
    public String NombreOriginalArchivo = "";
    public String ubicacionArchivo = "";

    public CompresionLzw(String contenido, Context contexto) {
        TextoArchivo = contenido;
        Contexto = contexto;
    }

    // Proceso de compresión
    public void Comprimir() throws Exception {
        ObtenerCaracteres();
        List<Integer> Compreso = new ArrayList<>();
        String actual = "", siguiente = "";
        int compreso = 0;
        for (int i = 1; i < TextoArchivo.length() - 1; i++){
            actual = String.valueOf(TextoArchivo.charAt(i));
            for (int j = i + 1; j < TextoArchivo.length(); j++) {
                siguiente = String.valueOf(TextoArchivo.charAt(j));
                if (TablaCaracteres.get(actual + siguiente) != null) {
                    i++;
                    actual += siguiente;
                    siguiente = String.valueOf(TextoArchivo.charAt(j));
                } else {
                    compreso = TablaCaracteres.get(actual);
                    TablaCaracteres.put(actual + siguiente, contadorCaracteres);
                    contadorCaracteres++;
                    j = TextoArchivo.length();
                }
            }
            Compreso.add(TablaCaracteres.get(actual));
        }
        String TextoCompreso = "";
        int NumeroActual = 0;
        for (int i = 0; i < Compreso.size(); i++) {
            NumeroActual = Compreso.get(i);
            TextoCompreso += String.valueOf((char) NumeroActual);
        }
        String ContenidoTabla = getContenidoTabla();
        escribirArchivoCompreso(NombreArchivoNuevo, NombreOriginalArchivo + "|" + ContenidoTabla + "||" + TextoCompreso);
    }

    private String getContenidoTabla() {
        String contenido = "";
        for (int i = 0; i < TablaEscribir.size(); i++) {
            contenido += TablaEscribir.get(i);
        }
        return contenido;
    }

    private boolean escribirArchivoCompreso(String nombreArchivo, String contenido) throws Exception {
        File directorio = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            directorio = new File(Environment.getExternalStorageDirectory() + "/CompresionesEstructuras/");
        else
            directorio = new File(Contexto.getFilesDir() + "/CompresionesEstructuras/");
        boolean dirCre = true;
        if (!directorio.exists())
            dirCre = directorio.mkdirs();
        if (!dirCre) {
            throw new Exception("No se pudo crear la ruta " + directorio.getAbsolutePath());
        }
        File archivoEscribir = new File(directorio.getAbsolutePath() + "/" + nombreArchivo + ".lzw");
        if (!archivoEscribir.createNewFile())
            throw new Exception("No se pudo crear el archivo " + directorio.getAbsolutePath());
        FileOutputStream fileOutputStream = new FileOutputStream(archivoEscribir);
        fileOutputStream.write(contenido.getBytes());
        fileOutputStream.close();
        ubicacionArchivo = archivoEscribir.getAbsolutePath();
        return true;
    }

    private void ObtenerCaracteres(){
        String caracter = String.valueOf(TextoArchivo.charAt(1));
        TablaCaracteres.put(caracter, contadorCaracteres);
        TablaEscribir.add(caracter + "01");
        for (int i = 2; i < TextoArchivo.length(); i++) {
            caracter = String.valueOf(TextoArchivo.charAt(i));
            if (TablaCaracteres.get(caracter) == null) {
                TablaCaracteres.put(caracter, contadorCaracteres);
                if (contadorCaracteres <= 9) {
                    TablaEscribir.add(caracter + "0" + String.valueOf(contadorCaracteres));
                } else {
                    TablaEscribir.add(caracter + String.valueOf(contadorCaracteres));
                }
                contadorCaracteres++;
            }
        }
    }

    // Extras
    public void setNombreOriginalArchivo(String nombre) {
        NombreOriginalArchivo = nombre;
    }

    public String getNombreOriginalArchivo() {
        return NombreOriginalArchivo;
    }

    public void setNombreArchivoNuevo (String nombre){
        NombreArchivoNuevo = nombre;
    }

    public String getNombreArchivoNuevo () {
        return NombreArchivoNuevo;
    }
}
