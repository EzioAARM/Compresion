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

    public boolean checkR (char ch){

        boolean flag = false;
        for (int i = 0; i < simbolos.size();i++) {
            if(ch == simbolos.get(i)) {
                times++;
                flag = true;
            }
        }
        return flag;
    }

    public void getSimbolos(String ruta){
        try {
            FileReader Alector = new FileReader(ruta);
            BufferedReader lector = new BufferedReader(Alector);
            char ch = (char)lector.read();
            while (ch != -1)
            {
                if(checkR(ch))
                {
                    simbolos.add(ch);
                }

            }

        }
        catch (Exception e)
        {

        }

    }

}
