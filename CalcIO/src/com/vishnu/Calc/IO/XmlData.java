/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vishnu.Calc.IO;

/**
 * Easily manipulate the XML by the functions
 *
 * @author Vishnu
 * @version 1.0
 *
 */
public class XmlData {

    private String xmlDatas = "";
    private String xmlVariables = "";

    /**
     * Used to insert the value
     *
     * @param variable the value to set the value
     * @param value the value for value
     *
     */
    public void putXMLData(String variable, String value) {
        xmlDatas = xmlDatas + "<" + variable + ">" + value + "</" + variable + ">" + "\n";
        xmlVariables = xmlVariables + variable + "~";
    }

    /**
     * Used to insert the value for multiple variable
     *
     * @param variable the value to set the value in array
     * @param value the value for value
     *
     */
    public void putXMLData(String[] variable, String value) {
        for (String variable1 : variable) {
            putXMLData(variable1, value);
        }
    }

    /**
     * used to get the particular data in the element
     *
     * @param variable get the data of variable
     * @return the value as string
     */
    public String getXMLData(String variable) {
        if (xmlDatas.contains("<" + variable + ">")) {
            return xmlDatas.substring(xmlDatas.lastIndexOf("<" + variable + ">") + variable.length() + 2, xmlDatas.lastIndexOf("</" + variable + ">"));
        }
        return "";
    }

    /**
     * check whether the data is present
     *
     * @param variable is the element name to find
     * @return isPresent or not
     */
    public boolean isXMLPresent(String variable) {
        if (xmlDatas.contains("<" + variable + ">")) {
            return true;
        }
        return false;
    }

    /**
     *
     * @return the XML datas
     */
    public String printXML() {
        return xmlDatas.trim();
    }

    /**
     *
     * @return the XMl elements
     */
    public String[] getXMLVariables() {
        return xmlVariables.split("~");
    }

    /**
     *
     * @return count the XML element number
     */
    public int getXMLCount() {
        return xmlVariables.split("~").length;
    }

    /**
     * Returns the value present in the elements
     *
     * @param value the input to get elements
     * @return the elements
     *
     */
    public String[] getValuesInElement(String value) {
        String values[] = xmlDatas.split("\n"), temps = "";
        for (int i = 0; i < values.length; i++) {
            if (values[i].contains(">" + value + "</")) {
                temps = temps + values[i] + "~";
            }
        }
        return temps.split("~");
    }

    /**
     * Returns the count of the value present in the elements
     *
     * @param value the input to get elements
     * @return the elements count
     *
     */
    public int getValuesInElementCount(String value) {
        return getValuesInElement(value).length;
    }

}
