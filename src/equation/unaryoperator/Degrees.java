package equation.unaryoperator;

import equation.UnaryOperator;
import equation.Symbol;

public class Degrees extends UnaryOperator {

    public Degrees(Symbol subject) {
	super(subject);
    }

    public float calc(float value) {
	return (float)Math.toDegrees(value);
    }

    public Symbol reverse (Symbol value) {
	return create("toRadians", value);
    }

    public String toString () {
	return super.toString("toDegrees");
    }
}