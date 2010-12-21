package equation.unaryoperator;

import equation.UnaryOperator;
import equation.Symbol;

public class Radians extends UnaryOperator {

    public Radians(Symbol subject) {
	super(subject);
    }

    public float calc(float value) {
	return (float)Math.toRadians(value);
    }

    public Symbol reverse (Symbol value) {
	return create("toDegrees", value);
    }

    public String toString () {
	return super.toString("toRadians");
    }
}