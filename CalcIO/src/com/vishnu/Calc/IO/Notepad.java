/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vishnu.Calc.IO;

/**
 * Class designed for multiple manipulation. Specially for notepad mode in
 * android connected with AI
 *
 * @version 4
 * @author Vishnu
 * @see AI
 * @see XmlData
 * @see org.javia.arity
 */
public class Notepad {

    private String Lines[];
    private String _originalformula = "", parsedformula = "", variableparsedformula = "";
    private String variables[];
    private int varCount = 0, linecount = 1;
    public XmlData x = new XmlData();

    /**
     * Empty constructor
     *
     * @see #Notepad(java.lang.String)
     */
    public Notepad() {
    }

    /**
     * Constructor that perform operation
     *
     * @param arg1 for enter the formula
     * @see #parseString(java.lang.String)
     */
    public Notepad(String arg1) {
        parseString(arg1);
    }

    /**
     * Get the inputs that give in the constructor and Split as variables.
     *
     * @see #parseString(java.lang.String)
     */
    public void parseString() {
        parseString(_originalformula);
    }

    /**
     * Manipulate and split into variables
     *
     * @param arg1 as the input string
     */
    public void parseString(String arg1) {
        _originalformula = arg1;
        parsedformula = _originalformula;
        lines();
        for (int i = 0; i < Lines.length; i++) {
            splitVariables(Lines[i]);
        }
    }

    /**
     * Split the inputs line as array for manipulation
     *
     * @param arg1 line no to print
     * @return the string in the particlar line
     */
    public int lines(String arg1) {
        Lines = arg1.split("\n");
        return Lines.length;
    }

    /**
     * Split the inputs line as array for manipulation get the value in the
     * constructor or parseString()
     *
     * @see #parseString(java.lang.String)
     * @return the string in the particlar line
     */
    public int lines() {
        return lines(parsedformula);
    }

    private String[] valNextequal = new String[lines()];

    /**
     * Get the string at line
     *
     * @param lineno as int
     * @return the line in the value
     * @throws NullPointerException for with out using lines() or lines(arg1)
     */
    public String atLine(int lineno) {
        return Lines[lineno];
    }

    /**
     * Get the line from string
     *
     * @param lineno the line
     * @param string the sentence to split
     * @return the line at sentence
     */
    public String atLine(int lineno, String string) {
        lines(string);
        return Lines[lineno];
    }

    /**
     * CHeck the input is it formula or assignment variable and perform some
     * manupulations
     *
     * @param line as the string
     * @see #isFormula(java.lang.String)
     * @see #performFormula(java.lang.String, java.lang.String)
     * @see #performSingleFormula(java.lang.String)
     * @see #getLHS(java.lang.String)
     * @see #getRHS(java.lang.String)
     */
    public void splitVariables(String line) {
        if (isFormula(getLHS(line)) && isFormula(getRHS(line))) {
            performFormula(getLHS(line), "lhs");
            performFormula(getRHS(line), "rhs");
        } else if (isFormula(getRHS(line))) {
            performFormula(getRHS(line), "rhs");
        } else if (isFormula(getLHS(line))) {
            performFormula(getLHS(line), "lhs");
        } else if (isSingleAssignment(line)) {
            if (isFormula(getRHS(line))) {
                x.putXMLData(getLHS(line), performSingleFormula(getRHS(line)));
                x.putXMLData(performSingleFormula(getRHS(line)), performSingleFormula(getRHS(line)));
            } else if (isNumber(getRHS(line))) {
                x.putXMLData(getLHS(line), getRHS(line));
                x.putXMLData(getRHS(line), getRHS(line));
            } else {
                x.putXMLData(getLHS(line), x.getXMLData(getRHS(line)));
                x.putXMLData(getRHS(line), x.getXMLData(getRHS(line)));
            }
        } else if (isMultiAssignment(line)) {
            if (isFormula(getRHS(line))) {
                x.putXMLData(getLHSArray(line), performSingleFormula(getRHS(line)));
                x.putXMLData(performSingleFormula(getRHS(line)), performSingleFormula(getRHS(line)));
            } else {
                x.putXMLData(getLHSArray(line), getRHS(line));
                x.putXMLData(getRHS(line), getRHS(line));
            }
        }
    }

    /**
     * perform split based on previous given value
     *
     * @see #splitVariables(java.lang.String)
     */
    public void splitVariables() {
        splitVariables(_originalformula);
    }

