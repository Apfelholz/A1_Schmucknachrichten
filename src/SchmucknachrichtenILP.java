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
        Loader.loadNativeLibraries();
    
        MPSolver solver = MPSolver.createSolver("SCIP");
    
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
    
        // Declare MP VARs
        MPVariable[] codes = new MPVariable[charTypes.length];
        for (int i = 0; i < codes.length; i++) {
            codes[i] = solver.makeIntVar(0.0, Integer.MAX_VALUE, Character.toString(charTypes[i]));
        }
    
        MPVariable[][] splitCodes = new MPVariable[codes.length][10];
        for (int i = 0; i < codes.length; i++) {
            for (int j = 0; j < 10; j++) {
                splitCodes[i][j] = solver.makeIntVar(0, numberOfDifferentPearlTypes - 1, "splitCodes_" + i + "_" + j);
            }
        }
    
        // Set constraints
        for (int c = 0; c < codes.length; c++) {
            MPConstraint constraint = solver.makeConstraint(0, 0, "constraint_" + c);
    
            // Für jede Ziffer in splitCodes, multipliziere mit der entsprechenden Potenz von 10
            for (int sc = 0; sc < 10; sc++) {
                constraint.setCoefficient(splitCodes[c][sc], (int) Math.pow(10, 9 - sc));
            }
    
            // Setze codes[c] gleich der Summe der Ziffern
            constraint.setCoefficient(codes[c], -1);
        }

        for (int c = 0; c < codes.length; c++){
            for (int ca = 0; ca < codes.length; ca++){
                if(c != ca){
                    for(int i = 0; i < 10; i++){
                        MPConstraint constraint = solver.makeConstraint(-Integer.MAX_VALUE,-1);
                        for (int j = 0; j <= i; j++){
                            constraint.setCoefficient(splitCodes[c][j], -Math.pow(10, i - j));
                            constraint.setCoefficient(splitCodes[ca][j], Math.pow(10, i - j));
                        }
                    }
                }
            }
        }


    
        MPObjective objective = solver.objective();
        objective.setCoefficient(codes[0], 1);  // Maximierung von codes[0] oder eine andere Variable
        objective.setMaximization();
    
        if (solver.solve() == MPSolver.ResultStatus.OPTIMAL) {
            System.out.println("Optimal!");
        } else {
            System.out.println("Kein Ergebnis gefunden.");
        }
    
        // Schreibe die Lösungen aller Variablen in Arrays
        int[] codeValues = new int[codes.length];
        int[][] splitCodeValues = new int[codes.length][10];
    
        for (int i = 0; i < codes.length; i++) {
            codeValues[i] = (int) codes[i].solutionValue();
            for (int j = 0; j < 10; j++) {
                splitCodeValues[i][j] = (int) splitCodes[i][j].solutionValue();
            }
        }
    
        // Optional: Ausgabe der Werte zur Überprüfung
        System.out.println("Code Values:");
        for (int value : codeValues) {
            System.out.print(value + " ");
        }
        System.out.println("\nSplit Code Values:");
        for (int i = 0; i < splitCodeValues.length; i++) {
            for (int j = 0; j < splitCodeValues[i].length; j++) {
                System.out.print(splitCodeValues[i][j] + " ");
            }
            System.out.println();
        }
    
        HashMap<Character, String> codeMap = new HashMap<>();
        for (int i = 0; i < codes.length; i++) {
            codeMap.put(charTypes[i], Integer.toString(codeValues[i]));
        }
    
        return codeMap;
    }
    
}
