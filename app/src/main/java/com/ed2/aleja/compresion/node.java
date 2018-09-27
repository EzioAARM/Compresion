package com.ed2.aleja.compresion;

public class node {

    public node(int freq, char caracter){
        prob = freq;
        aChar = caracter;
        valorComparacion = (int) aChar;
    }

    public String codeWord;
    public int prob;
    public char aChar;
    public node left;
    public node right;
    public int valorComparacion = 0;

    public boolean esHoja(){
        if (left == null && right == null){
            return  true;
        }
        return false;
    }
}
