package com.example.knapsnack;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
/*this is a knapsack project which uses dynamic programming techniques to calculate the
net profits with the given criteria for the organisation-Isha Rijal ,
I mostly took help from YOutube and some from online sources to complete this
 */
public class Knapsack {

    static class Project {
        String name;
        int labor;
        int profit;

        public Project(String name, int labor, int profit) {
            this.name = name;
            this.labor = labor;
            this.profit = profit;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt user for input
        System.out.print("Enter the number of available employee work weeks: ");
        int availableWeeks = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter the name of input file: ");
        String inputFile = scanner.nextLine();

        System.out.print("Enter the name of output file: ");
        String outputFile = scanner.nextLine();

        // Read all projects from input file
        ArrayList<Project> projects = readProjectsFromFile(inputFile);

        // Count the number of projects
        int projectCount = projects.size();

        // Solve knapsack problem
        ArrayList<Project> chosenProjects = knapsack(projects, availableWeeks);

        // Write results to output file
        writeOutputToFile(outputFile, availableWeeks, chosenProjects, projectCount);

        System.out.println("Done");
        scanner.close();
    }

    // Reads all projects from input file
    private static ArrayList<Project> readProjectsFromFile(String fileName) {
        ArrayList<Project> projects = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(fileName));
            while (scanner.hasNextLine()) {
                String[] projectData = scanner.nextLine().split(" ");
                String name = projectData[0];
                int labor = Integer.parseInt(projectData[1]);
                int profit = Integer.parseInt(projectData[2]);
                projects.add(new Project(name, labor, profit));

            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return projects;
    }

    // Solves the knapsack problem using dynamic programming
    private static ArrayList<Project> knapsack(ArrayList<Project> projects, int availableWeeks) {
        int n = projects.size();
        int[][] dp = new int[n + 1][availableWeeks + 1];

        // Build dp table
        for (int i = 1; i <= n; i++) {
            Project project = projects.get(i - 1);
            for (int j = 0; j <= availableWeeks; j++) {
                if (project.labor > j) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - project.labor] + project.profit);
                }
            }
        }

        // Retrieve chosen projects
        ArrayList<Project> chosenProjects = new ArrayList<>();
        int weeks = availableWeeks;
        for (int i = n; i > 0 && weeks > 0; i--) {
            if (dp[i][weeks] != dp[i - 1][weeks]) {
                chosenProjects.add(projects.get(i - 1));
                weeks -= projects.get(i - 1).labor;
            }
        }

        return chosenProjects;
    }

    // Writes results to output file
    private static void writeOutputToFile(String fileName, int availableWeeks, ArrayList<Project> chosenProjects, int projectCount) {
        try {

            FileWriter writer = new FileWriter(fileName);
            // Read all number of projects from input file
            writer.write("Number of projects available: " + projectCount + "\n");


            writer.write("Available employee work weeks: " + availableWeeks + "\n");
            writer.write("Number of projects chosen: " + chosenProjects.size() + "\n");
            int totalProfit = 0;
            for (Project project : chosenProjects) {
                writer.write(project.name + " " + project.labor + " " + project.profit + "\n");
                totalProfit += project.profit;
            }
            writer.write("Total profit: " + totalProfit + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

