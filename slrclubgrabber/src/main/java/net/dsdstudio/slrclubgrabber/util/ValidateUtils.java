package net.dsdstudio.slrclubgrabber.util;

import org.apache.commons.lang.StringUtils;

public class ValidateUtils {

	public static boolean isEmpty(Object o) {
		if (o == null)
			return true;
		if ("".equals(o.toString()))
			return true;
		return false;
	}

	public static void throwIfNull(Object o) {
		if (o == null)
			throw new IllegalArgumentException("잘못된 인자입니다 => " + o);
	}

	public static void throwIfNullOrEmpty(Object o) {
		if (o == null)
			throw new IllegalArgumentException("잘못된 인자입니다 => " + o);
		if (StringUtils.isEmpty(o.toString()))
			throw new IllegalArgumentException("값이 비었습니다. => " + o);
	}

	public static String returnDashIfNullOrEmpty(String o) {
		if (StringUtils.isEmpty(o))
			return "-";
		return o;
	}
}
