package com.ed2.aleja.compresion;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class DescomprimirFragment extends Fragment {

    private int valorRetornado = 1;
    public String nombre;
    CharSequence texto = "";
    Uri uri = null;
    View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.descomprimir_fragment, container, false);

        // Muestra dialogo de seleccionar archivo
        Button boton = rootView.findViewById(R.id.examinar_descomprimir);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent()
                        .addCategory(Intent.CATEGORY_OPENABLE)
                        .setType("*/*")
                        .setAction(Intent.ACTION_OPEN_DOCUMENT);
                startActivityForResult(Intent.createChooser(intent, "Seleccionar archivo"), valorRetornado);
            }
        });

        // Boton para hacer la descompresion del archivo
        final Button comprimir = rootView.findViewById(R.id.descomprimir_archivo_alone);
        comprimir.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText nombreArchivoCompreso = rootView.findViewById(R.id.nombre_archivo_descompreso);
                if (nombreArchivoCompreso.getText().length() != 0){
                    try {
                        // Lee el archivo y carga el texto a la app
                        InputStream inputStream = rootView.getContext().getContentResolver().openInputStream(uri);
                        BufferedReader lector = new BufferedReader(new InputStreamReader(inputStream));
                        String textoArchivo = "";
                        int caracter = lector.read();
                        while (caracter != -1) {
                            textoArchivo += (char) caracter;
                            caracter = lector.read();
                        }
                        TextView nombreDelArchivo = (TextView) rootView.findViewById(R.id.nombre_archivo);
                        String[] extension = nombreDelArchivo.getText().toString().split("\\.");
                        if (extension[extension.length - 1].contains("huff")) {
                            // Crea la clase que realiza la descompresión por huffman
                            huffmanCoding compresor = new huffmanCoding(textoArchivo, rootView.getContext());
                            compresor.Descomprimir();
                        } else {
                            // Crea la clase que realiza la descompresión por lzw
                            CompresionLzw lzw = new CompresionLzw(textoArchivo, rootView.getContext());
                            lzw.Descomprimir();
                        }

                        Toast.makeText(getActivity(), "Se realizó la descompresión del archivo", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(rootView.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    CharSequence textoError = "Debe ingresar un nombre para el archivo";
                    Toast.makeText(getActivity(), textoError, Toast.LENGTH_LONG).show();
                }
            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            texto = "No se selecciono ningún archivo";
            Toast.makeText(getActivity(), texto, Toast.LENGTH_SHORT).show();
        } else if ((resultCode == RESULT_OK) && (requestCode == valorRetornado )) {
            uri = data.getData();
            File archivo = new File(uri.toString());
            Cursor cursor = null;
            if (uri.toString().startsWith("content://")) {
                try {
                    cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        nombre = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } finally {
                    cursor.close();
                }
            } else if (uri.toString().startsWith("file://")) {
                nombre = archivo.getAbsolutePath().toString();
            }
            TextView mostrarUbicacion = rootView.findViewById(R.id.nombre_archivo);
            mostrarUbicacion.setText(nombre);
        }
    }
}
