package com.maiot.esempiomqtt;

public interface ISubscriber {
    void onMessageReceived(String topic, byte[] message);
}
