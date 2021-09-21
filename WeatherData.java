/*
File     :      WeatherData.java
Title    :      Curtin COMP1007 Assigment
Author   :      G.G.T.Shashen | ID 20534534
Created  :      08/06/2021
Modified :      11/06/2021
Desc     :      BOM Weather Station Application
*/
import java.io.*;
import java.util.*;
import java.util.Scanner;
public class WeatherData {

    // Declaration of Instance Variables
    private String code;
    private String stationNumber;
    private int year;
    private int month;
    private int day;
    public double maxTemp;

    // Main Method
    public static void main(String[] args) {
        // Declaration of  variables
        boolean loop=true;
        String name;
        int month;
        // Intizilization of Scanner
        Scanner sc = new Scanner(System.in);
        // Do While loop to take input repeatedly
        do {
            System.out.println("");
            System.out.println("");
            System.out.println("Welcome to the BOM Weather Station Application.");
            // Try Catch block to avoid invalid user input
            try {
                    // Getting the user input
                    System.out.print("Please enter the name of the data file or x to exit > ");
                    name = sc.next();
                    // Validating User Input
                    if (name.equals("x")) {
                            System.out.println("");
                            System.out.println("");
                            System.out.println("Terminating System...");
                            System.out.println("");
                            System.out.println("");
                            System.exit(0);
                        }else if (name.endsWith(".csv")){
                            // Calling required methods to display calculated values to the user
                            System.out.println("");
                            System.out.println("");
                            System.out.println("The data file contains "+ readNoLines(name) + " entries.");
                            System.out.println("There were "+ readEmptyLines(name) +" incomplete data entries.");
                            System.out.println("");
                            System.out.println("");
                            // Calling findMax and findMin methods to calculate max and min of the csv file
                            findMax(name);
                            findMin(name);
                            System.out.println("");
                            System.out.println("");
                            System.out.print("What month would you like to know the average temperature for? [1-12] > ");
                            month = sc.nextInt();
                            // Validating user input
                            if (month>0 && month<13) {
                                System.out.println("");
                                System.out.println("");
                                readAvgTemp(month,name);
                                System.out.println("");
                                System.out.println("------------------------------------------------------------------------"); 
                                System.out.println("");
                            }else {
                                System.out.println("");
                                System.out.println("");
                                System.out.println("Invalid Input! Retry...");
                                System.out.println("");
                                System.out.println("");
                            }
                            
                        }else {
                            // Error status for invalid inputs
                            System.out.println("");
                            System.out.println("");
                            System.out.println("Invalid Input! Retry...");
                            System.out.println("");
                            System.out.println("");
                        }
                // Catch block error status
                } catch (InputMismatchException e) {
                        System.out.println("");
                        System.out.println("");
                        System.out.println("Invalid Input! Retry...");
                        System.out.println("");
                        System.out.println("");
            } 
        }while (loop);
        sc.close();
    }

    // Default Constructor
    public WeatherData() {
        code = this.getCode();
        stationNumber = this.getStationNumber();
        year = this.getYear();
        month = this.getMonth();
        day = this.getDay();
        maxTemp = this.getMaxTemp();
    }

    // Parameter Constructor
    public WeatherData(String code, String stationNumber, int year, int month, int day, double maxTemp) {
        this.code = code;
        this.stationNumber = stationNumber;
        this.year = year;
        this.month = month;
        this.day = day;
        this.maxTemp = maxTemp;
    }

    // Copy Constructor
    public WeatherData(WeatherData pWeatherData) {
        this.code = pWeatherData.getCode();
        this.stationNumber = pWeatherData.getStationNumber();
        this.year = pWeatherData.getYear();
        this.month = pWeatherData.getMonth();
        this.day = pWeatherData.getMonth();
        this.maxTemp = pWeatherData.getMaxTemp();
    }

    // setCode Mutator
    public void setCode(String code) {
        this.code = code;
    }

    // setStationNumber Mutator
    public void setStationNumber(String stationNumber) {
        this.stationNumber = stationNumber;
    }

    // setYear Mutator
    public void setYear(int year) {
        this.year = year;
    }

    // setMonth Mutator
    public void setMonth(int month) {
        this.month = month;
    }

    // setDay Mutator
    public void setDay(int day) {
        this.day = day;
    }

    // setMaxTemp Mutator
    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    // getCode Accessor
    public String getCode() {
        return this.code;
    }
    
    // getStationNumber Accessor
    public String getStationNumber() {
        return this.stationNumber;
    }

    // getYear Accessor
    public int getYear() {
        return this.year;
    }

    // getMonth Accessor
    public int getMonth() {
        return this.month;
    }

    // getDay Accessor
    public int getDay() {
        return this.day;
    }

    // getMaxTemp Accessor
    public double getMaxTemp() {
        return this.maxTemp;
    }

    // Convert to String method
    public String toString(int val) {
        return String.valueOf(val);
    }

    // isEqual method
    public boolean equals(Object inObject)
    {
        boolean isEqual = false;
        WeatherData inWeatherData = null;
        if(inObject instanceof WeatherData) {
            inWeatherData = (WeatherData)inObject;
            if(code.equals(inWeatherData.getCode()))
            if(stationNumber.equals(inWeatherData.getStationNumber()))
            if(year == inWeatherData.getYear())
            if(month == inWeatherData.getMonth())
            if(day == inWeatherData.getDay())
            if(maxTemp == inWeatherData.getMaxTemp())
            isEqual = true;
        }
        return isEqual;
    }

