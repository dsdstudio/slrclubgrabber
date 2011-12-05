package net.dsdstudio.slrclubgrabber.logic;

import java.util.ArrayList;
import java.util.List;

import net.dsdstudio.slrgrabber.context.UrlContext;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.JDomSerializer;
import org.htmlcleaner.TagNode;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;

/**
 * JDom기반 Parser
 * 
 * @author bohyungkim
 * 
 */
public class SlrGrabParser {
	/**
	 * 페이징 Xpath Query
	 */
	public static String PageDirectionQuery = "//html/body/div[@class='slrbbs']/div[@id='slrct']/table/tbody/tr/td/table[@class='pageN']/tbody/tr/td[@class='list_num']/a";
	/**
	 * 게시물 List Xpath Query
	 */
	public static String PageListQuery = "//html/body/div[@class='slrbbs']/div[@id='slrct']/form[@action='list_all.php']/table/tbody/tr/td[@class='list_sbj']/a";
	/**
	 * 게시물에서 이미지 URL만 뽑아내는 Xpath Query
	 */
	public static String ImageQuery = "//html/body/div[@id='userct']/table/tbody/tr/td/img";

	/**
	 * URL 을 clean한 JDom Document 객체로 변환하여 리턴한다.<br />
	 * 
	 * @param url
	 * @return
	 */
	public static Document getPageContent(HttpClient client, String url) {
		GetMethod method = new GetMethod(url);
		method.setRequestHeader(
				"User-Agent",
				"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2");
		method.setRequestHeader("Referer",
				"http://www.slrclub.com/bbs/zboard.php?id=free");
		try {
			client.executeMethod(method);
			CleanerProperties prop = new CleanerProperties();
			prop.setOmitDoctypeDeclaration(true);
			prop.setOmitComments(true);
			prop.setRecognizeUnicodeChars(false);
			HtmlCleaner cleaner = new HtmlCleaner(prop);
			TagNode node = cleaner.clean(method.getResponseBodyAsStream());
			JDomSerializer serializer = new JDomSerializer(prop);
			return serializer.createJDom(node);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		return null;
	}

	/**
	 * 페이징 Element에서 링크를 뽑아낸다
	 * 
	 * @param doc
	 * @return
	 */
	public static List<String> getDirectionList(Document doc) {
		return elementReadTemplate(doc, PageDirectionQuery,
				new XpathCallback<List<String>>() {
					public List<String> parseSomethingWithElement(Element el,
							List<String> value) {
						value.add(el.getAttributeValue("href"));
						return value;
					}
				}, new ArrayList<String>());
	}

	/**
	 * 게시물에서 이미지태그중 이미지URL만 뽑아낸다
	 * 
	 * @param doc
	 * @return
	 */
	public static List<String> getPageImageTagURLs(Document doc) {
		return elementReadTemplate(doc, ImageQuery,
				new XpathCallback<List<String>>() {
					public List<String> parseSomethingWithElement(Element el,
							List<String> value) {
						String imgsrc = el.getAttributeValue("src");
						value.add(imgsrc);
						return value;
					}
				}, new ArrayList<String>());
	}

	/**
	 * 게시물 리스트에서 게시물 URL만 뽑아낸다
	 * 
	 * @param doc
	 * @return List<String> urllist
	 */
	public static List<String> getPageUrlList(Document doc) {
		XpathCallback<List<String>> callback = new XpathCallback<List<String>>() {
			public List<String> parseSomethingWithElement(Element el,
					List<String> value) {
				String linkurl = UrlContext.BaseUrl + "bbs/"
						+ el.getAttribute("href").getValue();
				value.add(linkurl);
				return value;
			}
		};
		return elementReadTemplate(doc, PageListQuery, callback,
				new ArrayList<String>());
	}

	/**
	 * JDom Element Element Read Template 로직 단순화를 위한 템플릿 메소드 패턴
	 * 
	 * @param doc
	 * @param xpath
	 * @param callback
	 * @param list
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public static <T> T elementReadTemplate(Document doc, String xpath,
			XpathCallback<T> callback, T list) {
		try {
			List<Element> ellist = XPath.selectNodes(doc, xpath);
			T res = list;
			for (Element el : ellist) {
				list = callback.parseSomethingWithElement(el, res);
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		return list;
	}

}
