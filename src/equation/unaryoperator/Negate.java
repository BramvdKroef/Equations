package equation.unaryoperator;

import equation.UnaryOperator;
import equation.Symbol;

public class Negate extends UnaryOperator {

    public Negate(Symbol subject) {
	super(subject);
    }

    public float calc(float value) {
	return 0 - value;
    }

    public Symbol reverse (Symbol value) {
	return create("-", value);
    }

    public String toString () {
	return super.toString("-");
    }
}