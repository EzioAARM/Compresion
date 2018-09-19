package com.ed2.aleja.compresion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

public class AdaptadorListViewItem extends BaseAdapter {

    private ArrayList<ListViewItem> ItemsList;
    private Context Contexto;

    public AdaptadorListViewItem(ArrayList<ListViewItem> itemsList, Context contexto) {
        ItemsList = itemsList;
        Contexto = contexto;
    }

    @Override
    public int getCount() {
        return ItemsList.size();
    }

    @Override
    public Object getItem(int position) {
        return ItemsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(Contexto).inflate(R.layout.compresos_list_item_layout, null);
        ImageView imgMetodoComrpesion = (ImageView) convertView.findViewById(R.id.img_tipo_compresion);
        TextView nombreArchivoCompreso = (TextView) convertView.findViewById(R.id.nombre_archivo);
        TextView ratioCompresion = (TextView) convertView.findViewById(R.id.ratio_compresion);
        TextView factorComrpesion = (TextView) convertView.findViewById(R.id.factor_compresion);

        ListViewItem item = (ListViewItem) getItem(position);

        if (item.getImgCompresion() == 1)
            imgMetodoComrpesion.setImageResource(R.drawable.ic_huffman_light);
        nombreArchivoCompreso.setText(item.getNombreArchivoCompreso());
        ratioCompresion.setText(item.getRatioCompresion());
        factorComrpesion.setText(item.getFactorCompresion());


        return convertView;
    }
}
