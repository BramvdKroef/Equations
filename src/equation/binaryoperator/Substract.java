package equation.binaryoperator;

import equation.BinaryOperator;
import equation.Symbol;

public class Substract extends BinaryOperator {

    public Substract (int priority, Symbol left, Symbol right) {
	super(priority, left, right);
    }
    
    public float calc(float f1, float f2) {
	return f1 - f2;
    }
    public Symbol reverseLeft(Symbol solution) {
	return create("+", solution, getOperand(RIGHT));
    }
    public Symbol reverseRight(Symbol solution) {
	return create("-", getOperand(LEFT), solution);
    }

    public String toString() {
	return super.toString("-");
    }
} 