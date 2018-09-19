package com.ed2.aleja.compresion;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CompresosFragment extends Fragment {

    View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.compresos_fragment, container, false);
        try {
            InputStream archivoComrpesos = getResources().openRawResource(R.raw.compresos);
            BufferedReader lector = new BufferedReader(new InputStreamReader(archivoComrpesos));
            String linea;
            String[] contenido = null;
            boolean esHuff = true;
            ArrayList<ListViewItem> listadoItems = new ArrayList<>();
            while ((linea = lector.readLine()) != null) {
                contenido = linea.split("\\\\");
                if (contenido[0].equals("1"))
                    esHuff = true;
                else
                    esHuff = false;
                listadoItems.add(new ListViewItem(esHuff, contenido[1], Double.parseDouble(contenido[2]), Double.parseDouble(contenido[3])));
            }
            archivoComrpesos.close();
            AdaptadorListViewItem adaptadorListViewItem = new AdaptadorListViewItem(listadoItems, rootView.getContext());
            ListView listViewCompresos = (ListView) rootView.findViewById(R.id.listview_compresos);
            listViewCompresos.setAdapter(adaptadorListViewItem);
        } catch (IOException ex) {
            Log.e("Listado de compresos", ex.getMessage());
        }
        return rootView;
    }
}
