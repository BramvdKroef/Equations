package equation;

import equation.Symbol;
import equation.Value;

public class Variable implements Symbol {
    private Symbol value = null;
    private String name;
    
    public Variable (String name) {
	this.name = name;
    }

    public void setValue(Symbol value) {
	this.value = value;
    }

    public Value getValue () {
	if (value == null)
	    return null;
	else if (value instanceof Value)
	    return (Value)value;
	else
	    // don't return value or you get infinite loops
	    return null;
    }

    public String toString() {
	return name;
    }

    /**
     * Returns a string representing the value the variable is equal to.
     *
     * @return null if the value was never set, a number if the
     * equasion could be solved, or a string representation of the equasion.
     * 
     */
    public String valueToString() {
	if (value == null)
	    return "null";
	else {
            Value val = value.getValue();
            if (val == null)
                return value.toString();
            else
                return val.toString();
        }
    }

}