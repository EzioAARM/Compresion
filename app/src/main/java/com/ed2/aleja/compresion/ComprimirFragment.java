package com.ed2.aleja.compresion;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class ComprimirFragment extends Fragment {

    private int valorRetornado = 1;
    View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.comprimir_fragment, container, false);
        Button boton = (Button) rootView.findViewById(R.id.examinar_comprimir);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(Intent.createChooser(intent, "Seleccionar archivo"), valorRetornado);
            }
        });
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CharSequence texto = "";
        if (resultCode == RESULT_CANCELED) {
            texto = "No se selecciono ningún archivo";
            Toast.makeText(this.getContext(), texto, Toast.LENGTH_SHORT);
        }
        if ((resultCode == RESULT_OK) && (requestCode == valorRetornado )) {
            Uri uri = data.getData();
            TextView mostrarUbicacion = (TextView) rootView.findViewById(R.id.nombre_archivo);
            mostrarUbicacion.setText(uri.getPath());
        }
    }
}
