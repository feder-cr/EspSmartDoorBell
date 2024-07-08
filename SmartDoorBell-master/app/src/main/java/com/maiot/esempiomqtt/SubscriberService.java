package com.maiot.esempiomqtt;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.maiot.esempiomqtt.activities.Subscriber;
import MQTT.MQTTEntity;
import MQTT.MQTTSubscriber;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class SubscriberService extends FirebaseMessagingService implements ISubscriber
{
    private static final String TAG = "MyFirebaseMsgService";
    int count = 0;
    private MQTTSubscriber mqttSubscriber = null;
    private static final String API_KEY = "RMeJcR7hpi9yhfzyuepfZtE_pZSS4sDc";
    private static final String API_SECRET = "NdI-SyyhndXGrWVE3RxZ5FN-LZeWXore";
    private static final String API_URL = "https://api-us.faceplusplus.com/facepp/v3/detect";
    private String returnAttributes = "gender,age,eyestatus";
    private String dateTimeString;
    NotificationManager notificationManager = null;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Map<String, String> x = remoteMessage.getData();

        if (x.get("default").equals("0"))
        {
            count=0;
            if(mqttSubscriber==null)
                subscribe();
            Date currentDateTime = new Date();
            SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String timeString = sdfTime.format(currentDateTime);
            String dateString = sdfDate.format(currentDateTime);
            dateTimeString = timeString + " " + dateString;

            SharedPreferences sharedPreferences = getSharedPreferences("historyNotification", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(dateTimeString, "");
            editor.apply();

            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            notificationManager = getSystemService(NotificationManager.class);
            String CHANNEL_ID = "my_channel_id";
            String channel_name = "channel_name";
            String channel_description = "channel_description";
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                        channel_name,
                        NotificationManager.IMPORTANCE_DEFAULT);
                channel.setDescription(channel_description);
                notificationManager.createNotificationChannel(channel);
            }

            Intent intent = new Intent(this, Subscriber.class);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(android.R.drawable.star_on)
                    .setContentTitle("Quancuno ha suonato il tuo campanello!!")
                    .setContentText(" Clicca questa notifica per scoprire chi...")
                    .setContentIntent(PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            notificationManager.notify(0, builder.build());
        }
    }
    void subscribe()
    {
        MQTTEntity mqttEntity = new MQTTEntity(Constants.BROKER, Constants.PORT);
        mqttSubscriber = new MQTTSubscriber(mqttEntity, this);
        mqttSubscriber.connectAndSubscribe();
    }

    @Override
    public void onMessageReceived(String topic, byte[] payload)
    {
        if(count==30) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
            {
                String base64String = Base64.getEncoder().encodeToString(payload);

                SharedPreferences sharedPreferences = getSharedPreferences("historyNotification", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(dateTimeString, FacePlusPlus(base64String));
                editor.apply();

            }
        }

        if(new String(payload).equals("1"))
        {
            if(notificationManager!=null)
                notificationManager.cancel(0);
        }
        count++;
    }

    public String FacePlusPlus(String imageBase64) {
        String responseData = null;
        try {
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Parametri della richiesta
            String parameters = "api_key=" + URLEncoder.encode(API_KEY, "UTF-8") +
                    "&api_secret=" + URLEncoder.encode(API_SECRET, "UTF-8") +
                    "&image_base64=" + URLEncoder.encode(imageBase64, "UTF-8") +
                    "&return_attributes=" + URLEncoder.encode(returnAttributes, "UTF-8");

            // Invio dei parametri
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            writer.write(parameters);
            writer.flush();
            writer.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                responseData = response.toString();
                // Gestisci la risposta qui
                Log.i(TAG, "Faccia: " + responseData);

            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseData;
    }
}
