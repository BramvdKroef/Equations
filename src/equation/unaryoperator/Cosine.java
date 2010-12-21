package equation.unaryoperator;

import equation.UnaryOperator;
import equation.Symbol;

public class Cosine extends UnaryOperator {

    public Cosine(Symbol subject) {
	super(subject);
    }
    
    public float calc(float value) {
	return (float)Math.cos(value);
    }

    public Symbol reverse (Symbol value) {
	return create("acos", value);
    }

    public String toString () {
	return super.toString("cos");
    }
}