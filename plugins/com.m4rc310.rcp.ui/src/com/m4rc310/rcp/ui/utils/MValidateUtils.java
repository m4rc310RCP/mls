package com.m4rc310.rcp.ui.utils;

public class MValidateUtils {

	public static int getMod11(String num) {

		int soma = 0;
		int resto = 0;
		int dv = 0;
		String[] numeros = new String[num.length() + 1];
		int multiplicador = 2;

		for (int i = num.length(); i > 0; i--) {
			if (multiplicador > 9) {
				multiplicador = 2;
				numeros[i] = String.valueOf(Integer.valueOf(num.substring(i - 1, i)) * multiplicador);
				multiplicador++;
			} else {
				numeros[i] = String.valueOf(Integer.valueOf(num.substring(i - 1, i)) * multiplicador);
				multiplicador++;
			}
		}

		for (int i = numeros.length; i > 0; i--) {
			if (numeros[i - 1] != null) {
				soma += Integer.valueOf(numeros[i - 1]);
			}
		}
		
		resto = soma % 11;
		dv = 11 - resto;
		
		if (dv > 9 ) {
			dv = 0;
		}
		return dv;
	}
	
	public static boolean validateMod11(String number) {
		number = number.replaceAll("\\D+","");
		int d0 = Integer.parseInt(number.substring(number.length()-1));
		
		int d1 = getMod11(number.substring(0, number.length()-1));
		
		return d0==d1;
	}
	
	public static void main(String[] args) {
		
		System.out.println(validateMod11("344435"));
		System.out.println(validateMod11("358673"));
		System.out.println(validateMod11("27698 7"));
		System.out.println(validateMod11("42501 0"));
		System.out.println(validateMod11("30809 9"));
		System.out.println(validateMod11("35306 0"));
		System.out.println(validateMod11("42852 3"));
		System.out.println(validateMod11("38232 9"));
		System.out.println(validateMod11("43006 4"));
		System.out.println(validateMod11("42262 2"));
		System.out.println(validateMod11("39571 4"));
		System.out.println(validateMod11("34685 3"));
		System.out.println(validateMod11("42500 1"));
		System.out.println(validateMod11("32065 0"));
		System.out.println(validateMod11("35622 0"));
		System.out.println(validateMod11("43532 5"));
		System.out.println(validateMod11("3996 9"));
		System.out.println(validateMod11("42268 1"));
		System.out.println(validateMod11("31040 9"));
		System.out.println(validateMod11("37576 4"));
		System.out.println(validateMod11("39580 3"));
		System.out.println(validateMod11("43584 8"));
		System.out.println(validateMod11("270881"));
		System.out.println(validateMod11("7066 1"));
		System.out.println(validateMod11("23117 7"));
		System.out.println(validateMod11("37758 9"));
		System.out.println(validateMod11("42504 4"));
		System.out.println(validateMod11("39582 0"));
		System.out.println(validateMod11("23148 7"));
		System.out.println(validateMod11("19933 8"));
		System.out.println(validateMod11("39076 3"));
		System.out.println(validateMod11("13273 0"));
		System.out.println(validateMod11("41240 6"));
	}

}
