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
    public  ArrayList<Character> simbolos = new ArrayList<Character>();
    public int times;
    public huffmanCoding(String ruta){
        getSimbolos(ruta);
    }

    public void getFrecuencias (char ch){


        for (int i = 0; i < simbolos.size();i++) {
            if(ch == simbolos.get(i)) {
                times++;
            }
            else if( simbolos.isEmpty() == true){
                times++;
            }
            else {

            }
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

        }
        catch (Exception e)
        {

        }

    }

}
