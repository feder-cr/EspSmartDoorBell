package com.maiot.esempiomqtt;

public class Constants {
    public static final String BROKER = "a3scqay97a4xdq-ats.iot.eu-central-1.amazonaws.com";
    public static final int PORT = 8883;


    public static final String TOPIC = "esp32/pub";
    public static final int QOS = 0;

    public static final String MCARCRT = "-----BEGIN CERTIFICATE-----\n" +
            "MIIDQTCCAimgAwIBAgITBmyfz5m/jAo54vB4ikPmljZbyjANBgkqhkiG9w0BAQsF\n" +
            "ADA5MQswCQYDVQQGEwJVUzEPMA0GA1UEChMGQW1hem9uMRkwFwYDVQQDExBBbWF6\n" +
            "b24gUm9vdCBDQSAxMB4XDTE1MDUyNjAwMDAwMFoXDTM4MDExNzAwMDAwMFowOTEL\n" +
            "MAkGA1UEBhMCVVMxDzANBgNVBAoTBkFtYXpvbjEZMBcGA1UEAxMQQW1hem9uIFJv\n" +
            "b3QgQ0EgMTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBALJ4gHHKeNXj\n" +
            "ca9HgFB0fW7Y14h29Jlo91ghYPl0hAEvrAIthtOgQ3pOsqTQNroBvo3bSMgHFzZM\n" +
            "9O6II8c+6zf1tRn4SWiw3te5djgdYZ6k/oI2peVKVuRF4fn9tBb6dNqcmzU5L/qw\n" +
            "IFAGbHrQgLKm+a/sRxmPUDgH3KKHOVj4utWp+UhnMJbulHheb4mjUcAwhmahRWa6\n" +
            "VOujw5H5SNz/0egwLX0tdHA114gk957EWW67c4cX8jJGKLhD+rcdqsq08p8kDi1L\n" +
            "93FcXmn/6pUCyziKrlA4b9v7LWIbxcceVOF34GfID5yHI9Y/QCB/IIDEgEw+OyQm\n" +
            "jgSubJrIqg0CAwEAAaNCMEAwDwYDVR0TAQH/BAUwAwEB/zAOBgNVHQ8BAf8EBAMC\n" +
            "AYYwHQYDVR0OBBYEFIQYzIU07LwMlJQuCFmcx7IQTgoIMA0GCSqGSIb3DQEBCwUA\n" +
            "A4IBAQCY8jdaQZChGsV2USggNiMOruYou6r4lK5IpDB/G/wkjUu0yKGX9rbxenDI\n" +
            "U5PMCCjjmCXPI6T53iHTfIUJrU6adTrCC2qJeHZERxhlbI1Bjjt/msv0tadQ1wUs\n" +
            "N+gDS63pYaACbvXy8MWy7Vu33PqUXHeeE6V/Uq2V8viTO96LXFvKWlJbYK8U90vv\n" +
            "o/ufQJVtMVT8QtPHRh8jrdkPSHCa2XV4cdFyQzR1bldZwgJcJmApzyMZFo6IQ6XU\n" +
            "5MsI+yMRQ+hDKXJioaldXgjUkK642M4UwtBV8ob2xJNDd2ZhwLnoQdeXeGADbkpy\n" +
            "rqXRfboQnoZsG4q5WTP468SQvvG5\n" +
            "-----END CERTIFICATE-----";
    public static final String MCRT ="-----BEGIN CERTIFICATE-----\n" +
            "MIIDWTCCAkGgAwIBAgIUWdc9So94Fh2FbQ+YUULEt7HoDAwwDQYJKoZIhvcNAQEL\n" +
            "BQAwTTFLMEkGA1UECwxCQW1hem9uIFdlYiBTZXJ2aWNlcyBPPUFtYXpvbi5jb20g\n" +
            "SW5jLiBMPVNlYXR0bGUgU1Q9V2FzaGluZ3RvbiBDPVVTMB4XDTIzMDYxODE2MzE0\n" +
            "OFoXDTQ5MTIzMTIzNTk1OVowHjEcMBoGA1UEAwwTQVdTIElvVCBDZXJ0aWZpY2F0\n" +
            "ZTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAO7TvR+fsVauHNPYixIB\n" +
            "qYx1X4PHL00V6nKBChAe1VH3H6a2RuNOWrHhm86H8VG53+YOfbDP0FUMewMbr8Bo\n" +
            "rUHlNEEQTMP3bTHDtxQaUx4H/VK54SyaND6sNk7n4ef5ozTfrD1plzskbjpobWMA\n" +
            "YrgFcR2WObIqQfVY3ZGYGSph3ZP8RU1gkMnINNnNho7aX/OMTmRBkH1mWiVO7szI\n" +
            "ftlJ+9XbpndTadELRMpgCoCbtc4jmQFpWOYy9vEjWzHPDz/eKsBkio1qlI6yujn0\n" +
            "JGB8iT90aKm6XmPYTjyq+QOLij+eF5duNcAWr02PndV7/+79ykkYeJ+Ovadmyx0Q\n" +
            "e/MCAwEAAaNgMF4wHwYDVR0jBBgwFoAUGpj1Jmk3MthsHduOLGvGwJHeGiIwHQYD\n" +
            "VR0OBBYEFJgNl7XZuyusBlc/E7uq34v3NJR0MAwGA1UdEwEB/wQCMAAwDgYDVR0P\n" +
            "AQH/BAQDAgeAMA0GCSqGSIb3DQEBCwUAA4IBAQCk+9ufGbnWz4EYnWDaoQB26frM\n" +
            "H0xjOlv9cmsLKjZAFfQ7vsra9ih5TuUkg1u6x4goIW87zrdN4ck1w+UcVRQgajV0\n" +
            "9cMgsdJ8/WkqTwc8CZdkbFgFEqMWCKVLFUefA8encLgC60V6v6sD52GNI3Ps2xk+\n" +
            "SOx4sSMzU5ubd1H2B0vW7ISfH76waWFLt1yJSIT7+N1PgQpH9/OuXGTys6/Cu7uY\n" +
            "eKIqRUn8ERg9B1XRZkQD4aWYhxNTlFoimcjme1kSUADThSPgNfceiNCRvrbrfnFI\n" +
            "HDM7dnZbnn3nLk5RKs1/3Tu7lRlq4csth/2PjqR7MXgYlzHi/GYoBNPe4/M4\n" +
            "-----END CERTIFICATE-----\n";
    public static final String MKEY = "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEogIBAAKCAQEA7tO9H5+xVq4c09iLEgGpjHVfg8cvTRXqcoEKEB7VUfcfprZG\n" +
            "405aseGbzofxUbnf5g59sM/QVQx7AxuvwGitQeU0QRBMw/dtMcO3FBpTHgf9Urnh\n" +
            "LJo0Pqw2Tufh5/mjNN+sPWmXOyRuOmhtYwBiuAVxHZY5sipB9VjdkZgZKmHdk/xF\n" +
            "TWCQycg02c2Gjtpf84xOZEGQfWZaJU7uzMh+2Un71dumd1Np0QtEymAKgJu1ziOZ\n" +
            "AWlY5jL28SNbMc8PP94qwGSKjWqUjrK6OfQkYHyJP3RoqbpeY9hOPKr5A4uKP54X\n" +
            "l241wBavTY+d1Xv/7v3KSRh4n469p2bLHRB78wIDAQABAoIBAHyJEO9ymEi41fNp\n" +
            "0Wr8TusdMUdIEFRD/1LiEttBGL96+5g1KFXTP9vN5y8VGDMjl6shF02hr+MJdQOz\n" +
            "5juQ2feoi0z6MO1nEhuHEOp8p6bbR69hmhdl+aRMrK0MoXITbJVfpTMI4oFGh+wR\n" +
            "FCkxxEhtHgTtlvxeqLJFOpR3GXhNOhmw+Jfb3jfblF1o7s8qg3s779amn8FrHXfj\n" +
            "ZsGQtPBSasi6EV7D+k77bnvK1gm7JFirImSDPB/751UKeMihn79CP4MRtvsuvKM1\n" +
            "3LUGSzbP5+TlTptrSncMy/0IYuOqfDBPMPAYsycAryUOp2hSWfaIPtaATwlY89Tw\n" +
            "WhSWQqECgYEA+VHbGS7W8150Epti3I2SL6lmu1exlXedJLlIdfO+OId3D+iCJwfx\n" +
            "b8oircDH/JLB2pz6Mk/z0NWkROklN0YYQ5WYm/WUWqJvy3/gnMumT11N1XKMKwPF\n" +
            "O14ljNrXFKddp/X4XWMn0SamHL59WTTg0AaF46p4hEnsEsaGgraAdssCgYEA9Tnp\n" +
            "TUDrCxNetaF74H6NhWuL3U+p/IzrHyLQ5BzeTF1dcDIr1Ntorj115AGwQHmBedbl\n" +
            "7bcJBqebc0s77ZJ+Bn5QagBOKB7VwDgElyd51TbB8Q5x8gr7o9I9d6QqdaBpokNU\n" +
            "XWzSAJStOBk8/mI8wKTeCfT5LTWWs/GFGAQoQnkCgYAvDROcHQrTwu+E025E0TLV\n" +
            "a9LHaDVLT9eIJq9WEznC0U3W4rotQ4J85ljFEaysLcYHy7gr5orMzoTBvH4ZgFDz\n" +
            "8Ddst1vX/u+6J9frv0MTlzgpIATz2TJPkeTkQCh27V7vY6wLc7r/7ZtLsWdWfnQu\n" +
            "zgxWesVjsc+re2vHH0n/IQKBgEnd6N3BaUuLySKTAvqLhFulGptObNjLJePaG+wq\n" +
            "mfeMxCGQq01RzgV2M/W7YHj7YLnvtpDVap2bjSK8jc+xKc8Q624Sq3hAWYiuFwvk\n" +
            "8zj7Zo4TGNWc41HkeWl5hOSrrkYXW+lMZzGPl2HnkEzcEIrC3rNHHGu20a9gWAP0\n" +
            "9J+ZAoGARuebP7vcUdH54xmi6+Ka+E91w+FVfeNzH0Q7PKLKkW9DcP8zVcl9/+JG\n" +
            "UJ+U8GPV5h0YY24OLyrXVObFI4wQjJm+5F/ZiEjwIe6PNOpzIAmyBfWiShRWd7t3\n" +
            "tiyfu+AaeAOl0XJNKQAs+x7tSFt8ukRlGQBAGBaqrtcQOuyIiRQ=\n" +
            "-----END RSA PRIVATE KEY-----\n";
}
