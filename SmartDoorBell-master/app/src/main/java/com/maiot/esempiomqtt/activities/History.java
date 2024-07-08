package com.maiot.esempiomqtt.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.maiot.esempiomqtt.MainActivity;
import com.maiot.esempiomqtt.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import MQTT.MQTTSubscriber;

public class History extends AppCompatActivity {
    private final String TAG = "Subscriber";


    private MQTTSubscriber mqttSubscriber = null;
    private Boolean isLive = false;
    private ImageButton deleteAllImageButton = null;
    private ImageButton homeImageButton = null;
    private ImageButton historyImageButton= null;
    private LinearLayout historyLinearLayout = null;
    SharedPreferences sharedPreferences = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        initViews();
        sharedPreferences = getSharedPreferences("historyNotification", Context.MODE_PRIVATE);

        // BUTTONS
        // DeleteAll
        deleteAllImageButton.setOnClickListener(v -> {
            // Rimuovere tutti gli elementi dal layout genitore
            historyLinearLayout.removeAllViews();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
        });
        //Home
        homeImageButton.setOnClickListener(view -> {
            // Creazione di un intent per avviare la nuova activity
            Intent intent = new Intent(History.this, MainActivity.class);
            startActivity(intent);
        });
        //History
        historyImageButton.setOnClickListener(view -> {
            // Ricrea l'attività corrente, aggiornando l'interfaccia utente
            recreate();
        });


        sharedPreferences = getSharedPreferences("historyNotification", Context.MODE_PRIVATE);

        Map<String, ?> allEntries = sharedPreferences.getAll();
        Map<String, String> sortedMap = new TreeMap<>(Collections.reverseOrder());
        sortedMap.putAll((Map<String, String>) allEntries);

        // Itera attraverso tutte le coppie chiave-valore
        for (Map.Entry<String, ?> entry : sortedMap.entrySet())
        {
            String key = entry.getKey();
            Object value = entry.getValue();

            historyLinearLayout = findViewById(R.id.historyLinearLayout);

            // Creazione della View
            View view = new View(this);
            view.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            view.setBackgroundColor(ContextCompat.getColor(this, R.color.gray_opac));

            // Creazione del LinearLayout con ImageView e TextView
            LinearLayout newLinearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(10, 10, 10, 10); // Aggiungi il margine di 10dp
            newLinearLayout.setLayoutParams(layoutParams);
            newLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            newLinearLayout.setPadding(10, 10, 10, 10);
            newLinearLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.custom_border));
            newLinearLayout.setId(View.generateViewId());


            // Creazione dell'ImageView
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            imageView.setImageResource(R.drawable.directions_run);

            // Creazione del TextView
            TextView textView = new TextView(this);
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

            textView.setTypeface(null, Typeface.BOLD);

            // Verifica se il valore è una stringa
            if (value instanceof String) {
                String stringValue = (String) value;

                if(stringValue!="")
                {
                    JSONObject jsonObject = null;
                    String ageValue= null;
                    String genderValue = null;
                    String glassValue = null;
                    String testToAdd = "";
                    String faceNum = null;

                    try {
                        jsonObject = new JSONObject(stringValue);
                        faceNum = jsonObject.getString("face_num");
                        JSONArray arrayFaces = jsonObject.getJSONArray("faces");
                        for(int x = 0;x<Integer.parseInt(faceNum);x++)
                        {
                            genderValue = arrayFaces.optJSONObject(x)
                                    .getJSONObject("attributes")
                                    .getJSONObject("gender")
                                    .getString("value");

                            ageValue = arrayFaces
                                    .optJSONObject(x)
                                    .getJSONObject("attributes")
                                    .getJSONObject("age")
                                    .getString("value");

                            glassValue = arrayFaces
                                    .optJSONObject(x)
                                    .getJSONObject("attributes")
                                    .getJSONObject("glass")
                                    .getString("value");
                            testToAdd=testToAdd+"\nGender: "+genderValue+"\tAge: " + ageValue+"\tGlass: " + glassValue;

                        }
                        if(Integer.parseInt(faceNum)==0)
                            textView.setPadding(16, 34, 0, 0);
                        if(Integer.parseInt(faceNum)==1)
                            textView.setPadding(16, 15, 0, 0);

                        textView.setText("Date: "+key+" (Detected faces: "+faceNum+")"+testToAdd);
                    } catch (Exception e) {
                        textView.setText("Date: "+key+" (Detected faces: "+faceNum+")");
                        textView.setPadding(16, 34, 0, 0);
                    }
                }else {
                    textView.setPadding(16, 34, 0, 0);
                    textView.setText("Date: "+key);
                }

                // Aggiunta dell'ImageView e del TextView al LinearLayout interno
                newLinearLayout.addView(imageView);
                newLinearLayout.addView(textView);
                // Aggiunta della View e del LinearLayout interno al LinearLayout principale
                historyLinearLayout.addView(view);
                historyLinearLayout.addView(newLinearLayout);


            }
        }
    }




    private void initViews()
    {
        deleteAllImageButton = findViewById(R.id.deleteAllImageButton);
        historyLinearLayout = findViewById(R.id.historyLinearLayout);
        homeImageButton = findViewById(R.id.homeImageButton);
        historyImageButton = findViewById(R.id.historyImageButton);
    }


}