package equation;

import equation.Operator;
import java.util.LinkedList;
import equation.unaryoperator.*;

public abstract class UnaryOperator extends Operator {
    private Symbol subject;
    
    public UnaryOperator(Symbol subject) {
	this.subject = subject;
    }

    public abstract float calc(float value);
    public abstract Symbol reverse(Symbol solution);

    public Symbol getSubject() {
	return subject;
    }
    public void setSubject(Symbol s) {
	subject = s;
    }

    public Value getValue() {
	Value v;
	v = subject.getValue();
	if (v != null) {
	    setSubject(v);
	    return new Value(calc(v.getFloat()));
	} else
	    return null;
    }

    public void solve(Symbol solution) {
	Symbol reverse = reverse(solution);
	if (subject instanceof Operator)
	    ((Operator)subject).solve(reverse);
	else if (subject instanceof Variable)
	    ((Variable)subject).setValue(reverse);
    }

    public static UnaryOperator create(String token, Symbol operand) {
    	if (token == "-")
	    return new Negate(operand);
	else if (token == "sqrt")
	    return new SquareRoot(operand);
	else if (token == "sin")
	    return new Sine(operand);
	else if (token == "cos")
	    return new Cosine(operand);
	else if (token == "tan")
	    return new Tangent(operand);
	else if (token == "abs")
	    return new Absolute(operand);
	else if (token == "toDegrees")
	    return new Degrees(operand);
	else if (token == "toRadians")
	    return new Radians(operand);
	else
	    return null;
    }
    
    public void getVariables(LinkedList<Variable> variables) {
	if (getSubject() instanceof Variable) {
	    if (!variables.contains(getSubject()))
		variables.add((Variable)getSubject());
	} else if (getSubject() instanceof Operator) {
	    ((Operator)getSubject()).getVariables(variables);
	}
    }
    
    public String toString(String op) {
	return op + " " +
	    getSubject().toString();
    }

}