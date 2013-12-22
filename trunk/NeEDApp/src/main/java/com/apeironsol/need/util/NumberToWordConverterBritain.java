package com.apeironsol.need.util;

/**
 * @author pradeep
 * 
 */
public class NumberToWordConverterBritain implements NumberToWordConverter {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String convertNumberToWord(final long number) {
		int reminder = 0;
		int leftOverNumber = 0;
		if (number >= 0 && number < 100) {
			if (UniqueWords.getUniqueWord((int) number) != null) {
				return UniqueWords.getUniqueWord((int) number);
			} else {
				reminder = (int) number % 10;
				leftOverNumber = (int) (number - reminder);
				return this.convertNumberToWord(leftOverNumber) + " " + (reminder > 0 ? this.convertNumberToWord(reminder) : "");
			}
		} else if (number > 99 && number < 1000) {
			reminder = (int) number % 100;
			leftOverNumber = (int) ((number - reminder) / 100);
			return this.convertNumberToWord(leftOverNumber) + this.appendAndToWordIfRequired(reminder, " Hundred ")
					+ (reminder > 0 ? this.convertNumberToWord(reminder) : "");
		} else if (number > 999 && number < 1000000) {
			reminder = (int) number % 1000;
			leftOverNumber = (int) ((number - reminder) / 1000);
			return this.convertNumberToWord(leftOverNumber) + this.appendAndToWordIfRequired(reminder, " Thousand ")
					+ (reminder > 0 ? this.convertNumberToWord(reminder) : "");
		} else if (number > 999999 && number < 1000000000) {
			reminder = (int) number % 1000000;
			leftOverNumber = (int) ((number - reminder) / 1000000);
			return this.convertNumberToWord(leftOverNumber) + this.appendAndToWordIfRequired(reminder, " Million ")
					+ (reminder > 0 ? this.convertNumberToWord(reminder) : "");
		}

		return "";
	}

	/**
	 * Append and to word if required. Following are rules for adding and always
	 * 1) insert AND after the word hundred, before any tens or units. 2)A
	 * general rule would be: always insert AND after any higher-power word, if
	 * the next number is tens or units only. A higher-power word in this
	 * context is any of: million, thousand or hundred
	 * 
	 * @param reminder
	 *            reminder.
	 * @param word
	 *            the word to which and might be added.
	 * @return return the word with and added if required.
	 */
	private String appendAndToWordIfRequired(final int reminder, final String word) {
		return reminder < 100 && reminder > 0 ? word + "And " : word;
	}

}
