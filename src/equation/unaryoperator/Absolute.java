package equation.unaryoperator;

import equation.UnaryOperator;
import equation.Symbol;
    
public class Absolute extends UnaryOperator {

    public Absolute (Symbol subject) {
	super(subject);
    }

    public float calc(float value) {
	return Math.abs(value);
    }

    public Symbol reverse(Symbol value) {
	return value;
    }

    public String toString () {
	return super.toString("abs");
    }
}