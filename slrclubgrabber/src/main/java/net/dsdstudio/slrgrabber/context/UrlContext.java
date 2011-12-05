package net.dsdstudio.slrgrabber.context;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;

public class UrlContext {
	public final static String BaseUrl = "http://www.slrclub.com/";
	public final static String PageUrl = "http://www.slrclub.com/bbs/zboard.php?id=free&sn=off&sid=off&ss=on&sc=off&keyword={0}&x=5&y=4";
	public final static String DirectionUrl = "http://www.slrclub.com/bbs/zboard.php?id=free&select_arrange=headnum&desc=asc&category=&fv=&sn=off&sid=off&ss=on&sc=off&st=off&keyword={0}&sn1=&sid1=&divpage={1,number,#}";

	/**
	 * URLEncode wrap method
	 * 
	 * @param s
	 * @param encoding
	 * @return
	 */
	public static String getEncodedString(String s, String encoding) {
		try {
			return URLEncoder.encode(s, encoding);
		} catch (UnsupportedEncodingException e) {
		}
		return s;
	}

	/**
	 * URL에서 확장자만 뽑아낸다 .xxx
	 * 
	 * @param url
	 * @return
	 */
	static public String getUrlExtension(String url) {
		return StringUtils.substring(url, StringUtils.lastIndexOf(url, '.'));
	}

}
