package equation;

public interface OptimizerStrategy {
    /**
     * Looks at the BinaryOperator in the care of the optimizer and attempts
     * to create an optimized version. 
     * 
     * @return the optimized version of the operator.
     */
    public Symbol optimize();
}