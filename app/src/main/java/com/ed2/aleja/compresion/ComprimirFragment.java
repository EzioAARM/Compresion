package com.ed2.aleja.compresion;

import android.content.Context;
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
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.Buffer;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class ComprimirFragment extends Fragment {

    private int valorRetornado = 1;
    public huffmanCoding compresor;
    public String nombre;
    CharSequence texto = "";
    Uri uri = null;
    View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.comprimir_fragment, container, false);
        Button boton = (Button) rootView.findViewById(R.id.examinar_comprimir);
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
        final Button comprimir = (Button) rootView.findViewById(R.id.comprimir_archivo);
        comprimir.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText nombreArchivoCompreso = (EditText) rootView.findViewById(R.id.nombre_archivo_compreso);
                if (nombreArchivoCompreso.getText().length() != 0){
                    try {
                        compresor = new huffmanCoding(uri, rootView.getContext());
                        EditText nombreArchivoGuardar = (EditText) rootView.findViewById(R.id.nombre_archivo_compreso);
                        compresor.setNombreOriginal(nombreArchivoGuardar.getText().toString());
                        compresor.getSimbolos(uri);
                        OutputStreamWriter escritor = new OutputStreamWriter(rootView.getContext().openFileOutput("compresos.compresos", Context.MODE_APPEND));
                        escritor.write("true\\\\" + compresor.getNombreOriginal() + "\\\\ratio\\\\factor\\\\" + compresor.ubicacionArchivo + "\n");
                        escritor.close();
                        Toast.makeText(getActivity(), "Se realizó la compresión del archivo", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(rootView.getContext(), "Hubo un error escribiendo el archivo", Toast.LENGTH_LONG).show();
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
            TextView mostrarUbicacion = rootView.findViewById(R.id.nombreArchivo);
            mostrarUbicacion.setText(nombre);
        }
    }
}
