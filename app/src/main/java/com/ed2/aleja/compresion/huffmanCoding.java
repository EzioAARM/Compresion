package com.ed2.aleja.compresion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
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
            FileReader Alector = new FileReader(ruta);
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
            build();
        }
        catch (Exception e){

        }
    }

    public  void build(){
        for(int i = 0; i < listaNodos.size(); i++) {

        }
    }

    public void getSimbolos(String ruta){
        try {
            FileReader Alector = new FileReader(ruta);
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
            getFrecuencias(ruta);
        }
        catch (Exception e)
        {

        }

    }

}
