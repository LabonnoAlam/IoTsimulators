# IoTsimulators
IoT-Home-Automation
│
├── src
│   └── main
│       ├── java
│       │   ├── HomeAutomationApp.java
│       │   ├── MqttClientHandler.java
│       │   ├── EventProcessor.java
│       │   ├── FileHandler.java
│       │   └── HomeAutomationGUI.java
│       └── resources
│           └── system_logs.txt (auto-created when logs are written)
│
└── pom.xml (for Maven dependencies)
import org.eclipse.paho.client.mqttv3.MqttException;

public class HomeAutomationApp {
    public static MqttClientHandler mqttClient;

    public static void main(String[] args) {
        try {
            // Initialize MQTT client
            mqttClient = new MqttClientHandler("tcp://broker.hivemq.com:1883", MqttClient.generateClientId());

            // Subscribe to the necessary topics for sensors
            mqttClient.subscribeToTopic("home/room1/temperature");
            mqttClient.subscribeToTopic("home/room1/humidity");
            mqttClient.subscribeToTopic("home/room1/motion");

            // Launch the GUI
            HomeAutomationGUI.createGUI();

        } catch (Exception e) {
            System.err.println("Error initializing system: " + e.getMessage());
        }
    }
}
import org.eclipse.paho.client.mqttv3.*;

public class MqttClientHandler {
    private MqttClient client;

    public MqttClientHandler(String broker, String clientId) throws MqttException {
        client = new MqttClient(broker, clientId);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        client.connect(options);
    }

    public void publishMessage(String topic, String payload) throws MqttException {
        MqttMessage message = new MqttMessage(payload.getBytes());
        message.setQos(2);
        client.publish(topic, message);
    }

    public void subscribeToTopic(String topic) throws MqttException {
        client.subscribe(topic);
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                System.out.println("Connection lost: " + cause.getMessage());
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                System.out.println("Message arrived from " + topic + ": " + new String(message.getPayload()));
                EventProcessor.processEvent(topic, new String(message.getPayload()));
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                System.out.println("Delivery complete");
            }
        });
    }

    public void disconnect() throws MqttException {
        client.disconnect();
    }
}
public class EventProcessor {
    public static void processEvent(String topic, String message) throws Exception {
        if (topic.equals("home/room1/temperature")) {
            double temperature = Double.parseDouble(message);
            if (temperature > 30) {
                System.out.println("High temperature detected: Turning on fan...");
                HomeAutomationApp.mqttClient.publishMessage("home/room1/fan", "ON");
                FileHandler.saveLog("Fan turned ON due to high temperature");
            }
        } else if (topic.equals("home/room1/humidity")) {
            double humidity = Double.parseDouble(message);
            if (humidity > 60) {
                System.out.println("High humidity detected: Turning on fan...");
                HomeAutomationApp.mqttClient.publishMessage("home/room1/fan", "ON");
                FileHandler.saveLog("Fan turned ON due to high humidity");
            }
        } else if (topic.equals("home/room1/motion")) {
            boolean motionDetected = Boolean.parseBoolean(message);
            if (motionDetected) {
                System.out.println("Motion detected: Turning on light...");
                HomeAutomationApp.mqttClient.publishMessage("home/room1/light", "ON");
                FileHandler.saveLog("Light turned ON due to motion detection");
            }
        }
    }
}
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileHandler {

    private static final String FILE_PATH = "system_logs.txt";

    public static void saveLog(String log) throws IOException {
        FileWriter writer = null;
        try {
            writer = new FileWriter(FILE_PATH, true);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String timeStamp = LocalDateTime.now().format(formatter);
            writer.write(timeStamp + " - " + log + "\n");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
            throw e;
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.eclipse.paho.client.mqttv3.MqttException;

public class HomeAutomationGUI {
    public static void createGUI() {
        JFrame frame = new JFrame("Home Automation Control");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();

        JButton lightOnButton = new JButton("Turn On Light");
        JButton lightOffButton = new JButton("Turn Off Light");
        JButton fanOnButton = new JButton("Turn On Fan");
        JButton fanOffButton = new JButton("Turn Off Fan");

        panel.add(lightOnButton);
        panel.add(lightOffButton);
        panel.add(fanOnButton);
        panel.add(fanOffButton);

        frame.add(panel);
        frame.setVisible(true);

        // Add action listeners for buttons
        lightOnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    HomeAutomationApp.mqttClient.publishMessage("home/room1/light", "ON");
                    FileHandler.saveLog("Light turned ON manually");
                } catch (MqttException ex) {
                    System.err.println("Error turning light on: " + ex.getMessage());
                } catch (IOException ioException) {
                    System.err.println("Error logging event: " + ioException.getMessage());
                }
            }
        });

        lightOffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    HomeAutomationApp.mqttClient.publishMessage("home/room1/light", "OFF");
                    FileHandler.saveLog("Light turned OFF manually");
                } catch (MqttException ex) {
                    System.err.println("Error turning light off: " + ex.getMessage());
                } catch (IOException ioException) {
                    System.err.println("Error logging event: " + ioException.getMessage());
                }
            }
        });

        fanOnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    HomeAutomationApp.mqttClient.publishMessage("home/room1/fan", "ON");
                    FileHandler.saveLog("Fan turned ON manually");
                } catch (MqttException ex) {
                    System.err.println("Error turning fan on: " + ex.getMessage());
                } catch (IOException ioException) {
                    System.err.println("Error logging event: " + ioException.getMessage());
                }
            }
        });

        fanOffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    HomeAutomationApp.mqttClient.publishMessage("home/room1/fan", "OFF");
                    FileHandler.saveLog("Fan turned OFF manually");
                } catch (MqttException ex) {
                    System.err.println("Error turning fan off: " + ex.getMessage());
                } catch (IOException ioException) {
                    System.err.println("Error logging event: " + ioException.getMessage());
                }
            }
        });
    }
}
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://m
