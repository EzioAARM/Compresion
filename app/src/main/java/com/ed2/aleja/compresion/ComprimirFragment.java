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
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

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
<<<<<<< HEAD
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("*/*");
=======
                Intent intent = new Intent()
                        .addCategory(Intent.CATEGORY_OPENABLE)
                        .setType("*/*")
                        .setAction(Intent.ACTION_OPEN_DOCUMENT);
>>>>>>> ee395931246285828fcd26746bb7b16764b44738
                startActivityForResult(Intent.createChooser(intent, "Seleccionar archivo"), valorRetornado);
            }
        });
        Button comprimir = (Button) rootView.findViewById(R.id.comprimir_archivo);
        comprimir.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                compresor = new huffmanCoding(uri, rootView.getContext());

            }
        });
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            texto = "No se selecciono ningún archivo";
            Toast.makeText(this.getContext(), texto, Toast.LENGTH_SHORT);
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
