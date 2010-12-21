package equation.unaryoperator;

import equation.UnaryOperator;
import equation.Symbol;

public class Sine extends UnaryOperator {

    public Sine(Symbol subject) {
	super(subject);
    }

    public float calc(float value) {
	return (float)Math.sin(value);
    }

    public Symbol reverse (Symbol value) {
	return create("asin", value);
    }

    public String toString () {
	return super.toString("sin");
    }
}