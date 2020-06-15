package com.paytm.insider.hackernews.util;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class UnixTimeStampToYear {

	public static Integer getAgeFromUnixTimeStamp(Long unixTime) {
		return LocalDateTime.now().getYear() - LocalDateTime.ofEpochSecond(unixTime, 0, ZonedDateTime.now().getOffset()).getYear();
	}
}
