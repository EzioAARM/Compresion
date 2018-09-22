package com.ed2.aleja.compresion;

import android.widget.ListView;

import java.util.ArrayList;

public class ListadoCompresos {
    public static ListadoCompresos instancia = null;
    public ArrayList<String> compresos = new ArrayList<>();

    public static synchronized ListadoCompresos getInstancia() {
        if (instancia == null) {
            instancia = new ListadoCompresos();
        }
        return instancia;
    }
}
