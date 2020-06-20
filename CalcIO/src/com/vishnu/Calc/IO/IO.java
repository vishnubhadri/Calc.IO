package com.vishnu.Calc.IO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.javia.arity.*;

/**
 * Class designed for Single manipulation
 *
 * @author Vishnu
 * @version 4
 * @see XmlData
 * @see Notepad
 * @see org.javia.arity
 */
public class IO {

    private String result = "", formula, Processedformula;
    private String[] datas, privatedatas;
    private int _operant = 0, _operator = 0;
    XmlData x;

    /**
     * This is the main parse function to perform the all datas it have some
     * Note:It is main to perform other operations
     *
     * @see #addConstantValues()
     * @see #getRHS(java.lang.String)
     * @param arg1 as String
     */
    private String constData = "";

    public void parseString(String arg1) {
        x = new XmlData();
        formula = arg1;
        Processedformula = getRHS(arg1);
        //Spliting as substrings
        String whole[] = Processedformula.split("\\+|\\-|\\*|\\/|\\%|\\^|\\!|\\√");
        String temp = "", tempfinal = "";
        for (String string : whole) {
            if (!(isNumber(result))) {
                if (!(x.isXMLPresent(string))) {
                    temp = temp + string + "~";
                    x.putXMLData(string, string);
                    temp = temp.trim();
                }
            }
        }
        //Removing space and splitting
        privatedatas = temp.split("~");
        for (int i = 0; i < privatedatas.length; i++) {
            if (!(new MathConstants().IsMathConstants(privatedatas[i]))) {
                tempfinal = tempfinal + privatedatas[i] + "~";
                tempfinal = tempfinal.trim();
            } else {
                constData = constData + privatedatas[i] + "~";
                constData = constData.trim();
            }
        }
        //Replacing ( and )
        tempfinal = tempfinal.replace("(", "");
        tempfinal = tempfinal.replace(")", "");
        String ff = "";
        XmlData tt = new XmlData();
        for (String string : tempfinal.split("~")) {
            string = string.trim();
            if (!(tt.isXMLPresent(string))) {
                if (!isNumber(string)) {
                    ff = ff + string + "~";
                    tt.putXMLData(string, string);
                }
            }
        }
        datas = ff.split("~");
        //On perm and comb function
        repcount = countOperant();
    }
    private int repcount;

    /**
     * It return the count of the operator Starts with 0
     *
     * @throws NullPointerException
     * @return Count operator
     */
    public int countOperant() {
        return datas.length;
    }

    /**
     * It return the operator in array
     *
     * @throws NullPointerException #NullPointerException
     */
    public String[] getOperant() {
        return datas;
    }

    @Deprecated
    /**
     * It return the count of the operator.
     *
     * @throws #NullPointerException
     */
    public String getOperantAt(int arg1) {
        return datas[arg1];
    }

    /**
     * Evaluate the inputs and perform the given mathamatical operations
     *
     * @throws NumberFormatException
     * @throws org.javia.arity.ArityException
     * @throws org.javia.arity.SyntaxException
     */
    public String result() throws Exception {
        modifyDatasAsXML();
        Symbols s = new Symbols();
        double ret = s.eval(Processedformula.trim());
        if (isLHS(formula)) {
            return getLHS(formula) + "=" + ret + "";
        }
        return +ret + "";
    }

    /**
     * Change formula based on the given inputs
     */
    private void modifyDatasAsXML() {
        for (String string : datas) {
            if (x.isXMLPresent(string)) {
                Processedformula = Processedformula.replace(string, x.getXMLData(string));
            }
        }
        if (constData.contains("~")) {
            modifyConstData();
        }
    }

    private void modifyConstData() {
        String temp[] = constData.split("~");
        for (String string : temp) {
            String[] values = string.split("\\(|\\)");
            if (x.isXMLPresent(values[1])) {
                Processedformula = Processedformula + getMathResult(values[0], x.getXMLData(values[1]));
            } else {
                Processedformula = Processedformula + getMathResult(values[0], values[1]);
            }
        }
    }

    /**
     * It is used to change the variables to values
     *
     * @param variable is the value to modify the formula
     * @param value is the value to replace formula
     */
    public void setValues(String variable, String value) {
        x.putXMLData(variable, value);
    }

    /**
     * Return the Current modify formula
     */
    public String printFormula() {
        return Processedformula;
    }

    /**
     *
     * @return original formula
     */
    String printOriginalFormula() {
        return formula;
    }

    /**
     *
     * @return No idea WTF is this
     */
    private String getShortString(String arg1) {
        String temp[] = arg1.split("=");
        int prev = 100;
        String shortformula = "";
        for (String cur : temp) {
            if (cur.length() < prev) {
                prev = cur.length();
                shortformula = cur;
            }
        }
        return shortformula;
    }

    /**
     *
     * @return LHS of the equation
     */
    private String getRHS(String arg1) {
        String temp[] = arg1.split("=");
        return temp[temp.length - 1];
    }

    /**
     *
     * @return RHS of the equation
     */
    private String getLHS(String arg1) {
        String temp[] = arg1.split("=");
        return temp[0];
    }

    /**
     * Check weather given equ is LHS
     */
    private boolean isLHS(String arg1) {
        String temp[] = arg1.split("=");
        if (temp.length < 2) {
            return false;
        }
        return true;
    }

    /**
     * Check the given char is operatior Identify + / * - ^ %
     */
    public boolean isOperator(String arg1) {
        return arg1.contains("+") || arg1.contains("-") || arg1.contains("/") || arg1.contains("*") || arg1.contains("%") || arg1.contains("^") || arg1.contains("!") || arg1.contains("√");
    }

    public boolean isNumber(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private String getMathResult(String function, String value) {
        switch (function.toLowerCase()) {
            case "sin":
                return Math.sin(Double.parseDouble(value)) + "";
            case "cos":
                return Math.cos(Double.parseDouble(value)) + "";
            case "tan":
                return Math.tan(Double.parseDouble(value)) + "";
            case "cot":
                return (1 / Math.sin(Double.parseDouble(value))) + "";
            case "sec":
                return (1 / Math.cos(Double.parseDouble(value))) + "";
            case "cosec":
                return (1 / Math.tan(Double.parseDouble(value))) + "";
            case "log":
                return Math.log(Double.parseDouble(value)) + "";
            default:
                return null;
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize(); //To change body of generated methods, choose Tools | Templates.
        constData = "";
        result = "";
        formula = "";
        Processedformula = "";

        datas = null;
        privatedatas = null;
        _operant = 0;
        _operator = 0;
    }
}