    /**
     * Split and return the variables
     *
     * @return the variables specified
     */
    public String[] getVariable() {
        return variables;
    }

    @Deprecated
    /**
     * Return variables as string
     */
    public String getVariables() {
        String temp = "";
        for (int i = 0; i < variables.length; i++) {
            temp = temp + variables[i] + "\t";
        }
        return temp;
    }

    /**
     * Check the given value is number
     *
     * @param s get the String and manipulate
     * @return whether is true or not
     *
     */
    public boolean isNumber(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Return the original formula
     *
     * @return
     */
    public String printOriginalFormula() {
        return _originalformula;
    }

    /**
     * Check the given char is operatior Identify + / * - ^ %
     */
    public boolean isOperator(String arg1) {
        return arg1.contains("+") || arg1.contains("-") || arg1.contains("/") || arg1.contains("*") || arg1.contains("^") || arg1.contains("%") || arg1.contains("√");
    }

    /**
     * Return the LHS of the given string
     *
     * @param arg1 as the equation
     * @return the string
     * @see #getLHS(java.lang.String, java.lang.String)
     * @see #getLHSArray(java.lang.String)
     */
    private String getRHS(String arg1) {
        String temp[] = arg1.split("=");
        return temp[temp.length - 1].trim();
    }

    /**
     * Return the LHS of the given string in array
     *
     * @param arg1 as the equation
     * @return the array
     * @see #getLHS(java.lang.String, java.lang.String)
     * @see #getLHS(java.lang.String)
     */
    private String[] getLHSArray(String arg1) {
        String temp[] = arg1.split("=");
        String t[] = new String[temp.length - 1];
        for (int i = 0; i < t.length; i++) {
            t[i] = temp[i].trim();
        }
        return t;
    }

    @Deprecated
    /**
     * Split equ into lhs based on given seperator
     *
     */
    private String getLHS(String arg1, String seperator) {
        String temp[] = arg1.split("=");
        String tem = "";
        for (String string : temp) {
            tem = tem + string + seperator;
        }
        return tem.trim();
    }

    /**
     * Return the first value in Equation with removing extra space
     *
     * @param arg1
     * @return the intial value
     */
    private String getLHS(String arg1) {
        return arg1.split("=")[0].trim();
    }

    /**
     * Check whether the given equation is formula based
     *
     * @param arg1 as the equation
     * @return boolean value
     * @see #isMultiAssignment(java.lang.String)
     * @see #isSingleAssignment(java.lang.String)
     */
    public boolean isFormula(String arg1) {
        return arg1.contains("+") || arg1.contains("-") || arg1.contains("/") || arg1.contains("*") || arg1.contains("^") || arg1.contains("%") || arg1.contains("√");
    }

    /**
     * Check whether the given equation is assignment value
     *
     * @param arg1 as the equation
     * @return boolean value
     * @see #isFormula(java.lang.String)
     * @see #isMultiAssignment(java.lang.String)
     */
    public boolean isSingleAssignment(String arg1) {
        if (getLHSArray(arg1).length == 1) {
            return true;
        }
        return false;
    }

    /**
     * Check whether the given equation is assignment value for multiple
     * variables
     *
     * @param arg1 as the equation
     * @return boolean value
     * @see #isFormula(java.lang.String)
     * @see #isSingleAssignment(java.lang.String)
     */
    public boolean isMultiAssignment(String arg1) {

        if (getLHSArray(arg1).length > 1) {
            return true;
        }
        return false;
    }

    /**
     * Perform the equation and eval and set the results in the function
     *
     * @param arg1 as the equation
     * @param side as LHS or RHS
     * @see #getLHSValue()
     * @see #getRHSValue()
     * @see AI
     */
    private void performFormula(String arg1, String side) {
        System.out.println("performFormula " + arg1 + "\n" + side);
        String Eval;
        if (side.equals("lhs") || side.equals("Lhs") || side.equals("LHS")) {
            Eval = getLHS(arg1).trim();
        } else {
            Eval = getRHS(arg1).trim();
        }
        addSteps(side + ":" + Eval + "\n");
        IO ai = new IO();
        ai.parseString(Eval + "");
        for (int i = 0; i < ai.getOperant().length; i++) {
            ai.setValues(ai.getOperantAt(i), x.getXMLData(ai.getOperantAt(i)));
        }
        try {
            String temp = ai.result();
            if (side.equals("lhs") || side.equals("Lhs") || side.equals("LHS")) {
                Gl = temp;
            } else {
                Gr = temp;
            }
        } catch (Exception e) {
            addSteps(e.toString());
        }
    }

    /**
     * Assign the single assignment value
     *
     * @param arg1 as stirng
     * @return
     * @see #performFormula(java.lang.String, java.lang.String)
     */
    private String performSingleFormula(String arg1) {
        IO ai = new IO();
        ai.parseString(arg1);
        try {
            String a[] = ai.result().split("=");
            return a[a.length - 1];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String res = "", Gl, Gr, steps = "\n//Execution Starts\n";

    /**
     * Add the step by step information
     *
     * @param arg1 datas to add
     */
    private void addSteps(String arg1) {
        steps = steps + arg1 + "\n";
    }

    /**
     * return the steps that added
     *
     * @return the steps
     * @see #getSteps()
     */
    @Deprecated
    public String printSteps() {
        return steps;
    }

    /**
     * return the steps that added
     *
     * @return the steps
     *
     */
    public String getSteps() {
        return steps;
    }

    /**
     * return the steps in array format
     *
     * @return the steps
     * @see #getSteps()
     */
    public String[] getStepArray() {
        return steps.split("\n");
    }

    /**
     * Return the string ar particular line
     *
     * @param i as the lineno
     * @return the step
     * @see #getStepArray()
     * @deprecated
     */
    public String getStepat(int i) {
        return steps.split("\n")[i];
    }

    /**
     * Set the result
     *
     * @param arg
     */
    private void setResult(String arg) {
        res = arg;
    }

    /**
     * @deprecated @see #Result(java.lang.String)
     * @see #printResult()
     * @return null
     */
    public String result() {
        return res;
    }

    /**
     * Return the result with comparing data
     *
     *
     * @see #Result(java.lang.String)
     * @return Answer
     */
    public String printResult() {
        try {
            if ((!(Gl == null)) && (!(Gr == null))) {
                if (Gl.equalsIgnoreCase(Gr)) {
                    return "Therefore:" + Gl + "=" + Gr;
                }
                return Gl + "\n" + Gr;
            }
            if ((Gl == null) && (Gl == null)) {
                return "Sorry Something Wrong\nLHS:=" + Gl + "\nRHS:=" + Gr;
            } else if (Gl == null) {
                return Lines[Lines.length - 1] + " = " + Gr;
            } else if (Gl == null) {
                return Lines[Lines.length - 1] + " = " + Gr;
            } else {
                return "Sorry Something Wrong\nLHS:=" + Gl + "\nRHS:=" + Gr;
            }
        } catch (NullPointerException e) {
            return "Sorry Something Wrong:" + e.getMessage();
        }
    }

    /**
     * Return the result based on side
     *
     * @param Side LHS or RHS
     * @see #Result(java.lang.String)
     * @return null
     */
    public String result(String Side) {
        switch (Side) {
            case "Lhs":
            case "lhs":
            case "LHS":
                return Gl;
            default:
                return Gr;
        }
    }

    /**
     * Print LHS value
     *
     * @return value
     */
    public String getLHSValue() {
        return Gl;
    }

    /**
     * Print RHS value
     *
     * @return value
     */
    public String getRHSValue() {
        return Gr;
    }

    private String arrayToString(String args[]) {
        return arrayToString(args, "");
    }

    private String arrayToString(String arg1[], String arg2[]) {
        String temp = "";
        for (int i = 0; i < arg1.length; i++) {
            temp = temp + arg1[i] + "     " + arg2[i] + "\n";
        }
        return temp;
    }

    private String arrayToString(String arg1[], String arg2) {
        String temp = "";
        for (int i = 0; i < arg1.length; i++) {
            temp = temp + arg1[i] + arg2;
        }
        return temp;
    }

    /**
     *
     * @param Variable
     * @return
     * @see XmlData
     */
    public String getValue(String Variable) {
        return x.getXMLData(Variable);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize(); //To change body of generated methods, choose Tools | Templates.
        Lines = null;
        _originalformula = "";
        parsedformula = "";
        variableparsedformula = "";
        variables = null;
        varCount = 0;
        linecount = 1;
    }

}
