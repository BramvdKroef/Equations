package equation.optimizer;

import equation.optimizer.BinaryOptimizer;
import equation.binaryoperator.Multiply;
import equation.binaryoperator.Add;
import equation.binaryoperator.Power;
import equation.Symbol;
import equation.Variable;
import equation.Value;
import equation.BinaryOperator;

public class MultiplyOptimizer extends BinaryOptimizer {
    public MultiplyOptimizer(Multiply multiply) {
	super(multiply);
    }

    /**
     * If any of the operands are Add operators rearrange
     * the operands so that they are in order of exponents
     * and multipliers. This way 3 + 2x + x^5 + 5x becomes
     * x^5 + 2x + 5x + 3 which is easier to simplify.
     */
    public void rearrange() {
	// Only the left operand could be Add because
	// with operators of the same priority the
	// left one wins so 1+2+3+4 becomes ((1+2)+3)+4
	Symbol left = getOperator().getLeft();
	Symbol right = getOperator().getRight();
	
	if (left instanceof Multiply) {
	    Multiply multiply = (Multiply)left;
	    if (multiply.getOptimizerStrategy() instanceof MultiplyOptimizer) {
		MultiplyOptimizer o = (MultiplyOptimizer)multiply.getOptimizerStrategy();
		o.rearrange();
	    }
	    for (int i=0; i < BinaryOperator.OPERANDS; i++) {
		Symbol s = multiply.getOperand(i);

		// if right is higher than s
		if (compare(s, right) == 1) {
		    // swap operators
		    multiply.setOperand(right, i);
		    right = s;
		    getOperator().setRight(s);
		}
	    }
	} else if (compare(left, right) == 1) {
	    // Swap operands if right is higher than left
	    getOperator().setRight(left);
	    getOperator().setLeft(right);
	}
	System.out.println("Rearranged: " + getOperator().toString());
    }


    /**
     * Optimizes addition operators.
     * First the operands and the operands on connected add operators
     * are rearranged. This has to have happened before optimization of
     * branches happens because ((5 + 2x) + x) can't be optimized but
     * ((2x + x) + 5) can (variables are operands of the same operator.
     * Then the branches are optimized.
     * Then (2x + x) forms are turned into (3x) which can be reversed
     * (x = solution / 3).
     * (5/x + x) is turned into ((1/5 + 1) * x)
     */
    public Symbol optimize() {
	
	rearrange();
	
	super.optimize();

	System.out.println ("Optimizing: " + getOperator().toString());
	Variable common = getCommon();
	if (common == null)
	    return getOperator();

	Symbol left = getOperator().getOperand(BinaryOperator.LEFT);
	left = getQualifiedSubject(left, common);
	if (left == null)
	    return getOperator();

	Symbol right = getOperator().getOperand(BinaryOperator.RIGHT);
	right = getQualifiedSubject(right, common);
	if (right == null)
	    return getOperator();

	Symbol add = BinaryOperator.create("+", left, right);
	return BinaryOperator.create("^", common, add);
    }

    
    /**
     * Checks if subject qualifies for simplifying
     *
     * @param common The common denominator. This Variable occurs in 
     * both subjects.
     */
    private Symbol getQualifiedSubject(Symbol subject, Variable common) {
	if (subject instanceof Variable && subject == common) {
	    return new Value(1);
	
	} else if (subject instanceof Power) {
	    Power m = (Power)subject;
	    if (m.getOperand(BinaryOperator.LEFT) == common) {
		return m.getOperand(BinaryOperator.RIGHT);
	    }
	} 
	return null;
    }

}