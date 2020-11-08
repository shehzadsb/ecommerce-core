package com.rapid.utils;

import java.util.regex.Pattern;


import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class ProfileValidator {
			
			
			static final Pattern EMAIL_PATTERN = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
					+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
			
			

			
			public static boolean validateEmailAddress(String email) {
				return EMAIL_PATTERN.matcher(email).matches();
				
			}
}
