package com.m4rc310.rcp.ui.phonetics;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Marcelo
 */
public class PhoneticUtils {
    public static String toString(String value){
        value = new MetaphonePtBr(value).toString();
        return value;
    }
}
