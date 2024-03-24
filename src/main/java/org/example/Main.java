package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Double> weights = new ArrayList<>();
        List<List<String>> allGrades = new ArrayList<>();
        List<String> results = new ArrayList<>();
        Integer alternativeNumb = 0;
        String fileName = "D:\\User\\unic\\МЭО\\inputData.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            if ((line = reader.readLine()) != null) {
                alternativeNumb = Integer.parseInt(line);
            }
            if ((line = reader.readLine()) != null) {
                weights = Arrays.stream(line.split("[ \t]"))
                        .mapToDouble(Double::parseDouble)
                        .boxed()
                        .collect(Collectors.toList());
            }

            for (int i = 0; i < alternativeNumb; i++) {
                if ((line = reader.readLine()) != null) {
                    List<String> grades = Arrays.asList(line.split("[ \t]"));
                    allGrades.add(grades);
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        }

        int i = 1;
        for (List<String> grades : allGrades) {
            OWACalculator owaCalculator = new OWACalculator(weights, grades);
            Long k = owaCalculator.calculateCombination(owaCalculator.getWeights(), owaCalculator.getGrades(),grades.size());
            results.add("S" + k);
            System.out.printf("РЕЗУЛЬТАТ Фw(A) = С%d = S%d%n--------------------------------------------------%n",weights.size(),k);
            i++;
        }
        System.out.println("\n" + results);
    }
}