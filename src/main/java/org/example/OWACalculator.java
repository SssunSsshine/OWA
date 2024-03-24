package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OWACalculator {
    private List<Double> weights;
    private List<String> grades;
    private Integer size;

    private static final Map<String, Integer> terms
            = Map.of("VL", 1, "L", 2, "M", 3, "H", 4, "VH", 5);

    public OWACalculator(List<Double> weights, List<String> grades) {
        this.weights = weights;
        this.grades = orderGrades(grades);
        this.size = terms.size();
    }

    public List<Double> getWeights() {
        return weights;
    }

    public List<String> getGrades() {
        return grades;
    }

    public Long calculateCombination(List<Double> weights, List<String> grades, Integer counter) {
        Long k;
        if (grades.size() > 2) {
            Long prevComb = calculateCombination(calculateWeight(weights), grades.subList(1, grades.size()), counter - 1);

            System.out.println("Веса: " + weights);
            System.out.println("Оценки: " + grades);
            System.out.println("(Предыдущая комбинация) C" + (counter - 1) + " = S" + prevComb);
            System.out.printf("Подсчет значений для C%d{%s} = %f * %s + (1 - %f) * C%d{%s}%n", counter, grades, weights.get(0), grades.get(0), weights.get(0), counter - 1, grades.subList(1, grades.size()));

            k = calculateK(size, weights.get(0), prevComb, terms.get(grades.get(0)));
        } else {
            System.out.println("Подсчет значений для C" + counter);
            System.out.println("Веса: " + weights);
            System.out.println("Оценки: " + grades);

            k = calculateK(size, weights.get(0), Long.parseLong(terms.get(grades.get(1)).toString()), terms.get(grades.get(0)));
        }
        System.out.printf("С%d = S%d%n%n", counter, k);
        return k;
    }

    private List<String> orderGrades(List<String> grades) {
        List<String> orderedGrades = grades
                .stream()
                .sorted((o1, o2) -> terms.get(o2).compareTo(terms.get(o1)))
                .collect(Collectors.toList());
        System.out.println("Упорядоченные оценки: " + orderedGrades + "\n");
        return orderedGrades;
    }

    private List<Double> calculateWeight(List<Double> weights) {
        List<Double> newWeights = new ArrayList<>();
        Double sum = 0.0;
        for (int i = 1; i < weights.size(); i++) {
            sum += weights.get(i);
        }
        for (int i = 1; i < weights.size(); i++) {
            newWeights.add(weights.get(i) / sum);
        }
        return newWeights;
    }

    private Long calculateK(Integer T, Double w, Long i, Integer j) {
        Long k = Math.min(T, i + Math.round(w * (j - i)));
        System.out.printf("k = min(%d,%d+round(%.3f * (%d - %d))) = %d%n", T, i, w, j, i, k);
        return k;
    }
}
