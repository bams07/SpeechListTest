package com.bams.android.examenspeechtotext;

import android.content.ClipData;
import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by bams on 2/23/17.
 */

public class ProductListAdapter extends BaseAdapter {
    private Context context; //context
    private ArrayList<Product> products; //data source of the list adapter
    TextToSpeech tts;


    //public constructor
    public ProductListAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;

        tts = new TextToSpeech(context.getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                tts.setLanguage(Locale.US);
            }
        });
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.layout_list_view_row_items, parent, false);
        }

        final Product currentItem = (Product) getItem(position);
        TextView txtName = (TextView) convertView.findViewById(R.id.rowTxtName);
        final TextView txtStatus = (TextView) convertView.findViewById(R.id.rowTxtStatus);
        final TextView txtDate = (TextView) convertView.findViewById(R.id.rowTxtDate);
        Button btnSpeech = (Button) convertView.findViewById(R.id.btnSpeech);
        Button btnBought = (Button) convertView.findViewById(R.id.btnBought);

        if(currentItem.status == "BOUGHT"){
            btnBought.setVisibility(Button.INVISIBLE);
        }


        btnSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = currentItem.name;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null, null);
                } else {
                    tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });

        btnBought.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar date =  Calendar.getInstance();
                SimpleDateFormat ft = new SimpleDateFormat("d-M-yyyy H:mm:ss");
                currentItem.setDate(date.getTime());
                currentItem.setStatus("BOUGHT");
                v.setVisibility(Button.INVISIBLE);
                txtDate.setText(ft.format(date.getTime()));
                txtStatus.setText("BOUGHT");
            }
        });

        txtName.setText(currentItem.name);
        txtStatus.setText(currentItem.status);
        txtDate.setText(currentItem.getDate());
        return convertView;
    }
}
