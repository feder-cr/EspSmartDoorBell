#include "secrets.h"
#include <WiFiClientSecure.h>
#include <MQTTClient.h>
#include "WiFi.h"
#include "esp_camera.h"
#include <esp_now.h>
#include <esp_wifi.h>

int counter = 0;
#define PWDN_GPIO_NUM     -1
#define RESET_GPIO_NUM    -1
#define XCLK_GPIO_NUM     10
#define SIOD_GPIO_NUM     40
#define SIOC_GPIO_NUM     39

#define Y9_GPIO_NUM       48
#define Y8_GPIO_NUM       11
#define Y7_GPIO_NUM       12
#define Y6_GPIO_NUM       14
#define Y5_GPIO_NUM       16
#define Y4_GPIO_NUM       18
#define Y3_GPIO_NUM       17
#define Y2_GPIO_NUM       15
#define VSYNC_GPIO_NUM    38
#define HREF_GPIO_NUM     47
#define PCLK_GPIO_NUM     13

#define LED_GPIO_NUM      21

#define ESP32CAM_PUBLISH_TOPIC   "esp32/pub"

const int bufferSize = 1024 * 23; // 23552 bytes
//const byte WIFI_BSSID[] = {0x20, 0x66, 0xCF, 0xEA, 0x75, 0x88};

WiFiClientSecure net = WiFiClientSecure();
MQTTClient client = MQTTClient(bufferSize);
bool receive = false; 


int x = 0;






void connectWIFI()
{

  //WiFi.begin(WIFI_SSID, WIFI_PASSWORD, 0, WIFI_BSSID);
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  while (WiFi.status() != WL_CONNECTED){
    delay(200);
    Serial.print(".");
  }

}
void connectAWS()
{
  net.setCACert(AWS_CERT_CA);
  net.setCertificate(AWS_CERT_CRT);
  net.setPrivateKey(AWS_CERT_PRIVATE);

  client.begin(AWS_IOT_ENDPOINT, 8883, net);
  client.setCleanSession(true);

  while (!client.connect(THINGNAME)) {delay(100);}

  if(!client.connected()){
    vTaskDelay(10);
    ESP.restart();
    return;
  }
  vTaskDelay(10);
  vTaskDelay(10);
  vTaskDelay(10);

}

void cameraInit(){
  camera_config_t config;
  config.ledc_channel = LEDC_CHANNEL_0;
  config.ledc_timer = LEDC_TIMER_0;
  config.pin_d0 = Y2_GPIO_NUM;
  config.pin_d1 = Y3_GPIO_NUM;
  config.pin_d2 = Y4_GPIO_NUM;
  config.pin_d3 = Y5_GPIO_NUM;
  config.pin_d4 = Y6_GPIO_NUM;
  config.pin_d5 = Y7_GPIO_NUM;
  config.pin_d6 = Y8_GPIO_NUM;
  config.pin_d7 = Y9_GPIO_NUM;
  config.pin_xclk = XCLK_GPIO_NUM;
  config.pin_pclk = PCLK_GPIO_NUM;
  config.pin_vsync = VSYNC_GPIO_NUM;
  config.pin_href = HREF_GPIO_NUM;
  config.pin_sscb_sda = SIOD_GPIO_NUM;
  config.pin_sscb_scl = SIOC_GPIO_NUM;
  config.pin_pwdn = PWDN_GPIO_NUM;
  config.pin_reset = RESET_GPIO_NUM;
  config.xclk_freq_hz = 24000000;
  config.frame_size = FRAMESIZE_VGA;
  config.pixel_format = PIXFORMAT_JPEG; // for streaming
  //config.pixel_format = PIXFORMAT_RGB565; // for face detection/recognition
  config.grab_mode = CAMERA_GRAB_WHEN_EMPTY;
  config.fb_location = CAMERA_FB_IN_PSRAM;
  config.jpeg_quality = 12;
  config.fb_count = 1;

  // camera init
  esp_err_t err = esp_camera_init(&config);
  if (err != ESP_OK) {
    //vTaskDelay(100);
    ESP.restart();
    return;
  }
}

void grabImage()
{
  camera_fb_t * fb = esp_camera_fb_get();
  if(fb != NULL ){
    bool result = client.publish(ESP32CAM_PUBLISH_TOPIC, (const char*)fb->buf, fb->len);
    if(!result){
      ESP.restart();
    }
  }
  esp_camera_fb_return(fb);
}

void setup() 
{
  WiFi.mode(WIFI_STA);
  esp_sleep_enable_timer_wakeup(2 * 1000000);
  if (esp_now_init() != ESP_OK) {
    vTaskDelay(100);

    return;
  }
  esp_now_register_recv_cb(OnDataRecv);
}

void loop() 
{
  delay(100);
    if(receive == true)
    {
      //setCpuFrequencyMhz(240);
      connectWIFI();
      sendFCMRequest();
      cameraInit();
      connectAWS();
      unsigned long startTime = millis();
      while(true)
      {
        client.loop();
        grabImage();
        if (millis() - startTime >= 55000) 
        { 
          for(int i=0;i<10;i++)
          {
            client.publish(ESP32CAM_PUBLISH_TOPIC, (const char*)"1");
          }
          break;
        }
      }
    }
  esp_deep_sleep_start();
}

void sendFCMRequest() 
{
  WiFiClientSecure client;
  client.setCACert(test_root_ca);
  while(!client.connect(server, 443)){delay(100);}
  String requestData = "{\"to\": \"/topics/nome_topic\", \"data\": {\"default\": \"0\"}}";
  client.println("POST /fcm/send HTTP/1.1");
  client.println("Host: " + String(server));
  client.println("Authorization: key=AAAAhYD-nLY:APA91bGGvvcUbFcaR_djh5XLUg6uiqVokb48y_OsLSzI504iMIrfTBjYo1cbYfgKgGAUiqzC-e_RFnt-EwULbbgETqGGbzf1RVXEX8o5e8K8D8YX1BnncEhU2hwUgUrUYgIRXXbS779S");
  client.println("Content-Type: application/json");
  client.print("Content-Length: ");
  client.println(requestData.length());
  client.println();
  client.println(requestData);
}


void OnDataRecv(const uint8_t * mac, const uint8_t *incomingData, int len) 
{
  receive = true;
}
