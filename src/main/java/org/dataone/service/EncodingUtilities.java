package org.dataone.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.BitSet;


/*
 Adapted from:
 (http://www.java2s.com/Code/Java/Network-Protocol/EncodeapathasrequiredbytheURLspecification.htm)
 
 Derby - Class org.apache.derby.iapi.util.PropertyUtil

 Licensed to the Apache Software Foundation (ASF) under one or more
 contributor license agreements.  See the NOTICE file distributed with
 this work for additional information regarding copyright ownership.
 The ASF licenses this file to you under the Apache License, Version 2.0
 (the "License"); you may not use this file except in compliance with
 the License.  You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

 */

/*
 * Useful resources for background on encoding 

 http://www.java-forums.org/new-java/350-how-obtain-ascii-code-character.html
 http://stackoverflow.com/questions/1527856/how-can-i-iterate-through-the-unicode-codepoints-of-a-java-string
 http://mule1.dataone.org/ArchitectureDocs/GUIDs.html
 http://download.oracle.com/javase/1.5.0/docs/api/java/lang/Character.html#unicode
 http://www.joelonsoftware.com/articles/Unicode.html


 http://www.utf8-chartable.de/unicode-utf8-table.pl

 */

public class EncodingUtilities {


	/**
	 * Array containing the allowable characters for 'pchar' set as defined by RFC 3986 ABNF:
	 *      pchar         = unreserved / pct-encoded / sub-delims / ":" / "@" 
	 *      unreserved  = ALPHA / DIGIT / "-" / "." / "_" / "~" 
	
	 */
	
	
	private static BitSet pcharUnescapedCharacters;

	private static final char[] hexadecimal = { '0', '1', '2', '3', '4', '5', '6', '7',
											    '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	static {
		pcharUnescapedCharacters = new BitSet(256);
		int i;

		// unreserved
		//   ALPHA
		for (i = 'a'; i <= 'z'; i++)     pcharUnescapedCharacters.set(i);
		for (i = 'A'; i <= 'Z'; i++)     pcharUnescapedCharacters.set(i);
		//   DIGIT
		for (i = '0'; i <= '9'; i++)     pcharUnescapedCharacters.set(i);
		//   other
		pcharUnescapedCharacters.set('-');
		pcharUnescapedCharacters.set('_');
		pcharUnescapedCharacters.set('.');
		pcharUnescapedCharacters.set('~');

		// sub-delims
		pcharUnescapedCharacters.set('!');
		pcharUnescapedCharacters.set('$');
		pcharUnescapedCharacters.set('&');
		pcharUnescapedCharacters.set('\'');
		pcharUnescapedCharacters.set('(');
		pcharUnescapedCharacters.set(')');
		pcharUnescapedCharacters.set('*');
		pcharUnescapedCharacters.set('+');
		pcharUnescapedCharacters.set(',');
		pcharUnescapedCharacters.set(';');
		pcharUnescapedCharacters.set('=');      

		// allowable from general delimiters set
		pcharUnescapedCharacters.set(':');
		pcharUnescapedCharacters.set('@');
	}


	/**
	 * Encode a path segment as required by the URL specification (<a href="http://www.ietf.org/rfc/rfc3986.txt">
	 * RFC 3986</a>). This differs from <code>java.net.URLEncoder.encode()</code> which encodes according
	 * to the <code>x-www-form-urlencoded</code> MIME format.
	 *
	 * @param idString the identifier to encode (operating as a segment, according to the ABNF)
	 * @return the URL-safe UTF-8 encoded identifier
	 */
	public static String encodeIdentifier(String idString) {
		// replaced StringBuffer with StringBuilder, as per recommendation in javadocs, for higher performance


		int maxBytesPerChar = 10;
		StringBuilder rewrittenPathSegment = new StringBuilder(idString.length());
		ByteArrayOutputStream buf = new ByteArrayOutputStream(maxBytesPerChar);
		OutputStreamWriter writer;
		try {
			writer = new OutputStreamWriter(buf, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			// probably don't want to continue
			writer = new OutputStreamWriter(buf);
		}

		for (int i = 0; i < idString.length(); i++) {
			int c = idString.charAt(i);
			if (pcharUnescapedCharacters.get(c)) {
				rewrittenPathSegment.append((char)c);
			} else {
				// convert to external encoding (UTF-8) before hex conversion
				try {
					writer.write(c);
					writer.flush();
				} catch(IOException e) {
					buf.reset();
					continue;
				}
				byte[] ba = buf.toByteArray();
				for (int j = 0; j < ba.length; j++) {
					// Converting each byte in the buffer
					byte toEncode = ba[j];
					rewrittenPathSegment.append('%');
					int low = (toEncode & 0x0f);
					int high = ((toEncode & 0xf0) >> 4);
					rewrittenPathSegment.append(hexadecimal[high]);
					rewrittenPathSegment.append(hexadecimal[low]);
				}
				buf.reset();
			}
		}
		return rewrittenPathSegment.toString();
	}
	
	
	public static String decodeXmlDataItems(String dataString)
	{
		String decodedString;
		
		decodedString = dataString.replaceAll("&gt;",">");
		decodedString = decodedString.replaceAll("&lt;","<");
		decodedString = decodedString.replaceAll("&amp;","&");
		decodedString = decodedString.replaceAll("&apos;","'");
		decodedString = decodedString.replaceAll("&quot;","\"");
		
		return decodedString;
	}
}