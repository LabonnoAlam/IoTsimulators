package com.iotmanagementsystem;

import java.io.*;
import java.util.HashMap;

public class SystemSecurity {

    private HashMap<String, String> userData = new HashMap<>(); // Simulated user data storage
    private String currentStatus;
    private final String filePath = "security_data.txt";

    // Constructor to load security data from file when system starts
    public SystemSecurity() {
        loadSecurityData();
    }

    // Method to store user data and current status
    public void dataStorage(String userId, String data, String status) {
        // Store user data and status in memory
        userData.put(userId, data);
        currentStatus = status;
        saveSecurityData(); // Save data to file
        System.out.println("Data and status stored for user: " + userId);
    }

    // Simulate transferring data with blockchain for privacy
    public void transferDataWithBlockchain(String userId) {
        // Encrypt data to simulate blockchain privacy (basic illustration)
        String encryptedData = encryptData(userData.get(userId));
        String encryptedStatus = encryptData(currentStatus);

        // Simulated blockchain data transfer
        System.out.println("Transferring encrypted data and status via blockchain for user: " + userId);
        System.out.println("Encrypted Data: " + encryptedData);
        System.out.println("Encrypted Status: " + encryptedStatus);
    }

    // Basic encryption simulation (for illustration purposes)
    private String encryptData(String data) {
        StringBuilder encrypted = new StringBuilder();
        for (char c : data.toCharArray()) {
            encrypted.append((char) (c + 3)); // Simple Caesar cipher for encryption
        }
        return encrypted.toString();
    }

    // Save security data to file
    private void saveSecurityData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String userId : userData.keySet()) {
                writer.write(userId + "=" + userData.get(userId) + "," + currentStatus);
                writer.newLine();
            }
            System.out.println("Security data saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving security data: " + e.getMessage());
        }
    }

    // Load security data from file
    private void loadSecurityData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                String userId = parts[0];
                String[] dataParts = parts[1].split(",");
                String data = dataParts[0];
                String status = dataParts[1];
                userData.put(userId, data);
                currentStatus = status;
            }
            System.out.println("Security data loaded from file.");
        } catch (IOException e) {
            System.out.println("Error loading security data: " + e.getMessage());
        }
    }

    // Optionally, you can add a method to display current user data and status
    public void displayUserData() {
        System.out.println("Current User Data:");
        for (String userId : userData.keySet()) {
            System.out.println("User ID: " + userId + ", Data: " + userData.get(userId) + ", Status: " + currentStatus);
        }
    }
}
