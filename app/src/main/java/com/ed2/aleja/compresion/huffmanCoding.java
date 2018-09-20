package com.ed2.aleja.compresion;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.ContextMenu;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class huffmanCoding {
    public ArrayList<node> listaNodos = new ArrayList<>();
    public Map<Integer, Character> simbolosO = new TreeMap<Integer, Character>();
    public Map<Character, String> tabla = new TreeMap<>();
    public ArrayList<Character> simbolos = new ArrayList<Character>();
    public ArrayList<node> hojas = new ArrayList<>();
    public int times;
    public int decodeCount = 0;
    public int pointer = 0;
    public int flag = 1;
    public int indice = 0;
    public String outPut = "";
    public ArrayList<Integer> bitArray = new ArrayList<>();
    public String res;
    public String codeWord;
    public Uri miUri;
    private Context Contexto = null;

    public huffmanCoding(Uri uri, Context contextoApp){
        Contexto = contextoApp;
        getSimbolos(uri);
    }

    public void getFrecuencias (Uri uri){

        try {
            char actual;
            for (int i = 0; i < simbolos.size(); i++){
                InputStream inputStream = Contexto.getContentResolver().openInputStream(uri);
                BufferedReader lector = new BufferedReader(new InputStreamReader(inputStream));
                int ch = lector.read();
                char caracter = (char)ch;
                while (ch != -1) {
                    if(caracter == simbolos.get(i)){
                        times++;
                    }
                    ch = lector.read();
                    caracter = (char)ch;
                }
                simbolosO.put(times, simbolos.get(i));
                node nodoActual = new node(times, simbolos.get(i));
                listaNodos.add(nodoActual);
                times = 0;
                lector.close();
            }
            build();
        }
        catch (Exception e){
            Log.println(Log.DEBUG," ", e.toString());
        }
    }

    public void mySort(){

        int i, j;
        node temp;
        for (i = 0; i < listaNodos.size() - 1; i++)
        {
            for (j = 0; j < listaNodos.size()-i-1; j++)
            {
                if (listaNodos.get(j).prob > listaNodos.get(j+1).prob)
                {
                    temp = listaNodos.get(j);
                    listaNodos.remove(j);
                    listaNodos.add(j+1, temp);
                }
            }
        }

    }

    public  void build(){
        mySort();
        node temp;
        int freqT;
        while (listaNodos.size()>1) {
            freqT = listaNodos.get(0).prob + listaNodos.get(1).prob;
            temp = new node(freqT, 'む');
            temp.right = listaNodos.get(0);
            temp.left = listaNodos.get(1);
            listaNodos.remove(0);
            listaNodos.remove(0);
            listaNodos.add(temp);
            mySort();
        }
        traverse(listaNodos.get(0));
        ArrayList<node> test = hojas;
    }

    public void traverse(node arbol){
        node temp = arbol;
        if ( temp == null){
            return;
        }
        traverse(temp.left, "0", "");
        traverse(temp.right, "1", "");
        escribir();
    }

    public void traverse(node arbol, String code, String codeWordParam){
        node temp = arbol;
        if ( temp == null){
            return;
        }
        traverse(temp.left, "0", codeWordParam + code);
        if (temp.esHoja()){
            temp.codeWord = codeWordParam + code;
            hojas.add(temp);
            tabla.put(temp.aChar, temp.codeWord);
        }
        traverse(temp.right, "1", codeWordParam + code);

    }

    public void decode(){
        String decode = "";
        int f = 0;
        try {
            int ch = (int)res.charAt(0);
            int size = res.length();
            char caracter = (char)ch;
            while (f < res.length()-1) {
                decode = decode + Integer.toBinaryString(ch);
                f++;
                ch = (int)res.charAt(f);
                caracter = (char)ch;

            }
            char[]  caracteres = decode.toCharArray();
            decode = "";
            int temp = 0;
            while (decodeCount < caracteres.length) {
                for (int i = 0; i < hojas.size(); i++) {
                    for (int j = temp; j < temp + hojas.get(i).codeWord.length(); j++) {
                        decode = decode + caracteres[j];



                        decodeCount++;
                    }
                    temp = decodeCount;
                    if (decode.equals(hojas.get(i).codeWord.trim())) {
                        outPut = outPut + hojas.get(i).aChar;
                    }
                    else {
                        decodeCount = 0;
                        temp = decodeCount;
                    }
                    decode = "";
                }
            }

        }
        catch (Exception e){
            Log.println(Log.DEBUG, "", e.toString());
        }
    }

    public void decode(Uri uri){
        String decode = "";
        try {
            InputStream inputStream = Contexto.getContentResolver().openInputStream(uri);
            BufferedReader lector = new BufferedReader(new InputStreamReader(inputStream));
            int ch = lector.read();
            char caracter = (char)ch;
            while (ch != -1) {
                decode = decode + Integer.toBinaryString(ch);
                ch = lector.read();
                caracter = (char)ch;
            }
            char[]  caracteres = decode.toCharArray();
            decode = "";
            while (decodeCount != caracteres.length) {
                for (int i = 0; i < hojas.size(); i++) {
                    for (int j = decodeCount; j < decodeCount + hojas.get(i).codeWord.length(); j++) {
                        decode = decode + caracteres[j];
                        decodeCount++;
                    }
                    if (decode == hojas.get(i).codeWord) {
                        outPut = outPut + hojas.get(i).aChar;
                    }
                    else {
                        decodeCount = 0;
                    }
                    decode = "";
                }
            }
        }
        catch (Exception e){

        }
    }

    public void escribir(){
        String code = "";
        char[] temp;
        try {
            InputStream inputStream = Contexto.getContentResolver().openInputStream(miUri);
            BufferedReader lector = new BufferedReader(new InputStreamReader(inputStream));
            int ch = lector.read();
            char caracter = (char)ch;
            int c = 0;
            char[] chars = new char[flag];
            while (ch != -1){
                code = tabla.get(caracter);
                temp = code.toCharArray();
                for(int i = 0; i < temp.length; i++) {
                    if(pointer == 8){
                        chars[indice] = (char)c;
                        c = 0;
                        indice++;
                        pointer = 0;
                    }
                    c = c << 1;
                    c += temp[i];
                    pointer++;
                }
                ch = lector.read();
                caracter = (char)ch;
            }
            res = new String(chars);
            decode();
        }
        catch (Exception e){
            Log.println(Log.DEBUG,"",e.toString());
        }

    }

    public void getSimbolos(Uri uri){
        miUri = uri;
        try {
            InputStream inputStream = Contexto.getContentResolver().openInputStream(uri);
            BufferedReader lector = new BufferedReader(new InputStreamReader(inputStream));
            int ch = lector.read();
            char caracter = (char)ch;
            simbolos.add(caracter);
            while (ch != -1)
            {
                for (int i = 0; i < simbolos.size(); i++){
                    if(caracter == simbolos.get(i)){
                        break;
                    }
                    else if(i == simbolos.size() -1) {
                        simbolos.add(caracter);
                    }
                }
                /*if(checkR(caracter))
                {
                    simbolosO.put(times, ch);
                }*/
                flag++;
                ch = lector.read();
                caracter = (char)ch;
            }
            lector.close();
            getFrecuencias(uri);
        }
        catch (Exception e)
        {
            Log.println(Log.DEBUG," ", e.toString());
        }

    }


    public void escribirArchivoCompreso(String nombreArchivo, String contenido) throws Exception {
        File directorio = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            directorio = new File(Environment.getExternalStorageDirectory() + "/" + nombreArchivo + ".huff");
        } else {
            directorio = new File(Contexto.getFilesDir() + "/" + nombreArchivo + ".huff");
        }
        directorio = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + nombreArchivo + ".huff");
        boolean dirCre = directorio.mkdirs();
        if (!dirCre) {
            throw new Exception("No se pudo crear la ruta " + directorio.getAbsolutePath());
        }
        FileOutputStream fileOutputStream = new FileOutputStream(directorio);
        fileOutputStream.write(contenido.getBytes());
        fileOutputStream.close();
    }

    public void escribirArchivo(String nombreArchivo, String extension, String contenido) throws Exception {
        File directorio = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            directorio = new File(Environment.getExternalStorageDirectory() + "/DescompresionEstructuras/" + nombreArchivo + ".huff");
        } else {
            directorio = new File(Contexto.getFilesDir() + "/DescompresionEstructuras/" + nombreArchivo + "." + extension);
        }
        directorio = new File(Environment.getExternalStorageDirectory() + "/DescompresionEstructuras/" + nombreArchivo + ".huff");
        boolean dirCre = directorio.createNewFile();
        if (!dirCre) {
            throw new Exception("No se pudo crear la ruta " + directorio.getAbsolutePath());
        }
        FileOutputStream fileOutputStream = new FileOutputStream(directorio);
        fileOutputStream.write(contenido.getBytes());
        fileOutputStream.close();
    }
}
