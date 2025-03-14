import java.util.HashMap;
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

    public static HashMap<Character,String> findCodes(char[] massage, int numberOfDifferentPearlTypes, int[] pearlTypes) {
        Loader.loadNativeLibraries(); // OR-Tools initialisieren

        MPSolver solver = new MPSolver("ILP", MPSolver.OptimizationProblemType.CBC_MIXED_INTEGER_PROGRAMMING);

        char[] charTypes = getCharTypes(massage, numberOfDifferentPearlTypes);
        MPVariable[] codes = new MPVariable[charTypes.length];
        for(int i = 0; i < codes.length; i++){
            codes[i] = solver.makeIntVar(0, Integer.MAX_VALUE, Character.toString(charTypes[i]));
        }

        MPVariable[][] splitCodes = new MPVariable[codes.length][10];
        for (int i = 0; i < codes.length; i++) {
            for (int j = 0; j < 10; j++) {
                splitCodes[i][j] = solver.makeIntVar(0, 9, Character.toString(charTypes[i]) + Integer.toString(j));
            }
        }

        MPVariable schmuckLength = solver.makeIntVar(0,0, "schmuckLength");

        // implement the seting of the splitCodes 

        for (int i = 0; i < codes.length; i++) {
            MPConstraint constraint = solver.makeConstraint(codes[i].lb(), codes[i].ub(), "Split_Constraint_" + i);
        
            for (int j = 0; j < 10; j++) {
                constraint.setCoefficient(splitCodes[i][j], Math.pow(10, 9 - j));
            }
        }


        // todo implement the prefixfree save

        for (int i = 0; i < codes.length; i++) {
            for (int j = 0; j < codes.length; j++) {
                if (i != j) {
                    for (int k = 0; k < 10; k++) {
                        MPConstraint constraint = solver.makeConstraint(0, 0, "Inequality_Constraint_" + i + "_" + j + "_" + k);
                        constraint.setCoefficient(splitCodes[i][k], 1);
                        constraint.setCoefficient(splitCodes[j][k], -1);
                    }
                }
            }
        }
        // todo implement the the setting of the Length

        MPConstraint legthConstrain = solver.makeConstraint(schmuckLength.lb(), schmuckLength.ub());

        // ! find out what the secend value of "setCoefficient" means and set it arordingly

        MPObjective objective = solver.objective();
        objective.setCoefficient(schmuckLength, 1);
        objective.setMinimization();

        solver.solve();
         HashMap<Character,String> codeMap = new HashMap<>();
         for(int i = 0; i < codes.length;i++){
            codeMap.put(charTypes[i], Double.toString(codes[i].solutionValue()));
         }
         return codeMap;
    }
}
