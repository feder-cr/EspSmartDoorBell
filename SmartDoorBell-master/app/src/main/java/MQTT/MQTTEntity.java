package MQTT;

public class MQTTEntity {

    private final String broker;
    private final int port;

    public MQTTEntity(String broker, int port) {
        this.broker = broker;
        this.port = port;
    }

    public String getBroker() {
        return broker;
    }


    public int getPort() {
        return port;
    }

}
