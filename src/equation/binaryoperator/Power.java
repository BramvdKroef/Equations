package equation.binaryoperator;

import equation.BinaryOperator;
import equation.Symbol;
import equation.Value;

public class Power extends BinaryOperator {

    public Power(int priority, Symbol left, Symbol right) {
	super(priority, left, right);
    }
    
    public float calc(float f1, float f2) {
	return (float)Math.pow(f1, f2);
    }
    public Symbol reverseLeft(Symbol solution) {
	return create("^", solution,
		      create("/", new Value(1), getOperand(RIGHT)));
    }
    public Symbol reverseRight(Symbol solution) {
	return create("/", solution, getOperand(LEFT));
	//return (float)Math.log(solution)/(float)Math.log(left);
    }

    public String toString() {
	return super.toString("^");
    }
}