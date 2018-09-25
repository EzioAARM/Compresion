package com.ed2.aleja.compresion;

import android.content.Context;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

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
    private Map<Integer, String> TablaCaracteresInversa = new TreeMap<>();
    private ArrayList<String> TablaEscribir = new ArrayList<>();
    int contadorCaracteres = 2;
    public double Factor;
    public double Razon;

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
        Compreso.add(1);
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
        Factor = TextoArchivo.length() / (NombreOriginalArchivo.length() + 1 + ContenidoTabla.length() + 2 + TextoCompreso.length());
        Razon = (NombreOriginalArchivo.length() + 1 + ContenidoTabla.length() + 2 + TextoCompreso.length()) / TextoArchivo.length();
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
        String caracter = String.valueOf(TextoArchivo.charAt(0));
        TablaCaracteres.put(caracter, contadorCaracteres);
        TablaEscribir.add(caracter + "01");
        for (int i = 1; i < TextoArchivo.length(); i++) {
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

    // Proceso de descompresión
    public void Descomprimir() throws Exception {
        separarContenido();
        String CadenaDescompresa = "";
        int asciiAnterior = (int) TextoArchivo.charAt(0);
        String anterior = "";
        int asciiActual = 0;
        String actual = "";
        for (int i = 1; i < TextoArchivo.length(); i++) {
            asciiActual = (int) TextoArchivo.charAt(i);
            actual = String.valueOf(TablaCaracteresInversa.get(asciiActual));
            actual = String.valueOf(actual.charAt(0));
            if (TablaCaracteres.get(anterior + actual) == null) {
                contadorCaracteres++;
                TablaCaracteres.put(anterior + actual, contadorCaracteres);
                TablaCaracteresInversa.put(contadorCaracteres, anterior + actual);
            }
            CadenaDescompresa += TablaCaracteresInversa.get(asciiActual);
            asciiAnterior = asciiActual;
            anterior = String.valueOf(TablaCaracteresInversa.get(asciiAnterior));
        }
        escribirArchivoDescompreso(getNombreOriginalArchivo(), CadenaDescompresa);
    }

    public void escribirArchivoDescompreso(String nombreArchivo, String contenido) throws Exception {
        File directorio = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            directorio = new File(Environment.getExternalStorageDirectory() + "/DescompresionesEstructuras/");
        else
            directorio = new File(Contexto.getFilesDir() + "/DescompresionesEstructuras/");
        boolean dirCre = true;
        if (!directorio.exists())
            dirCre = directorio.mkdirs();
        if (!dirCre) {
            throw new Exception("No se pudo crear la ruta " + directorio.getAbsolutePath());
        }
        File archivoEscribir = new File(directorio.getAbsolutePath() + "/" + nombreArchivo);
        if (archivoEscribir.exists()) {
            throw new Exception("El archivo " + nombreArchivo + " ya existe");
        } else {
            if (!archivoEscribir.createNewFile())
                throw new Exception("No se pudo crear el archivo " + archivoEscribir.getAbsolutePath());
            FileOutputStream fileOutputStream = new FileOutputStream(archivoEscribir);
            fileOutputStream.write(contenido.getBytes());
            fileOutputStream.close();
        }
    }

    private void separarContenido() {
        String nombre = "";
        for (int i = 0; i < TextoArchivo.length(); i++) {
            if (TextoArchivo.charAt(i) != '|') {
                nombre += String.valueOf(TextoArchivo.charAt(i));
            } else {
                TextoArchivo = TextoArchivo.substring(i + 1);
                i = TextoArchivo.length();
            }
        }
        setNombreOriginalArchivo(nombre);
        String tablaCaracteres = "";
        for (int i = 0; i < TextoArchivo.length(); i++) {
            if (String.valueOf(TextoArchivo.charAt(i)).equals("|") && String.valueOf(TextoArchivo.charAt(i + 1)).equals("|")) {
                TextoArchivo = TextoArchivo.substring(i + 2);
                i = TextoArchivo.length();
            } else {
                tablaCaracteres += TextoArchivo.charAt(i);
            }
        }
        String caracterAparicion = "";
        String caracter = "";
        while (tablaCaracteres.length() > 0) {
            caracter = String.valueOf(tablaCaracteres.charAt(0));
            tablaCaracteres = tablaCaracteres.substring(1);
            for (int i = 0; i < 2; i++) {
                caracterAparicion += String.valueOf(tablaCaracteres.charAt(i));
            }
            tablaCaracteres = tablaCaracteres.substring(2);
            TablaCaracteres.put(caracter, Integer.parseInt(caracterAparicion));
            TablaCaracteresInversa.put(Integer.parseInt(caracterAparicion), caracter);
            caracter = "";
            caracterAparicion = "";
        }
        contadorCaracteres = TablaCaracteresInversa.size();
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
