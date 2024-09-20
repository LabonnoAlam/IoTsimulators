package com.iotmanagementsystem;

// Class to store the current status of various IoT components
public class Status {
    // Boolean flags for different system statuses
    public static boolean solarPanel = false;
    public static boolean batteryStatus = false;
    public static boolean day = false;
    public static boolean voltageInRange = false;
    public static boolean light = false;
    public static boolean shading = false;
    public static boolean electricalLine = false;

    // Double values for temperature and appliances
    public static double temperature = 0.0;
    public static double ac = 0.0;
    public static double heater = 0.0;

    // Integer to store the count of people present
    public static int personPresent = 0;
}

