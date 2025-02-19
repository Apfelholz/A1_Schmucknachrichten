
import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPSolver.ResultStatus;
import com.google.ortools.linearsolver.MPVariable;
import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;

public class ILPExample {
    public static void main(String[] args) {
        Loader.loadNativeLibraries(); // OR-Tools initialisieren

        MPSolver solver = new MPSolver("ILP", MPSolver.OptimizationProblemType.CBC_MIXED_INTEGER_PROGRAMMING);

        // Variablen (Ganzzahlen)
        MPVariable x = solver.makeIntVar(0, 100, "x");
        MPVariable y = solver.makeIntVar(0, 100, "y");

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


