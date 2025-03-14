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

    public static HashMap<Character,String> findCodes(char[] massage, int numberOfDifferentPearlTypes, int[] pearlTypes) {
        Loader.loadNativeLibraries(); // OR-Tools initialisieren

        MPSolver solver = new MPSolver("ILP", MPSolver.OptimizationProblemType.CBC_MIXED_INTEGER_PROGRAMMING);

        char[] charTypes = getCharTypes(massage, numberOfDifferentPearlTypes);
        MPVariable[] codes = new MPVariable[charTypes.length];
        for(int i = 0; i < codes.length; i++){
            codes[i] = solver.makeIntVar(0, Integer.MAX_VALUE, Character.toString(charTypes[i]));
        }

        MPVariable[][] splitCodes = new MPVariable[codes.length][10];
        for(int i = 0; i < codes.length; i++){
            int j = 0;
            for(MPVariable codeDigit : splitCodes[i]){
                codeDigit = solver.makeIntVar(0, 9, Character.toString(charTypes[i]) + Integer.toString(j));
                j++;
            }
            j = 0;
        }

        MPVariable schmuckLength = solver.makeIntVar(0,0, "schmuckLength");


        // todo implement the seting of the splitCodes 
        // todo implement the prefixfree save
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
