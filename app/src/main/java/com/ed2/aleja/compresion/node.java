package com.ed2.aleja.compresion;

public class node {

    public node(int freq, char caracter){
        prob = freq;
        aChar = caracter;

    }

    public int prob;
    public char aChar;
    public node left;
    public node right;
}
