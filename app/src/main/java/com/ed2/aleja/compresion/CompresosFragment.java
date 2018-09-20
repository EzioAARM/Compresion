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
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class CompresosFragment extends Fragment {

    View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.compresos_fragment, container, false);
        try {
            String[] contenido = null;
            boolean esHuff = true;
            ArrayList<ListViewItem> listadoItems = new ArrayList<>();
            int i = 0;
            ArrayList<String> listadoEnFragment = ListadoCompresos.getInstancia().compresos;
            while (i < listadoEnFragment.size()) {
                contenido = ListadoCompresos.getInstancia().compresos.get(i).split("\\\\");
                if (contenido[0].equals("1"))
                    esHuff = true;
                else
                    esHuff = false;
                listadoItems.add(new ListViewItem(esHuff, contenido[1], Double.parseDouble(contenido[2]), Double.parseDouble(contenido[3])));
                i++;
            }
            AdaptadorListViewItem adaptadorListViewItem = new AdaptadorListViewItem(listadoItems, rootView.getContext());
            ListView listViewCompresos = (ListView) rootView.findViewById(R.id.listview_compresos);
            listViewCompresos.setAdapter(adaptadorListViewItem);
        } catch (Exception ex) {
            Toast.makeText(rootView.getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        return rootView;
    }
}
