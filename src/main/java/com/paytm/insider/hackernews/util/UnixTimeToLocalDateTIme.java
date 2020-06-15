package com.paytm.insider.hackernews.util;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class UnixTimeToLocalDateTIme {

	public static LocalDateTime getLocalDateTime(Long unixTimeStamp) {
		return LocalDateTime.ofEpochSecond(unixTimeStamp, 0, ZonedDateTime.now().getOffset());
	}
}
