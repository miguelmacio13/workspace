package com.tem.aspire;

/**
 * TODO: Document me!
 *
 * @author miguel.macias
 *
 */
public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String perro = "miguel 5";
        Boolean gato = false;
        
        for(Character c : perro.toCharArray()){
            if(Character.isDigit(c)){
                gato = true;
                break;
            }
        }      
        System.out.println(gato);
    }

}
