package equation.optimizer;

import equation.Symbol;
import equation.BinaryOperator;
import equation.binaryoperator.Add;
import equation.binaryoperator.Multiply;

public class OptimizerFactory {

    public static void setOptimizer(BinaryOperator op) {
	if (op instanceof Add) {
	    op.setOptimizerStrategy(new AddOptimizer((Add)op));
	} else if (op instanceof Multiply) {
	    op.setOptimizerStrategy(new MultiplyOptimizer((Multiply)op));

	} else
	    op.setOptimizerStrategy(new BinaryOptimizer(op));

	for (int i = 0; i < BinaryOperator.OPERANDS; i++) {
	    Symbol operand = op.getOperand(i);
	    if (operand instanceof BinaryOperator)
		setOptimizer((BinaryOperator)operand);
	}
    }
}