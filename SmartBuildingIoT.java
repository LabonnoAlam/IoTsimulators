import java.util.Scanner;
import java.util.InputMismatchException;

public class SmartBuildingIoTAS {
    
    // Utility methods to print
    public static void print(String s) {
        System.out.print(s);
    }

    public static void println(String s) {
        System.out.println(s);
    }

    public static void main(String[] args) {
        // Instantiate a Scanner object for user input
        Scanner input1 = new Scanner(System.in);

        // Instantiate Control class (assuming it's implemented elsewhere)
        Control control = new Control();

        System.out.println("--------------Smart Building IoT Home Management System--------------"); 
        System.out.println("Please enter some basic information to start the system");

        // Input section for temperature
        double tempTemp;
        while (true) {
            try {
                System.out.print("Temperature: ");
                tempTemp = input1.nextDouble();
                if (tempTemp < 15.0 || tempTemp > 35.0) {
                    System.out.println("Please enter a valid temperature between 15.0 - 35.0");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                println("Invalid input format! Please enter a numeric value.");
                input1.next(); // Clear the invalid input
            }
        }

        // Input section for day
        String tempDay;
        while (true) {
            try {
                System.out.print("Day (Enter YES / NO): ");
                tempDay = input1.next().toUpperCase();
                if (tempDay.equals("YES") || tempDay.equals("NO")) {
                    break;
                } else {
                    System.out.println("Please enter a valid input (YES / NO).");
                }
            } catch (InputMismatchException e) {
                println("Invalid input format! Please enter YES or NO.");
                input1.next(); // Clear the invalid input
            }
        }

        // Input section for person count
        int tempPer;
        while (true) {
            try {
                System.out.print("Number of persons in the room: ");
                tempPer = input1.nextInt();
                if (tempPer < 0) {
                    System.out.println("Please enter a valid number. Persons cannot be negative.");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                println("Invalid input format! Please enter a numeric value.");
                input1.next(); // Clear the invalid input
            }
        }

        // Update the Status class with the collected input
        Status.Temperature = tempTemp;
        if (Status.Temperature >= 24) {
            Status.AC = 23.0;
            Status.Heater = 0.0;
        } else {
            Status.AC = 0.0;
            Status.Heater = 23.0;
        }

        if (tempDay.equals("YES")) {
            Status.Day = true;
            Status.Light = false;
            Status.Shading = false;
        } else {
            Status.Day = false;
            Status.Light = true;
            Status.Shading = true;
        }

        Status.PersonPresent = tempPer;

        // Main process loop for managing operations
        while (true) {
            System.out.println("\nChoose an operation:");
            println("1. Light");
            println("2. Shading");
            println("3. AC");
            println("4. Heater");
            println("5. Update Temperature");
            println("6. Update Day Status");
            println("7. Update Number of Persons");
            println("8. Manage Solar Power");

            print("Operation Number (1-8): ");
            int operation = input1.nextInt();

            switch (operation) {
                case 1:
                    handleLightControl(input1, control);
                    break;
                case 2:
                    handleShadingControl(input1, control);
                    break;
                case 3:
                    handleACControl(input1, control);
                    break;
                case 4:
                    handleHeaterControl(input1, control);
                    break;
                case 5:
                    handleTemperatureControl(input1, control);
                    break;
                case 6:
                    handleDayControl(input1, control);
                    break;
                case 7:
                    handlePersonControl(input1, control);
                    break;
                case 8:
                    manageSolarPower();
                    break;
                default:
                    println("Operation out of range. Please select a number between 1 and 8.");
            }
        }
    }

    // Methods for handling different operations
    public static void handleLightControl(Scanner input1, Control control) {
        String lightInput;
        while (true) {
            print("Do you want to turn ON or OFF the light? ");
            lightInput = input1.next().toUpperCase();
            if (lightInput.equals("ON") || lightInput.equals("OFF")) {
                if (Status.PersonPresent == 0 && lightInput.equals("ON")) {
                    println("Cannot turn the light ON because no one is present in the room.");
                } else {
                    control.light(lightInput);
                }
                break;
            } else {
                println("Invalid input! Please enter ON or OFF.");
            }
        }
    }

    public static void handleShadingControl(Scanner input1, Control control) {
        String shadeInput;
        while (true) {
            print("Do you want to turn ON or OFF the shading? ");
            shadeInput = input1.next().toUpperCase();
            if (shadeInput.equals("ON") || shadeInput.equals("OFF")) {
                control.shading(shadeInput);
                break;
            } else {
                println("Invalid input! Please enter ON or OFF.");
            }
        }
    }

    public static void handleACControl(Scanner input1, Control control) {
        double acTemp;
        while (true) {
            print("Enter new AC temperature: ");
            try {
                acTemp = input1.nextDouble();
                if (acTemp >= 15 && acTemp <= 35) {
                    control.ac(acTemp);
                    break;
                } else {
                    println("Please enter a valid temperature between 15 and 35.");
                }
            } catch (InputMismatchException e) {
                println("Invalid input! Please enter a numeric value.");
                input1.next(); // Clear the invalid input
            }
        }
    }

    public static void handleHeaterControl(Scanner input1, Control control) {
        double heaterTemp;
        while (true) {
            print("Enter new heater temperature: ");
            try {
                heaterTemp = input1.nextDouble();
                if (heaterTemp >= 15 && heaterTemp <= 35) {
                    control.heater(heaterTemp);
                    break;
                } else {
                    println("Please enter a valid temperature between 15 and 35.");
                }
            } catch (InputMismatchException e) {
                println("Invalid input! Please enter a numeric value.");
                input1.next(); // Clear the invalid input
            }
        }
    }

    public static void handleTemperatureControl(Scanner input1, Control control) {
        double newTemp;
        while (true) {
            print("Enter new temperature: ");
            try {
                newTemp = input1.nextDouble();
                if (newTemp >= 15 && newTemp <= 35) {
                    control.temperature(newTemp);
                    break;
                } else {
                    println("Please enter a valid temperature between 15 and 35.");
                }
            } catch (InputMismatchException e) {
                println("Invalid input! Please enter a numeric value.");
                input1.next(); // Clear the invalid input
            }
        }
    }

    public static void handleDayControl(Scanner input1, Control control) {
        String dayInput;
        while (true) {
            print("Is it daytime? (YES / NO): ");
            dayInput = input1.next().toUpperCase();
            if (dayInput.equals("YES") || dayInput.equals("NO")) {
                control.day(dayInput);
                break;
            } else {
                println("Invalid input! Please enter YES or NO.");
            }
        }
    }

    public static void handlePersonControl(Scanner input1, Control control) {
        int personCount;
        while (true) {
            print("Enter number of persons present: ");
            try {
                personCount = input1.nextInt();
                if (personCount >= 0) {
                    control.person(personCount);
                    break;
                } else {
                    println("Please enter a non-negative number.");
                }
            } catch (InputMismatchException e) {
                println("Invalid input! Please enter a numeric value.");
                input1.next(); // Clear the invalid input
            }
        }
    }

    public static void manageSolarPower() {
        if (Status.Day) {
            println("Solar panel is working. Battery charging: 93%.");
        } else {
            println("Solar panel is inactive. Battery is in standby mode.");
        }
    }
}

