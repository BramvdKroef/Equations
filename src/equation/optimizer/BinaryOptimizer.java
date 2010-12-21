package equation.optimizer;

import equation.BinaryOperator;
import equation.Operator;
import equation.Variable;
import equation.OptimizerStrategy;
import equation.Symbol;

import java.util.LinkedList;

public class BinaryOptimizer implements OptimizerStrategy {
    private BinaryOperator op;
    
    public BinaryOptimizer(BinaryOperator op) {
	this.op = op;
    }

    public BinaryOperator getOperator() {
	return op;
    }
    
    /**
     * Optimize the operator by checking each operands. If one of
     * the operands is a BinaryOperator than it is optimized as well.
     * When overriding this method it is best to call this one before
     * doing any optimizing to make sure the operands are optimized.
     */
    public Symbol optimize() {
	for (int i = 0; i < op.OPERANDS; i++) {
	    Symbol s = op.getOperand(i);
	    if (s instanceof BinaryOperator) {
		s = ((BinaryOperator)s).optimize();
		if (s != null)
		    op.setOperand(s, i);
	    }
	}
	return op;
    }


    /**
     * Find out what variables both branches of the operator
     * have in common.
     * The variables of both subject1 and subject2 are collected
     * and compared and one variable that both have in common
     * is returned.
     */
    public Variable getCommon() {
	LinkedList<Variable>[] ops = new LinkedList[BinaryOperator.OPERANDS];

	for (int i = 0; i < BinaryOperator.OPERANDS; i++) {
	    ops[i] = new LinkedList<Variable>();
	    op.getVariables(ops[i], i);
	}
		
	ops[BinaryOperator.LEFT].retainAll(ops[BinaryOperator.RIGHT]);
	if (ops[BinaryOperator.LEFT].size() > 0)
	    return ops[BinaryOperator.LEFT].getFirst();
	else
	    return null;
    }

    /**
     * Compares symbols to see which should be ordered before
     * the other. If one of the symbols is a BinaryOperator
     * it comes first. If both are then the order is decided
     * by hasPriority(). If none are then the order stays the same.
     * @param s1 a symbol to check. 
     * @param s2 another symbol to check against s1
     * @return -1 if s1 should come first; 1 if s2 should come first;
     *   or 0 if it is undecided.
     */
    protected int compare(Symbol s1, Symbol s2) {
	if (s1 instanceof BinaryOperator) {
	    if (s2 instanceof BinaryOperator) {
		if (((BinaryOperator)s1).hasPriority((BinaryOperator)s2))
		    return -1;
		else
		    return 1;
	    } else
		return -1;
	} else if (s2 instanceof BinaryOperator)
	    return 1;
	else if (s1 instanceof Variable)
	    return -1;
	else if (s2 instanceof Variable)
	    return 1;
	else
	    return 0;
    }

}