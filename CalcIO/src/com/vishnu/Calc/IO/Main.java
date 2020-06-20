package com.vishnu.Calc.IO;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.Arrays;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @version 3
 * @author Vishnu
 */
public class Main {

    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        DataInputStream in = new DataInputStream(System.in);
        if (args.length > 0) {
            switch (args[0].toUpperCase()) {
                case "-M": {
                    Notepad n = new Notepad();
                    String temp = "";
                    for (int i = 1; i < args.length;
                            i++) {
                        temp = temp + args[i] + "\n";
                    }
                    System.out.println(temp);
                    n.parseString(temp);
                    System.out.println(n.getLHSValue());
                    System.out.println(n.getRHSValue());
                    break;
                }
                case "-S": {
                    IO a = new IO();
                    a.parseString(args[1]);
                    System.out.println(Arrays.toString(a.getOperant()));
                    for (int i = 0; i
                            < a.getOperant().length; i++) {
                        if (!(a.getOperant()[i].isEmpty())) {
                            System.out.println("Enter the value for " + a.getOperant()[i]);
                            String temp = in.readLine();
                            a.setValues(a.getOperant()[i], temp);
                        }
                    }
                    System.out.println(a.result());
                    break;
                }
                case "-I": {
                    String inp = "";
                    try (FileInputStream fs = new FileInputStream(args[1])) {

                        int i = 0;
                        while ((i = fs.read()) != -1) {
                            inp += ((char) i);
                        }
                        fs.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println(inp);
                    System.out.println();
                    Notepad n = new Notepad(inp);
                    System.out.println(n.getLHSValue());
                    System.out.println(n.getRHSValue());
                    break;
                }
                default: {
                    printDef();
                }
            }
        }
        /**
         * Notepad Input model
         */
        /*Notepad n = new Notepad();
         n.parseString("a=1+1\nb=11\n(a+b)^2=a^2+2*a*b+b^2");
         System.out.println("LHS:"+n.getSteps());
         System.out.println("LHS:"+n.getLHSValue());
         System.out.println("RHS:"+n.getRHSValue());*/
        /**
         * AI Input model and output retrive
         */
        /*  AI a = new AI();
         a.parseString("√(33+3)");
         System.out.println(a.getOperant().length);
         for (int i = 1; i < a.countOperant() + 1; i++) {
         if ((!(a.getOperant()[i - 1].equals(""))) && (!(a.getOperant()[i - 1].isEmpty()))) {
         System.out.println(a.getOperant()[i - 1] + "");
         String temp = in.readLine();
         a.setValues(a.getOperant()[i - 1], temp);
         }
         }
         System.out.println(a.result());
         /*XmlData x = new XmlData();
         x.putXMLData("a", "100");
         x.putXMLData("b", "200");
         x.putXMLData("c", "100");*/
        /**
         * Finally HATE U Java....
         */
    }

    static void printDef() {
        System.out.println("Calc.IO");
        System.out.println("\t-S for normal mode");
        System.out.println("\t-M for multiple mode");
        System.out.println("\t-I for File Input");
        System.out.println("\tAfter specifier the enter the equation");
        System.out.println("\t\t\tExample");
        System.out.println("\t\t\t-S a+b");
        System.out.println("\t\t\ta=100");
        System.out.println("\t\t\tb=100");
        System.out.println("\t\t\t-M a=10\n\t\t\tb=10\n\t\t\ta+b");
        System.out.println("\t\t\t-I C:/sample.txt"); 
    }
}
