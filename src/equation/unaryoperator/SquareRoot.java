package equation.unaryoperator;

import equation.UnaryOperator;
import equation.BinaryOperator;
import equation.Symbol;
import equation.Value;

public class SquareRoot extends UnaryOperator {

    public SquareRoot (Symbol subject) {
	super(subject);
    }
    
    public float calc(float value) {
	return (float)Math.sqrt(value);
    }

    public Symbol reverse (Symbol value) {
	return BinaryOperator.create("^", value, new Value(2));
    }

    public String toString () {
	return super.toString("sqrt");
    }
}