package net.dsdstudio.slrclubgrabber.main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

import net.dsdstudio.slrclubgrabber.logic.SlrGrabParser;
import net.dsdstudio.slrclubgrabber.util.LoggerUtil;
import net.dsdstudio.slrgrabber.context.SlrGrabContext;
import net.dsdstudio.slrgrabber.context.UrlContext;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.jdom.Document;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;

public class Main {
	static {
		LoggerUtil.setConsoleLogging();
	}
	private HttpClient client = new HttpClient();
	private SlrGrabContext context;

	public Main setUp(SlrGrabContext ctx) {
		this.context = ctx;
		return this;
	}

	public Integer getDirectionIndex(String url) {
		Document doc = SlrGrabParser.getPageContent(this.client, url);
		List<String> directionlist = SlrGrabParser.getDirectionList(doc);
		String direction = directionlist.get(0);
		return directionlist.isEmpty() ? 0 : NumberUtils.toInt(StringUtils
				.substring(direction,
						StringUtils.lastIndexOf(direction, "=") + 1));
	}

	public void grabContent(String url) {
		Document doc = SlrGrabParser.getPageContent(this.client, url);
		List<String> urllist = SlrGrabParser.getPageUrlList(doc);
		for (String purl : urllist) {
			Document document = SlrGrabParser.getPageContent(this.client, purl);
			List<String> imgurllist = SlrGrabParser
					.getPageImageTagURLs(document);
			for (String imgurl : imgurllist) {
				this.write(imgurl, this.context.getBasefilepath());
			}
		}
	}

	public void write(final String url, final String filepath) {
		GetMethod method = new GetMethod();
		URI uri = null;
		try {
			uri = new URI(url, false, "EUC_KR");
			method.setURI(uri);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (uri == null)
			return;

		method.setRequestHeader(
				"User-Agent",
				"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2");
		method.setRequestHeader("Referer",
				"http://www.slrclub.com/bbs/zboard.php?id=free");
		FileOutputStream fos = null;
		try {
			int statuscode = this.client.executeMethod(method);
			if (statuscode == 200 || statuscode == 304) {
				DateTime now = DateTime.now();
				File file = new File(filepath + File.separator + "grab");
				file.mkdirs();

				fos = new FileOutputStream(filepath + File.separator + "grab/"
						+ now.toString("yyyyMMddHHmmssSSS")
						+ UrlContext.getUrlExtension(url));
				IOUtils.write(method.getResponseBody(), fos);
			} else {
				System.out.println("[" + statuscode + "] Error writing file "
						+ url);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(fos);
		}
	}

	public void login(String userid, String password) {
		PostMethod method = new PostMethod(
				"http://www.slrclub.com/login/process.php");
		method.setRequestHeader(
				"User-Agent",
				"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2");
		method.setRequestHeader("Referer",
				"http://www.slrclub.com/bbs/zboard.php?id=free");
		method.addParameter("user_id", userid);
		method.addParameter("password", password);
		try {
			this.client.executeMethod(method);
			if (StringUtils.contains(method.getResponseBodyAsString(), "false")) {
				throw new RuntimeException(
						"Login Failed.. check your id and password.");
			}
			System.out.println("Login Success! userid["
					+ this.context.getUserid() + "]");
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
	}

	public void execute() {
		this.login(this.context.getUserid(), this.context.getPassword());
		Integer directionidx = this.getDirectionIndex(MessageFormat.format(
				UrlContext.PageUrl, UrlContext.getEncodedString(
						this.context.getSearchkeyword(), "EUC_KR")));

		System.out.println("Find [" + directionidx + "] Pages");
		System.out.println("Exporting image from page.");

		DateTime startdate = DateTime.now();
		for (int i = directionidx; i >= 0; i--) {
			System.out.println("Exporting Page [" + i + "] ");
			String pageurl = MessageFormat.format(UrlContext.DirectionUrl,
					UrlContext.getEncodedString(
							this.context.getSearchkeyword(), "EUC_KR"), i);
			this.grabContent(pageurl);
			System.out.println("Export Page [" + i + "] Succeed.");
		}
		DateTime enddate = DateTime.now();
		Duration duration = new Interval(startdate, enddate).toDuration();
		System.out.println("duration : "
				+ MessageFormat.format("{0,number}",
						duration.getStandardSeconds()) + "Seconds");
	}

	public static void main(String[] args) {
		if (args.length != 4) {
			Main.pringInformation();
			return;
		}
		SlrGrabContext ctx = SlrGrabContext.getInstance(args[0], args[1],
				args[3], args[2]);

		new Main().setUp(ctx).execute();

	}

	public static void pringInformation() {
		System.out.println(StringUtils.center("", 80, '-'));
		System.out.println(StringUtils.center("SlrClubDataGrabber Version 1.0",
				80, ' '));
		System.out.println(StringUtils.center("Author : bhkim", 80));
		System.out.println(StringUtils.center(
				"Contact : dsdgunAtgmail.com,@dsdstudio,http://blog.dsdstudio.net", 80, ' '));
		System.out
				.println(StringUtils
						.center("Usage: java -jar slrgrabber.jar <id> <password> <savefilepath> <keyword>",
								80, ' '));
		System.out
				.println(StringUtils
						.center("Example) java -jar slrgrabber.jar userid password c:/jogong jogong",
								80, ' '));
		System.out.println(StringUtils.center("", 80, '-'));
	}
}
