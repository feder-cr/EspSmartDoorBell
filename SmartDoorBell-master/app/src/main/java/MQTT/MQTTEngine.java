package MQTT;

import com.maiot.esempiomqtt.Constants;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class MQTTEngine {

    private final String TAG = "MQTTEngine";

    private MqttClient mqttClient = null;
    private MqttConnectOptions mqttOptions = null;
    InputStream mCaCrtFile;
    InputStream mCrtFile;
    InputStream mKeyFile;
    public MQTTEngine(MQTTEntity mqttEntity) {
        try {
        initMqttOptions();
        initMqttClient(mqttEntity.getBroker(), mqttEntity.getPort());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void connectToMqttBroker () {
        try {
            mqttClient.connect(mqttOptions);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    public void disconnectFromMqttBroker (){
        try {
            mqttClient.disconnect();
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    public void subscribeToTopic(String topic, int qos) {
        try {
            mqttClient.subscribe(topic, qos);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    public void setMessageReceivedCallback(MqttCallback mqttCallback) {
        mqttClient.setCallback(mqttCallback);
    }



//todo
    private void initMqttOptions() throws Exception {
        mqttOptions = new MqttConnectOptions();
        mCaCrtFile = new ByteArrayInputStream(Constants.MCARCRT.getBytes());
        mCrtFile = new ByteArrayInputStream(Constants.MCRT.getBytes());
        mKeyFile = new ByteArrayInputStream(Constants.MKEY.getBytes());
        mqttOptions.setSocketFactory(getSocketFactory(mCaCrtFile, mCrtFile, mKeyFile, ""));
    }

    private void initMqttClient(String broker, int port) throws MqttException {
        mqttClient = new MqttClient(
                "ssl://" + broker + ":" + port,  // Broker URI, protocol://broker:port
                "AndroidApp",
                new MemoryPersistence()
        );
    }
    public static SSLSocketFactory getSocketFactory(InputStream caCrtFile, InputStream crtFile, InputStream keyFile,
                                                    String password) throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        // load CA certificate
        X509Certificate caCert = null;

        BufferedInputStream bis = new BufferedInputStream(caCrtFile);
        CertificateFactory cf = CertificateFactory.getInstance("X.509");

        while (bis.available() > 0) {
            caCert = (X509Certificate) cf.generateCertificate(bis);
        }
        // load client certificate
        bis = new BufferedInputStream(crtFile);
        X509Certificate cert = null;
        while (bis.available() > 0) {
            cert = (X509Certificate) cf.generateCertificate(bis);
        }
        // load client private cert
        PEMParser pemParser = new PEMParser(new InputStreamReader(keyFile));
        Object object = pemParser.readObject();
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
        KeyPair key = converter.getKeyPair((PEMKeyPair) object);

        KeyStore caKs = KeyStore.getInstance(KeyStore.getDefaultType());
        caKs.load(null, null);
        caKs.setCertificateEntry("cert-certificate", caCert);
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(caKs);

        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(null, null);
        ks.setCertificateEntry("certificate", cert);
        ks.setKeyEntry("private-cert", key.getPrivate(), password.toCharArray(),
                new java.security.cert.Certificate[]{cert});
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(ks, password.toCharArray());

        SSLContext context = SSLContext.getInstance("TLSv1.2");
        context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

        return context.getSocketFactory();
    }
}
