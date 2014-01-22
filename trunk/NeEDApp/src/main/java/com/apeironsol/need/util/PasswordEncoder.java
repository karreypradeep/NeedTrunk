/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Random;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class PasswordEncoder {

	private static Random	rand	= new Random(new Date().getTime());

	public static String encrypt(final String str) {

		BASE64Encoder encoder = new BASE64Encoder();

		byte[] salt = new byte[8];

		rand.nextBytes(salt);

		return encoder.encode(salt) + encoder.encode(str.getBytes(Charset.forName("UTF-8")));
	}

	public static String decrypt(final String encstr) {

		if (encstr.length() > 12) {

			String cipher = encstr.substring(12);

			BASE64Decoder decoder = new BASE64Decoder();

			try {

				return new String(decoder.decodeBuffer(cipher), Charset.forName("UTF-8"));

			} catch (IOException e) {

				// throw new InvalidImplementationException(

				// Fail

			}

		}

		return null;
	}
}
