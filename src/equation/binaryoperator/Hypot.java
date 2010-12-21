package equation.binaryoperator;

import equation.BinaryOperator;
import equation.UnaryOperator;
import equation.Symbol;
import equation.Value;

public class Hypot extends BinaryOperator {
    public Hypot(int priority, Symbol left, Symbol right) {
	super(priority, left, right);
    }
		 
    public float calc(float left, float right) {
	return (float)Math.hypot(left, right);
    }
    public Symbol reverseLeft(Symbol solution) {
	Symbol s = create("-",
			  create("^", solution, new Value(2)),
			  create("^", getOperand(RIGHT), new Value(2)));
	return UnaryOperator.create("sqrt", s);
    }
    public Symbol reverseRight(Symbol solution) {
	Symbol s = create("-",
			  create("^", solution, new Value(2)),
			  create("^", getOperand(LEFT), new Value(2)));
	return UnaryOperator.create("sqrt", s);
    }

    public String toString() {
	return super.toString(" hypot ");
    }
}