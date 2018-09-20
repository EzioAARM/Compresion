package com.ed2.aleja.compresion;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class DescomprimirFragment extends Fragment {

    View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.descomprimir_fragment, container, false);
        Button botonPrueba = (Button) rootView.findViewById(R.id.boton_test);
        botonPrueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri myUri = Uri.parse("http://www.google.com");
                huffmanCoding huff = new huffmanCoding(myUri, rootView.getContext());
                try {
                    huff.escribirArchivoCompreso("archivoPruebaEx", "HolaHolaHola");
                } catch (Exception e) {
                    Toast.makeText(rootView.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        return rootView;
    }
}
