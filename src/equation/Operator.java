package equation;

import equation.Value;
import equation.Symbol;
import java.util.LinkedList;

public abstract class Operator implements Symbol {

    /**
     * Attempts to find the value of the Variables in
     * it's tree, knowing the solution of the operator.
     */
    public abstract void solve(Symbol solution);
    
    public abstract void getVariables(LinkedList<Variable> variables);

}