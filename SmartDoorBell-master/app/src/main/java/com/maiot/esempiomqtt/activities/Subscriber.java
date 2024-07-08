package com.maiot.esempiomqtt.activities;
import androidx.appcompat.app.AppCompatActivity;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import com.maiot.esempiomqtt.Constants;
import com.maiot.esempiomqtt.ISubscriber;
import com.maiot.esempiomqtt.MainActivity;
import com.maiot.esempiomqtt.R;
import MQTT.MQTTEntity;
import MQTT.MQTTSubscriber;

public class Subscriber extends AppCompatActivity  implements ISubscriber {
    private final String TAG = "Subscriber";
    private ImageView video = null;
    private ImageButton homeImageButton = null;
    private ImageButton historyImageButton= null;
    private ImageButton endLiveImageButton = null;
    private MQTTSubscriber mqttSubscriber = null;
    private Boolean isLive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        initViews();
        subscribe();
        isLive = true;

        // BUTTONS
        //Home
        homeImageButton.setOnClickListener(view -> {
            // Creazione di un intent per avviare la nuova activity
            Intent intent = new Intent(Subscriber.this, MainActivity.class);
            startActivity(intent);
        });
        //EndLive
        endLiveImageButton.setOnClickListener(v -> {
            if(isLive)
                endLive();
        });
        //History
        historyImageButton.setOnClickListener(view -> {
            Intent intent = new Intent(Subscriber.this, History.class);
            startActivity(intent);
        });
    }

    void subscribe()
    {
        MQTTEntity mqttEntity = new MQTTEntity(Constants.BROKER, Constants.PORT);
        mqttSubscriber = new MQTTSubscriber(mqttEntity, this);
        mqttSubscriber.connectAndSubscribe();
    }

    private void endLive()
    {
        NotificationManager notificationManager = getSystemService(NotificationManager.class);

        // Cancella la notifica utilizzando l'ID
        notificationManager.cancel(0);
        isLive = false;
        runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Chiamata terminata", Toast.LENGTH_SHORT).show());
        runOnUiThread(() -> updateTextViews(BitmapFactory.decodeResource(getResources(), R.drawable.end_call)));
    }
    
    public void onMessageReceived(String topic, byte[] payload)
    {
        if(isLive) {
            if (new String(payload).equals("1")) {
                endLive();
            } else {
                Bitmap picture = BitmapFactory.decodeByteArray(payload, 0, payload.length);
                runOnUiThread(() -> updateTextViews(picture));
            }
        }
    }

    private void updateTextViews(Bitmap picture) {
        video.setImageBitmap(picture);
    }

    private void initViews() {
        video = findViewById(R.id.videoImageView);
        homeImageButton = findViewById(R.id.homeImageButton);
        historyImageButton = findViewById(R.id.historyImageButton);
        endLiveImageButton = findViewById(R.id.endLiveImageButton);
    }
}
