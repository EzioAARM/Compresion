package com.ed2.aleja.compresion;

import android.app.ListActivity;

public class ListViewItem {
    private int ImgCompresion;
    private String NombreArchivoCompreso;
    private String RatioCompresion;
    private String FactorCompresion;
    private String RutaCompresion;

    public ListViewItem(boolean esHuffman, String nombreArchivoCompreso, double ratioCompresion, double factorCompresion, String ruta){
        if (esHuffman){
            ImgCompresion = 1;
        } else {
            ImgCompresion = 2;
        }
        NombreArchivoCompreso = nombreArchivoCompreso;
        RatioCompresion = "Ratio: " + String.valueOf(ratioCompresion);
        FactorCompresion = "Factor: " + String.valueOf(factorCompresion);
        RutaCompresion = ruta;
    }

    public int getImgCompresion(){
        return ImgCompresion;
    }

    public String getNombreArchivoCompreso(){
        return NombreArchivoCompreso;
    }

    public String getRatioCompresion(){
        return RatioCompresion;
    }

    public String getFactorCompresion(){
        return FactorCompresion;
    }

    public String getRutaCompresion() {
        return RutaCompresion;
    }
}
