import java.util.HashMap;
import java.util.Set;
import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;
import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;

public class SchmucknachrichtenILP {

    private static char[] getCharTypes(char[] message, int numberOfDifferentPearlTypes) {
        HashMap<Character, Integer> frequencyMap = new HashMap<>();

        for (char c : message) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }

        Set<Character> charTypSet = frequencyMap.keySet();
        // Konvertiere das Set in ein Array von chars
        char[] charTypes = new char[charTypSet.size()];
        int i = 0;
        for (Character c : charTypSet) {
            charTypes[i++] = c;
        }
        return charTypes;
    }

    public static HashMap<Character, String> findCodes(char[] massage, int numberOfDifferentPearlTypes, int[] pearlTypes) {
        Loader.loadNativeLibraries(); // OR-Tools initialisieren

        MPSolver solver = new MPSolver("ILP", MPSolver.OptimizationProblemType.CBC_MIXED_INTEGER_PROGRAMMING);

        char[] charTypes = getCharTypes(massage, numberOfDifferentPearlTypes);
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

        // Addiere die Länge jedes Code-Kombinationswertes zu der Gesamtlänge
        for (int i = 0; i < codes.length; i++) {
            for (int j = 0; j < 10; j++) {
                // Verwende den Index von splitCodes[i][j], um die Perlengröße aus pearlTypes zu holen
                lengthConstraint.setCoefficient(splitCodes[i][j], pearlTypes[(int) splitCodes[i][j].solutionValue()]);
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

        solver.solve();

        HashMap<Character, String> codeMap = new HashMap<>();
        for (int i = 0; i < codes.length; i++) {
            codeMap.put(charTypes[i], Integer.toString((int) codes[i].solutionValue()));
        }

        return codeMap;
    }
}
