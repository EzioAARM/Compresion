package com.ed2.aleja.compresion;

import android.util.Log;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class huffmanCoding {
    public ArrayList<node> listaNodos = new ArrayList<>();
    public Map<Integer, Character> simbolosO = new TreeMap<Integer, Character>();
    public ArrayList<Character> simbolos = new ArrayList<Character>();
    public int times;
    public huffmanCoding(String ruta){
        getSimbolos(ruta);
    }

    public void getFrecuencias (String ruta){

        try {
            FileInputStream archivo = new FileInputStream(new File(ruta));
            InputStreamReader Alector = new InputStreamReader(archivo);
            BufferedReader lector = new BufferedReader(Alector);
            char actual;
            for (int i = 0; i < simbolos.size(); i++){
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
            }
            lector.close();
            Alector.close();
            build();
        }
        catch (Exception e){

        }
    }


    public  void build(){
        Collections.sort(listaNodos, new Comparator<node>() {
            @Override
            public int compare(node o1, node o2) {
                int temp = 0;
                if(o1.prob < o2.prob) {
                    temp = -1;
                }
                return temp;
            }
        });
        node temp;
        int freqT;
        while (listaNodos.size()>1) {
            freqT = listaNodos.get(0).prob + listaNodos.get(1).prob;
            temp = new node(freqT, 'む');
            temp.right = listaNodos.get(0);
            temp.left = listaNodos.get(1);
            listaNodos.remove(0);
            listaNodos.remove(1);
            listaNodos.add(temp);
            Collections.sort(listaNodos, new Comparator<node>() {
                @Override
                public int compare(node o1, node o2) {
                    int temp = 0;
                    if(o1.prob < o2.prob) {
                        temp = -1;
                    }
                    return temp;
                }
            });
        }
        traverse(listaNodos.get(0));
    }

    public void traverse(node arbol){
        node root = arbol;
        node temp = root;
        String codeWord = "";
        while (temp.left != null){
            temp = temp.left;
            codeWord = codeWord + "0";
        }

    }

    public void getSimbolos(String ruta){
        try {
            FileInputStream archivo = new FileInputStream(new File(ruta));
            InputStreamReader Alector = new InputStreamReader(archivo);
            BufferedReader lector = new BufferedReader(Alector);
            int ch = lector.read();
            char caracter = (char)ch;
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
                ch = lector.read();
                caracter = (char)ch;
            }
            Alector.close();
            lector.close();
            getFrecuencias(ruta);
        }
        catch (Exception e)
        {
            Log.println(Log.DEBUG," ", e.toString());
        }

    }

}
