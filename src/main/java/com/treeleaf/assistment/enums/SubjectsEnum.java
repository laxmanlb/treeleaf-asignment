/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.treeleaf.assistment.enums;

/**
 *
 * @author Laxman Baniya
 */
public enum SubjectsEnum {
    ENG("English"), NEP("Nepali"), MTH("Maths"),
    SOC("Social"), Health("Health"), SCI("Science"),
    LAW("Law"), POP("Population");
    
    private String value;

    private SubjectsEnum(String value) {
        this.value = value;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }
}
