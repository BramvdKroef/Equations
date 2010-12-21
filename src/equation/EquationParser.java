package equation;


import equation.unaryoperator.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.io.IOException;
import java.text.ParseException;


public class EquationParser {

    private HashMap<String, Variable> variables;

    public EquationParser() {
	variables = new HashMap<String, Variable>();
    }

    
    public LinkedList<Symbol> parse(String equation)
	throws IOException, ParseException {
	StreamTokenizer f = new StreamTokenizer(new StringReader(equation));
	f.ordinaryChar('/');
	f.ordinaryChar('-');
	f.nextToken();
	
	LinkedList<Symbol> list = new LinkedList<Symbol>();
	Symbol s;
	while ((s = parseBinary(f)) != null) {
	    if (f.ttype == f.TT_EOL)
		f.nextToken();
	    if (s != null)
		list.add(s);
	}
	return list;
    }

    public HashMap<String, Variable> getVariables() {
	return variables;
    }

    public void clearVariables() {
	variables.clear();
    }

    /**
     * binary = unary [binary-operator binary]
     */
    private Symbol parseBinary (StreamTokenizer f) throws
	IOException, ParseException {
	Symbol s1 = parseUnary(f);
	if (s1 == null)
	    return null;

	String token;
	if (f.ttype == f.TT_WORD)
	    token = f.sval;
	else
	    token = new Character((char)f.ttype).toString();
	
	BinaryOperator op = BinaryOperator.create(token, s1, null);
	if (op == null)
	    return s1;

	f.nextToken();
	
	Symbol s2 = parseBinary(f);
	if (s2 == null)
	    throw new ParseException("Operator " + token +
				     " needs two operands", f.lineno());
	
	if (s2 instanceof BinaryOperator &&
	    op.hasPriority((BinaryOperator)s2)) {
	    BinaryOperator node = (BinaryOperator)s2;
	    while (node.getLeft() instanceof BinaryOperator &&
		   op.hasPriority((BinaryOperator)node.getLeft())) {
		node = (BinaryOperator)node.getLeft();
	    }
	    op.setRight(node.getLeft());
	    node.setLeft(op);
	    
	    return s2;
	} else {
	    op.setRight(s2);
	    return op;
	}

    }

    /**
     * unary = [unary-operator] value
     */
    private Symbol parseUnary (StreamTokenizer f) throws
	IOException, ParseException {
	//{"-", "sqrt", "sin", "cos", "tan", "abs", "toDegrees", "toRadians"},	
	String token;
	if (f.ttype == f.TT_WORD)
	    token = f.sval;
	else
	    token = new Character((char)f.ttype).toString();

	UnaryOperator op = UnaryOperator.create(token, null);

	if (op == null)
	    return getValue(f);
	
	f.nextToken();
	Symbol s = getValue(f);
	if (s == null)
	    throw new ParseException(op.toString() + " needs a subject.",
				     f.lineno());
	op.setSubject(s);
	return op;
    }

    /**
     * parenthesis = '(' binary ')'
     */
    private Symbol parseParenthesis(StreamTokenizer f)
	throws IOException, ParseException {
	if (f.ttype == '(') {
	    f.nextToken();
	    
	    Symbol s = parseBinary(f);
	    if (f.ttype != ')')
		throw new ParseException("Unclosed parenthesis", f.lineno());
	    f.nextToken();
		
	    if (s instanceof BinaryOperator)
		((BinaryOperator)s).setPriority(0);
	    return s;
	}
	return null;
    }

    /**
     * value = parenthesis | float | variable | 'pi' | 'rnd' | 'e'
     */
    private Symbol getValue(StreamTokenizer f)
	throws IOException, ParseException {
	Symbol s = null;

	s = parseParenthesis(f);
	if (s != null)
	    return s;
	else if (f.ttype == StreamTokenizer.TT_NUMBER) {
	    s = new Value((float)f.nval);
	} else if (f.ttype == StreamTokenizer.TT_WORD) {
	    if (f.sval.equals("pi"))
		s = new Value((float)Math.PI);
	    else if (f.sval.equals("rnd"))
		s = new Value((float)Math.random());
	    else if (f.sval.equals("e"))
		s = new Value((float)Math.E);
	    else {
		s = variables.get(f.sval);
		if (s == null) {
		    s = new Variable(f.sval);
		    variables.put(f.sval, (Variable)s);
		}
	    }
	} else {
	    return null;
	}

	f.nextToken();
	return s;
    }

}