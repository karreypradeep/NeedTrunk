package com.apeironsol.need.util;

/**
 * @author pradeep
 * 
 */
public class NumberWordCountryFactory {

	/**
	 * Returns the number to word converter based on input type.
	 * 
	 * @param numberWordCountry
	 *            country.
	 * @return the number to word converter based on input type.
	 */
	public static NumberToWordConverter getNumberInWordInstanceFor(final Country numberWordCountry) {
		final NumberToWordConverter numberInWord;
		if (Country.BRITAIN.equals(numberWordCountry)) {
			numberInWord = new NumberToWordConverterBritain();
		} else if (Country.INDIA.equals(numberWordCountry)) {
			numberInWord = new NumberToWordConverterIndia();
		} else {
			numberInWord = new NumberToWordConverterBritain();
		}
		return numberInWord;
	}
}
