import java.util.HashMap;
import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;
import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;

public class SchmucknachrichtenILP {

    private static HashMap<Character, Integer> getCharTypesWithFrequency(char[] message) {
        HashMap<Character, Integer> frequencyMap = new HashMap<>();

        for (char c : message) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }

        return frequencyMap;
    }

    public static HashMap<Character, String> findCodes(char[] massage, int numberOfDifferentPearlTypes, int[] pearlTypes) {
        Loader.loadNativeLibraries(); // OR-Tools initialisieren

        MPSolver solver = new MPSolver("ILP", MPSolver.OptimizationProblemType.CBC_MIXED_INTEGER_PROGRAMMING);

        HashMap<Character, Integer> charFrequencyMap = getCharTypesWithFrequency(massage);
        char[] charTypes = new char[charFrequencyMap.size()];
        int[] charfrequency = new int[charFrequencyMap.size()];
        int idx = 0;
        for (int freq : charFrequencyMap.values()) {
            charfrequency[idx++] = freq;
        }
        int index = 0;
        for (Character c : charFrequencyMap.keySet()) {
            charTypes[index++] = c;
        }

        MPVariable[] codes = new MPVariable[charTypes.length];
        for (int i = 0; i < codes.length; i++) {
            codes[i] = solver.makeIntVar(0, Integer.MAX_VALUE, Character.toString(charTypes[i]));
        }

        MPVariable[][] splitCodes = new MPVariable[codes.length][10];
        for (int i = 0; i < codes.length; i++) {
            for (int j = 0; j < 10; j++) {
                splitCodes[i][j] = solver.makeIntVar(0, numberOfDifferentPearlTypes - 1, Character.toString(charTypes[i]) + Integer.toString(j));
            }
        }

        MPVariable schmuckLength = solver.makeIntVar(0, Integer.MAX_VALUE, "schmuckLength");

        // Implementiere das Setzen von splitCodes
        for (int i = 0; i < codes.length; i++) {
            MPConstraint constraint = solver.makeConstraint(codes[i].lb(), codes[i].ub(), "Split_Constraint_" + i);
            for (int j = 0; j < 10; j++) {
                constraint.setCoefficient(splitCodes[i][j], Math.pow(10, 9 - j));
            }
        }

        // Implementiere das Prefix-free-Speichern
        for (int i = 0; i < codes.length; i++) {
            for (int j = 0; j < codes.length; j++) {
                if (i != j) {
                    for (int k = 0; k < 10; k++) {
                        MPConstraint constraint = solver.makeConstraint(0, 0, "prefixfree_Constraint_" + i + "_" + j + "_" + k);
                        constraint.setCoefficient(splitCodes[i][k], 1);
                        constraint.setCoefficient(splitCodes[j][k], -1);
                    }
                }
            }
        }

        // Implementiere das Setzen der Länge
        MPConstraint lengthConstraint = solver.makeConstraint(0, Integer.MAX_VALUE, "Length_Constraint");

        for (int i = 0; i < codes.length; i++) {
            for (int j = 0; j < 10; j++) {
                lengthConstraint.setCoefficient(splitCodes[i][j], pearlTypes[(int) splitCodes[i][j].solutionValue()]*charfrequency[i]);
            }
        }

        // Bedingung, um sicherzustellen, dass jeder Code einzigartig ist
        for (int i = 0; i < codes.length; i++) {
            for (int j = i + 1; j < codes.length; j++) {
                MPConstraint uniqueConstraint = solver.makeConstraint(1, Double.POSITIVE_INFINITY, "Unique_Constraint_" + i + "_" + j);
                uniqueConstraint.setCoefficient(codes[i], 1);
                uniqueConstraint.setCoefficient(codes[j], -1);
            }
        }

        // Setze das Ziel auf Minimierung der Gesamtlänge
        MPObjective objective = solver.objective();
        objective.setCoefficient(schmuckLength, 1);
        objective.setMinimization();

        if (solver.solve() == MPSolver.ResultStatus.OPTIMAL) {
            System.out.println("optimal!");
        } else {
            System.out.println("Kein Ergebnis gefunden.");
        }

        HashMap<Character, String> codeMap = new HashMap<>();
        for (int i = 0; i < codes.length; i++) {
            codeMap.put(charTypes[i], Integer.toString((int) codes[i].solutionValue()));
        }

         return codeMap;
    }
}
