package equation;

import equation.optimizer.OptimizerFactory;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Iterator;
import java.io.IOException;
import java.text.ParseException;

public class EquationDialog extends JDialog implements DocumentListener{
    public static void main(String[] args) {
	new EquationDialog().setVisible(true);
    }
    private JTextArea formula, variables;
	
    public EquationDialog(){
	super();
	applyLayout();
    }
    public EquationDialog(Dialog owner){
	super(owner, "Equation");
	applyLayout();
    }
    public EquationDialog(Frame owner){
	super(owner, "Equation");
	applyLayout();
    }
    public EquationDialog(Dialog owner, boolean modal){
	super(owner, "Equation", modal);
	applyLayout();
    }
    public EquationDialog(Frame owner, boolean modal){
	super(owner, "Equation", modal);
	applyLayout();
    }
    private void applyLayout(){
	setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
	getContentPane().add(new JLabel("Equations:"));
	formula = new JTextArea("5+x*2+x = 2");
	formula.getDocument().addDocumentListener(this);
	getContentPane().add(new JScrollPane(formula));
	getContentPane().add(new JLabel("Variables:"));
	variables = new JTextArea("");
	getContentPane().add(new JScrollPane(variables));
	setSize(new Dimension(300, 200));
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	calculate(formula.getText());
    }
    private void calculate(String s){
	variables.setText("");

	EquationParser parser = new EquationParser();
	try {
	    LinkedList<Symbol> symbols = parser.parse(s);
	    for (Iterator<Symbol> i = symbols.iterator(); i.hasNext();) {
		Symbol symbol = i.next();
		System.out.println(symbol.toString());
		System.out.println(symbol.getValue());
		if (symbol instanceof BinaryOperator) {
		    BinaryOperator op = (BinaryOperator)symbol;
		    OptimizerFactory.setOptimizer(op);
		    Symbol symbol_o = op.optimize();
		    System.out.println(symbol.toString());
		    System.out.println(symbol.getValue());
		}
	    }
	} catch (IOException e) {
	} catch (ParseException pe) {
	    variables.append(pe.getMessage());
	}
	HashMap<String, Variable> map = parser.getVariables();
	Iterator<String> i = map.keySet().iterator();
	while(i.hasNext()){
	    String key = i.next();
	    variables.append(key + " = "+map.get(key).valueToString() + "\n");
	}
    }
    public void changedUpdate(DocumentEvent e){
	calculate(formula.getText());
    }
    public void insertUpdate(DocumentEvent e) {
	calculate(formula.getText());
    }
    public void removeUpdate(DocumentEvent e){
	calculate(formula.getText());
    }
}
