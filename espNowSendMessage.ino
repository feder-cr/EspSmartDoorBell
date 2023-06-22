#include <esp_now.h>
#include <WiFi.h>

#define BUTTON_PIN 21
int lastState = LOW;  
int currentState;

uint8_t broadcastAddress[] = {0x34, 0x85, 0x18, 0xAD, 0xCF, 0x20}; //esp32s3
esp_now_peer_info_t peerInfo;
char myData[8] = "pressed";


void OnDataSent(const uint8_t *mac_addr, esp_now_send_status_t status) {
  Serial.print("\r\nLast Packet Send Status:\t");
  if(status == ESP_NOW_SEND_SUCCESS)
  {
    Serial.println("Delivery Success");
  }else{
    esp_now_send(broadcastAddress, (uint8_t *) "pressed", sizeof(myData));
    Serial.println("Delivery Fail");
  }
}
 
void setup() {
  Serial.begin(115200);
  pinMode(BUTTON_PIN, INPUT_PULLUP);
  WiFi.mode(WIFI_STA);
  if (esp_now_init() != ESP_OK) {
    Serial.println("Error initializing ESP-NOW");
    return;
  }
  esp_now_register_send_cb(OnDataSent);
  // Register peer
  memcpy(peerInfo.peer_addr, broadcastAddress, 6);
  peerInfo.channel = 0;  
  peerInfo.encrypt = false;
  // Add peer        
  if (esp_now_add_peer(&peerInfo) != ESP_OK){
    Serial.println("Failed to add peer");
    return;
  }
}
 
void loop() 
{
  currentState = digitalRead(BUTTON_PIN);
  if (lastState == HIGH && currentState == LOW){
    esp_now_send(broadcastAddress, (uint8_t *) "pressed", sizeof(myData));
    sleep(1);
  }
  lastState = currentState;
}
