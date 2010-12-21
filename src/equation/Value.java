package equation;

import equation.Symbol;

public class Value implements Symbol {
    private float value;
    
    public Value(float value) {
	this.value = value;
    }

    public Value getValue() {
	return this;
    }

    public float getFloat() {
	return this.value;
    }

    public String toString() {
	return "" + value;
    }
}