package com.maiot.esempiomqtt;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import com.google.firebase.messaging.FirebaseMessaging;
import com.maiot.esempiomqtt.activities.History;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    private ImageButton historyButton = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        FirebaseMessaging.getInstance().subscribeToTopic("nome_topic");
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, History.class);
                startActivity(intent);
            }
        });



    }

    private void initViews(){
        historyButton = findViewById(R.id.historyImageButton);
    }
}