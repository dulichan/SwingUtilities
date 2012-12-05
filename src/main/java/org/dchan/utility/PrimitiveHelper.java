package org.dchan.utility;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.apache.commons.lang3.StringUtils;

/**
 * Provides some important tools to handle utility work.
 * 
 * @author dulithar
 * 
 */
public class PrimitiveHelper {
	static DecimalFormat format = new DecimalFormat("0.00");

	public static String getFormattedDouble(Double d) {
		return format.format(d);
	}

	public static String getFormattedDouble(BigDecimal d) {
		return format.format(d);
	}

	public static boolean isCreditCardValid(String cardNumber) {
		String remove = StringUtils.remove(cardNumber, '-');
		String digitsOnly = remove;

		int sum = 0;
		int digit = 0;
		int addend = 0;
		boolean timesTwo = false;
		for (int i = digitsOnly.length() - 1; i >= 0; i--) {
			digit = Integer.parseInt(digitsOnly.substring(i, i + 1));
			if (timesTwo) {
				addend = digit * 2;
				if (addend > 9) {
					addend -= 9;
				}
			} else {
				addend = digit;
			}
			sum += addend;
			timesTwo = !timesTwo;
		}

		int modulus = sum % 10;
		return modulus == 0;

	}

	public static String getFormattedDouble(String text) {
		return getFormattedDouble(Double.valueOf(text));
	}

}
