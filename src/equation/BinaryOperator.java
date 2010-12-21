package equation;

import equation.Operator;
import equation.Value;
import equation.Symbol;
import equation.binaryoperator.*;

import java.util.LinkedList;

public abstract class BinaryOperator extends Operator {
    public static final int LEFT = 0, RIGHT = 1, OPERANDS = 2;

    private int priority;
    private Symbol[] operands;

    /**
     * OptimizerStrategy that can optimize the operator.
     */
    private OptimizerStrategy strategy;

    
    /**
     * Creates a new binary operator. 
     *
     * @param priority defines whether the operator has priority
     *    over other binary operators.
     * @param Symbol left the symbol that is the left operand.
     * @param Symbol right the symbol that is the right operand.
     */
    public BinaryOperator(int priority, Symbol left, Symbol right) {
	this.operands = new Symbol[2];
	operands[LEFT] = left;
	operands[RIGHT] = right;
	this.priority = priority;
    }

    /**
     * Creates a new binary operator that belongs with the given
     * token.
     */
    public static BinaryOperator create(String token, Symbol left,
					Symbol right) {

	if (token.equals("="))
	    return new Equals(7, left, right);
	else if (token.equals("+"))
	    return new Add(6, left, right);
	else if (token.equals( "-"))
	    return new Substract(5, left, right);
	else if (token.equals("*"))
	    return new Multiply(4, left, right);
	else if (token.equals("/"))
	    return new Divide(3, left, right);
	else if (token.equals("^"))
	    return new Power(2, left, right);
	else if (token.equals("hypot"))
	    return new Hypot(1, left, right);
	else
	    return null;
    }

    /**
     * Calculate the solution from the two given values.
     */
    public abstract float calc(float f1, float f2);
    /**
     * Calculate the value of the left operand using the solution
     * and the value of the right operand.
     */
    public abstract Symbol reverseLeft(Symbol solution);
    /**
     * Calculate the value of the right operand using the solution
     * and the value of the left operand.
     */
    public abstract Symbol reverseRight(Symbol solution);

    /**
     * @param side which operand has to be returned(LEFT or RIGHT)
     * @return if side == LEFT the left operand, if side == RIGHT
     * the right
     */
    public final Symbol getOperand(int side) {
	return this.operands[side];
    }

    public final Symbol getLeft() {
	return getOperand(LEFT);
    }
    public final Symbol getRight() {
	return getOperand(RIGHT);
    }
	
    /**
     * Sets one of the operands
     */
    public void setOperand(final Symbol symbol, int side) {
	this.operands[side] = symbol;
    }

    public void setLeft(final Symbol symbol) {
	setOperand(symbol, LEFT);
    }
    public void setRight(final Symbol symbol) {
	setOperand(symbol, RIGHT);
    }

    /**
     * Get the value of one of the operands.
     */
    public final Value getValue(int side) {
	Value v = getOperand(side).getValue();
	if (v != null)
	    setOperand(v, side);
	return v;
    }

    public int getPriority() {
	return priority;
    }
    public void setPriority(int priority) {
	this.priority = priority;
    }

    /**
     * Checks if this operator has priority over the given one.
     *
     * @param op the operator to check against
     * @return true if op.getPriority() is less or equal to
     *     getPriority()
     */
    public boolean hasPriority(BinaryOperator op) {
	return (priority <= op.getPriority());
    }

    /**
     * Calculates the value of the left and right operator.
     *
     * @return the calculated solution of the operator or
     *   null if the value of one of the operands can't be
     *   figured out.
     * @see calc()
     */
    public Value getValue() {
	Value v1 = getValue(LEFT);
	Value v2 = getValue(RIGHT);
		
	if (v1 != null && v2 != null)
	    return new Value(calc(v1.getFloat(), v2.getFloat()));
	else
	    return null;
    }

    /**
     * Try to figure out the value of one of the operands using
     * the given solution.
     * 
     * @see reverseLeft()
     * @see reverseRight()
     */
    public void solve(Symbol solution) {
	Symbol leftReversed = reverseLeft(solution);
	Symbol left = getOperand(LEFT);
	if (left instanceof Operator)
	    ((Operator)left).solve(leftReversed);
	else if (left instanceof Variable)
	    ((Variable)left).setValue(leftReversed);

	Symbol rightReversed = reverseRight(solution);
	Symbol right = getOperand(RIGHT);
	if (right instanceof Operator)
	    ((Operator)right).solve(rightReversed);
	else if (right instanceof Variable)
	    ((Variable)right).setValue(rightReversed);
    }

    
    /**
     * Travels down the given branch and collects Variable instances
     * that are found.
     */
    public void getVariables(LinkedList<Variable> variables, int side) {
	if (getOperand(side) instanceof Variable) {
	    if (!variables.contains(getOperand(side)))
		variables.add((Variable)getOperand(side));
	} else if (getOperand(side) instanceof Operator) {
	    ((Operator)getOperand(side)).getVariables(variables);
	}
    }

    /**
     * Look for variables in both branches.
     */
    public void getVariables(LinkedList<Variable> variables) {
	for (int i = 0; i < OPERANDS; i++) {
	    getVariables(variables, i);
	}	
    }

    public void setOptimizerStrategy(OptimizerStrategy strategy) {
	this.strategy = strategy;
    }

    public OptimizerStrategy getOptimizerStrategy() {
	return strategy;
    }

    public Symbol optimize() {
	if (strategy != null)
	    return strategy.optimize();
	else
	    return null;
    }
    
    public String toString(String op) {
	String[] str = new String[OPERANDS];
	
	for (int i = 0; i < OPERANDS; i++) {
	    if (getOperand(i) != null)
		str[i] = getOperand(i).toString();
	    else
		str[i] = "null";
	}

	return "(" + str[LEFT] + " " + op + " " +
	    str[RIGHT] + ")";
    }
}