import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

// Class representing the elements in the system (light, shading, fan, etc.)
class Element {
    private Map<String, ElementStatus> statusMap;

    public Element() {
        statusMap = new HashMap<>();
        // Initializing elements with their default status (OFF/0)
        statusMap.put("Light", new ElementStatus(false, -1));
        statusMap.put("Shading", new ElementStatus(false, -1));
        statusMap.put("Fan", new ElementStatus(false, -1));
        statusMap.put("AC", new ElementStatus(false, -1));
        statusMap.put("Heater", new ElementStatus(false, -1));
        statusMap.put("People", new ElementStatus(false, 0)); // No people in the room initially
    }

    public Map<String, ElementStatus> getAll() {
        return statusMap;
    }

    public ElementStatus get(String key) {
        return statusMap.getOrDefault(key, new ElementStatus(false, -1));
    }

    public void set(String key, ElementStatus status) {
        statusMap.put(key, status);
    }

    // Turn off all systems
    public void turnOff() {
        for (Map.Entry<String, ElementStatus> entry : statusMap.entrySet()) {
            entry.setValue(new ElementStatus(false, -1));
        }
    }
}

class ElementStatus {
    public boolean isOn;
    public int value;

    public ElementStatus(boolean isOn, int value) {
        this.isOn = isOn;
        this.value = value;
    }

    @Override
    public String toString() {
        return (isOn ? "ON" : "OFF") + (value != -1 ? " [" + value + "°C]" : "");
    }
}

public class IoTManagementSystem {

    public static void guiLineDivider() {
        System.out.println("--------------------------------------------");
        System.out.println("--------------------------------------------");
    }

    public static void handleInvalidInput(Scanner sc) {
        sc.next();  // Clear invalid input
        System.out.println("Invalid input. Please try again.");
    }

