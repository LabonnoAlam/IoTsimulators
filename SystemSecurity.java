package com.iotmanagementsystem;

import java.util.HashMap;

public class SecuritySystem {

    private HashMap<String, String> userData = new HashMap<>(); // Simulated user data storage
    private String currentStatus;

    // Method to store user data and current status in the cloud
    public void dataStorage(String userId, String data, String status) {
        // Simulate storing data in cloud storage
        userData.put(userId, data);
        currentStatus = status;
        System.out.println("Data and status stored in cloud for user: " + userId);
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

    // Example of retrieving stored data (optional method)
    public String getUserData(String userId) {
        return userData.get(userId);
    }
}

