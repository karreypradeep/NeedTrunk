/**
 * 
 */
package com.apeironsol.need.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author pradeep
 * 
 */
public class UniqueWords {

	private static final String					ZERO			= "Zero";

	private static final String					ONE				= "One";

	private static final String					TWO				= "Two";

	private static final String					THREE			= "Three";

	private static final String					FOUR			= "Four";

	private static final String					FIVE			= "Five";

	private static final String					SIX				= "Six";

	private static final String					SEVEN			= "Seven";

	private static final String					EIGHT			= "Eight";

	private static final String					NINE			= "Nine";

	private static final String					TEN				= "Ten";

	private static final String					ELEVEN			= "Eleven";

	private static final String					TWELVE			= "Twelve";

	private static final String					THIRTEEN		= "Thirteen";

	private static final String					FOURTEEN		= "Fourteen";

	private static final String					FIFTEEN			= "Fifteen";

	private static final String					SIXTEEN			= "Sixteen";

	private static final String					SEVENTEEN		= "Seventeen";

	private static final String					EIGHTEEN		= "Eighteen";

	private static final String					NINETEEN		= "Nineteen";

	private static final String					TWENTY			= "Twenty";

	private static final String					THIRTY			= "Thirty";

	private static final String					FOURTY			= "Fourty";

	private static final String					FIFTY			= "Fifty";

	private static final String					SIXTY			= "Sixty";

	private static final String					SEVENTY			= "Seventy";

	private static final String					EIGHTY			= "Eighty";

	private static final String					NINETY			= "Ninety";

	private static final Map<Integer, String>	uniqueNumbers	= new HashMap<Integer, String>();

	static {
		uniqueNumbers.put(0, ZERO);
		uniqueNumbers.put(1, ONE);
		uniqueNumbers.put(2, TWO);
		uniqueNumbers.put(3, THREE);
		uniqueNumbers.put(4, FOUR);
		uniqueNumbers.put(5, FIVE);
		uniqueNumbers.put(6, SIX);
		uniqueNumbers.put(7, SEVEN);
		uniqueNumbers.put(8, EIGHT);
		uniqueNumbers.put(9, NINE);
		uniqueNumbers.put(10, TEN);
		uniqueNumbers.put(11, ELEVEN);
		uniqueNumbers.put(12, TWELVE);
		uniqueNumbers.put(13, THIRTEEN);
		uniqueNumbers.put(14, FOURTEEN);
		uniqueNumbers.put(15, FIFTEEN);
		uniqueNumbers.put(16, SIXTEEN);
		uniqueNumbers.put(17, SEVENTEEN);
		uniqueNumbers.put(18, EIGHTEEN);
		uniqueNumbers.put(19, NINETEEN);
		uniqueNumbers.put(20, TWENTY);
		uniqueNumbers.put(30, THIRTY);
		uniqueNumbers.put(40, FOURTY);
		uniqueNumbers.put(50, FIFTY);
		uniqueNumbers.put(60, SIXTY);
		uniqueNumbers.put(70, SEVENTY);
		uniqueNumbers.put(80, EIGHTY);
		uniqueNumbers.put(90, NINETY);
	}

	/**
	 * Returns unique words.
	 * 
	 * @param number
	 *            unique number.
	 * @return unique words.
	 */
	public static String getUniqueWord(final int number) {
		return uniqueNumbers.get(number);
	}

}