    public static void showStatus(Element element) {
        for (Map.Entry<String, ElementStatus> entry : element.getAll().entrySet()) {
            String key = entry.getKey();
            ElementStatus status = entry.getValue();
            System.out.print("\t* " + key + ": ");
            if (key.equals("People")) {
                System.out.println(status.isOn ? status.value + " people" : "No people");
            } else {
                System.out.println(status);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Element element = new Element();
        boolean programClose = false;

        System.out.println("----- Smart Building IoT Home Automation Management System -----");

        while (!programClose) {
            System.out.println("Status:");
            showStatus(element);
            guiLineDivider();

            short opt = getUserOption(scanner, "Choose an option\n\t1. Update\n\t0. Exit\n\t: ", 0, 1);
            if (opt == 0) {
                programClose = true;
                System.out.println("System is turned off!");
                break;
            }

            guiLineDivider();

            String[] updateOptions = {"Light ON/OFF", "Shading ON/OFF", "Fan ON/OFF", "Change Temperature", "Number of People in room"};
            opt = getUserOption(scanner, updateOptions);

            guiLineDivider();

            switch (opt) {
                case 1: handleLightUpdate(scanner, element); break;
                case 2: handleShadingUpdate(scanner, element); break;
                case 3: handleFanUpdate(scanner, element); break;
                case 4: handleTemperatureChange(scanner, element); break;
                case 5: handlePeopleUpdate(scanner, element); break;
            }
        }

        scanner.close();
        guiLineDivider();
    }

    private static short getUserOption(Scanner scanner, String prompt, int min, int max) {
        short opt;
        while (true) {
            System.out.print(prompt);
            try {
                opt = scanner.nextShort();
                if (opt >= min && opt <= max) {
                    break;
                } else {
                    System.out.println("Invalid option. Please choose between " + min + " and " + max + ".");
                }
            } catch (InputMismatchException e) {
                handleInvalidInput(scanner);
            }
        }
        return opt;
    }

    private static short getUserOption(Scanner scanner, String[] options) {
        while (true) {
            System.out.println("Choose an option to update:");
            for (int i = 0; i < options.length; i++) {
                System.out.println("\t" + (i + 1) + ". " + options[i]);
            }
            System.out.print(": ");
            try {
                return scanner.nextShort();
            } catch (InputMismatchException e) {
                handleInvalidInput(scanner);
            }
        }
    }

    private static void handleLightUpdate(Scanner scanner, Element element) {
        while (true) {
            System.out.print("Choose an option:\n\t1. Light ON\n\t2. Light OFF\n\t: ");
            try {
                short opt = scanner.nextShort();
                if (opt == 1 && element.get("People").value > 0) {
                    element.set("Light", new ElementStatus(true, -1));
                    System.out.println("Light ON successfully.");
                } else if (opt == 2) {
                    element.set("Light", new ElementStatus(false, -1));
                    System.out.println("Light OFF successfully.");
                } else {
                    System.out.println("No people in the room. Cannot turn the light on.");
                }
                break;
            } catch (InputMismatchException e) {
                handleInvalidInput(scanner);
            }
        }
    }

    private static void handleShadingUpdate(Scanner scanner, Element element) {
        while (true) {
            System.out.print("Choose an option:\n\t1. Shading UP\n\t2. Shading DOWN\n\t: ");
            try {
                short opt = scanner.nextShort();
                if (opt == 1) {
                    element.set("Shading", new ElementStatus(true, -1));
                    System.out.println("Shading UP successfully.");
                } else if (opt == 2) {
                    element.set("Shading", new ElementStatus(false, -1));
                    System.out.println("Shading DOWN successfully.");
                }
                break;
            } catch (InputMismatchException e) {
                handleInvalidInput(scanner);
            }
        }
    }

    private static void handleFanUpdate(Scanner scanner, Element element) {
        while (true) {
            System.out.print("Choose an option:\n\t1. Fan ON\n\t2. Fan OFF\n\t: ");
            try {
                short opt = scanner.nextShort();
                if (opt == 1 && element.get("People").value > 0) {
                    element.set("Fan", new ElementStatus(true, -1));
                    System.out.println("Fan ON successfully.");
                } else if (opt == 2) {
                    element.set("Fan", new ElementStatus(false, -1));
                    System.out.println("Fan OFF successfully.");
                } else {
                    System.out.println("No people in the room. Cannot turn the fan on.");
                }
                break;
            } catch (InputMismatchException e) {
                handleInvalidInput(scanner);
            }
        }
    }

    private static void handleTemperatureChange(Scanner scanner, Element element) {
        while (true) {
            System.out.print("Set Room Temperature [15°C - 35°C]: ");
            try {
                int temp = scanner.nextInt();
                if (temp >= 15 && temp <= 35) {
                    if (element.get("People").value > 0) {
                        if (temp <= 25) {
                            element.set("AC", new ElementStatus(true, temp));
                            element.set("Heater", new ElementStatus(false, -1));
                        } else {
                            element.set("Heater", new ElementStatus(true, temp));
                            element.set("AC", new ElementStatus(false, -1));
                        }
                        System.out.println("Temperature set to " + temp + "°C.");
                    } else {
                        System.out.println("No people in the room. Cannot change the temperature.");
                    }
                    break;
                } else {
                    System.out.println("Temperature must be between 15 and 35.");
                }
            } catch (InputMismatchException e) {
                handleInvalidInput(scanner);
            }
        }
    }

    private static void handlePeopleUpdate(Scanner scanner, Element element) {
        while (true) {
            System.out.print("Number of People in the room [0 - 5]: ");
            try {
                int people = scanner.nextInt();
                if (people >= 0 && people <= 5) {
                    if (people == 0) {
                        element.turnOff();
                        System.out.println("All systems turned off as there are no people in the room.");
                    } else {
                        element.set("People", new ElementStatus(true, people));
                        System.out.println(people + " people in the room.");
                    }
                    break;
                } else {
                    System.out.println("Please enter a number between 0 and 5.");
                }
            } catch (InputMismatchException e) {
                handleInvalidInput(scanner);
            }
        }
    }
}
