package com.iotmanagementsystem;

public class Control {

    // Turn light on or off
    public void light(String s) {
        Status.Light = s.equals("ON");
    }

    // Set day or night mode
    public void day(String d) {
        Status.Day = d.equals("YES");
        Status.Shading = !Status.Day;
        if (Status.Day) {
            Status.Light = false;
        } else if (Status.PersonPresent > 0) {
            Status.Light = true;
        }
    }

    // Turn shading on or off
    public void shading(String s) {
        Status.Shading = s.equals("ON");
    }

    // Set AC temperature
    public void ac(double t) {
        Status.AC = t;
        Status.Heater = 0.0;
        Status.Temperature = t;
    }

    // Set heater temperature
    public void heater(double t) {
        Status.Heater = t;
        Status.AC = 0.0;
        Status.Temperature = t;
    }

    // Update current temperature
    public void temperature(double t) {
        Status.Temperature = t;
        Status.AC = t;
        Status.Heater = 0.0;
    }

    // Update number of people present
    public void person(int p) {
        Status.PersonPresent = p;
        if (p == 0) {
            Status.AC = 0.0;
            Status.Heater = 0.0;
        } else if (!Status.Day) {
            Status.Light = true;
        }
    }
}

