import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Set;

import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPSolver.ResultStatus;
import com.google.ortools.linearsolver.MPVariable;
import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;

public class SchmucknachrichtenILP {

    private static char[] getCharTypes(char[] message, int numberOfDifferentPearlTypes){

        HashMap<Character, Integer> frequencyMap = new HashMap<>();

        for (char c : message){
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }

        Set<Character> charTypSet = frequencyMap.keySet();
        char[] charTyps = charTypSet.toString().toCharArray();

        return charTyps;
    }

    public static void findCodes(char[] massage, int numberOfDifferentPearlTypes, int[] pearlTypes) {
        Loader.loadNativeLibraries(); // OR-Tools initialisieren

        MPSolver solver = new MPSolver("ILP", MPSolver.OptimizationProblemType.CBC_MIXED_INTEGER_PROGRAMMING);

        // todo finde out waht the first tow values of "makeIntVar" men and set them arcordingly
        char[] charTypes = getCharTypes(massage, numberOfDifferentPearlTypes);
        MPVariable[] codes = new MPVariable[charTypes.length];
        for(int i = 0; i < codes.length; i++){
            codes[i] = solver.makeIntVar(0, 0, Character.toString(charTypes[i]));
        }

        MPVariable[][] splitCodes = new MPVariable[codes.length][10];
        for(int i = 0; i < codes.length; i++){
            int j = 0;
            for(MPVariable codeDigit : splitCodes[i]){
                codeDigit = solver.makeIntVar(0, 0, Character.toString(charTypes[i]) + Integer.toString(j));
                j++;
            }
            j = 0;
        }

        // Beispiel-Constraint: x + 2y ≤ 14
        MPConstraint constraint1 = solver.makeConstraint(Double.NEGATIVE_INFINITY, 14);
        constraint1.setCoefficient(x, 1);
        constraint1.setCoefficient(y, 2);

        // Zielfunktion: Maximiere x + y
        MPObjective objective = solver.objective();
        objective.setCoefficient(x, 1);
        objective.setCoefficient(y, 1);
        objective.setMaximization();

        ResultStatus resultStatus = solver.solve();
        if (resultStatus == ResultStatus.OPTIMAL) {
            System.out.println("Lösung gefunden:");
            System.out.println("x = " + x.solutionValue());
            System.out.println("y = " + y.solutionValue());
        } else {
            System.out.println("Keine optimale Lösung gefunden.");
        }
    }
}