    // Date formatting method
    public String formattedDate(int year, int month, int day) {
        return toString(day)+"/"+toString(month)+"/"+toString(year);
    }

    // Method to read the number of lines in the datasheet
    public static int readNoLines(String path)
    {
        int count=-1;
        try {
        // BufferedReader to extract data from the datasheet
           BufferedReader br = new BufferedReader(new FileReader("./" + path));
            while((br.readLine()) != null) {
                count++;
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
            return count;
    }

    // Method to read the number of ommited data in the datasheet
    public static int readEmptyLines(String path) {

        String line="";
        int count=0;
        int missing;
        try {
            // BufferedReader to extract data from the datasheet
           BufferedReader br = new BufferedReader(new FileReader("./" + path));
           line = br.readLine();
           while((line = br.readLine()) != null) {
                String[] values = line.split(",");
                missing = 8 - values.length;
                count += missing;
           }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
            return count;
    }

    // findMax method returns the maximum value of the temperature column in the datasheet
    public static void findMax(String path) {
        // Declaration and Initilization of variables 
        String line="";
        int missing;
        double maxValue=0.0;
        // Creating new WeatherData object
        WeatherData vals = new WeatherData();
        try {
            // BufferedReader to extract data from the datasheet
            BufferedReader br = new BufferedReader(new FileReader("./" + path));
            // Skips the header row
            line = br.readLine();
            while((line = br.readLine()) != null) {
                // Assigns csv data values by splitting each column
                String[] values = line.split(",");
                // Finds the empty data cells in the datasheet
                missing = 8 - values.length;
                // Proceeds to calculate data for the columns which are not empty
                if (missing == 0) {
                    // Converting String data values to double
                    double currentValue = Double.parseDouble(values[5]);
                    // Finding the max value from the converted double array values
                    if(currentValue> maxValue) {
                        maxValue = currentValue;
                        // Calling setters to assign only specific data from the array
                        vals.setMaxTemp(maxValue);
                        vals.setYear(Integer.parseInt(values[2]));
                        vals.setMonth(Integer.parseInt(values[3]));
                        vals.setDay(Integer.parseInt(values[4]));
                    }
                }
            }
            br.close();
        // Catching the errors and priting to the console
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Displaying calculated data to the user
        System.out.println("Details of the maximum temperature: "+ vals.getMaxTemp() + " degrees on " + vals.formattedDate(vals.getYear(), vals.getMonth(), vals.getDay()));
    }

    // findMin method returns the minimum value of the temperature column in the datasheet
    public static void findMin(String path) {
        // Declaration and Initilization of variables
        String line="";
        int missing;
        double minValue=999999999;
        // Creating new WeatherData object
        WeatherData vals = new WeatherData();
        try {
            // BufferedReader to extract data from the datasheet
            BufferedReader br = new BufferedReader(new FileReader("./" + path));
            // Skips the header row
            line = br.readLine();
            while((line = br.readLine()) != null) {
                // Assigns csv data values by splitting each column
                String[] values = line.split(",");
                // Finds the empty data cells in the datasheet
                missing = 8 - values.length;
                // Proceeds to calculate data for the columns which are not empty
                    if (missing == 0) {
                        // Converting String data values to double
                        double currentValue = Double.parseDouble(values[5]);
                        // Finding the max value from the converted double array values
                        if(currentValue < minValue) {
                        // Calling setters to assign only specific data from the array
                            minValue = currentValue;
                            vals.setMaxTemp(minValue);
                            vals.setYear(Integer.parseInt(values[2]));
                            vals.setMonth(Integer.parseInt(values[3]));
                            vals.setDay(Integer.parseInt(values[4]));
                        }
                    }
                }
            br.close();
        // Catching the errors and priting to the console
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Displaying calculated data to the user
        System.out.println("Details of the minimum temperature: "+ minValue + " degrees on " + vals.formattedDate(vals.getYear(), vals.getMonth(), vals.getDay()));
    }

    // readAvgTemp method to calculate average temperature value from the datasheet
    public static void readAvgTemp(int month, String path) {
        // Declaration and Initilization of variables
        String line="";
        int count=0;
        double temp=0.0;
        int missing=0;
        String avg;
        // Creating new WeatherData object
        WeatherData vals = new WeatherData();
        // Converting Integer month value to string so it can be compared with array values
        String stringMonth = vals.toString(month);
        try {
            // BufferedReader to extract data from the datasheet
           BufferedReader br = new BufferedReader(new FileReader("./" + path));
           // Skips the header row
           line = br.readLine();
            while((line = br.readLine()) != null) {
                // Assigns csv data values by splitting each column
                String[] values = line.split(",");
                // Finds the empty data cells in the datasheet
                missing = 8 - values.length;
                // Proceeds to calculate data for the columns which are not empty
                if (missing == 0) {
                    // Finds the array value which corresponds to the user input
                    if ((values[3].contains(stringMonth)))
                    {
                        // Counts the converted array values
                        temp += Double.parseDouble(values[5]);
                        count++;
                    }
                }
            }
            // Calculates the average and formatted to 1 decimal place
            avg = String.format("%.1f",temp/count);
            // Displays the data to the user
            System.out.println("The average temperature for month "+ month +" was : "+ avg);
            br.close();
        // Catching the errors and priting to the console
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}