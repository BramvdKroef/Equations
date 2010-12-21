package equation.unaryoperator;

import equation.UnaryOperator;
import equation.Symbol;

public class Tangent extends UnaryOperator {

    public Tangent (Symbol subject) {
	super(subject);
    }

    public float calc(float value) {
	return (float)Math.tan(value);
    }

    public Symbol reverse (Symbol value) {
	return create("atan", value);
    }

    public String toString () {
	return super.toString("tan");
    }
}