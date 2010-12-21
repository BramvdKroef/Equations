package equation.binaryoperator;

import equation.BinaryOperator;
import equation.Variable;
import equation.Operator;
import equation.Symbol;
import equation.Value;

public class Equals extends BinaryOperator {

    public Equals(int priority, Symbol left, Symbol right) {
	super(priority, left, right);
    }

    public float calc(float f1, float f2) {
	return 0;
    }
    public Symbol reverseLeft(Symbol solution) {
	return new Value(0);
    }
    public Symbol reverseRight(Symbol solution) {
	return new Value(0);
    }

    public Value getValue() throws ArithmeticException {
	Value v1 = getValue(LEFT);
	Value v2 = getValue(RIGHT);

	if (v1 != null && v2 != null) {
	    if (v1.getFloat() != v2.getFloat())
		throw new ArithmeticException("Not a valid equasion.");
	    else
		return v1;
	} else {
	    Symbol s2 = getRight();
	    if (s2 instanceof Operator)
		((Operator)s2).solve(getLeft());
	    else if (s2 instanceof Variable)
		((Variable)s2).setValue(getLeft());
	
	    Symbol s1 = getLeft();
	    if (s1 instanceof Operator)
		((Operator)s1).solve(getRight());
	    else if (s1 instanceof Variable)
		((Variable)s1).setValue(getRight());
	}
	if (v1 != null)
	    return v1;
	else if (v2 != null)
	    return v2;
	else
	    return null;
    }
    
    public void solve(Symbol solution) {
	for (int i=0; i < OPERANDS; i++) {
	    Value value = getValue(i);

	    if (value == null) {
		Symbol s = getLeft();
		if (s instanceof Operator)
		    ((Operator)s).solve(solution);
		else if (s instanceof Variable)
		    ((Variable)s).setValue(solution);
	    }
	}

    }

    public String toString() {
	return super.toString("=");
    }
}