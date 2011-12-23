package net.dsdstudio.slrgrabber.context;

import net.dsdstudio.slrclubgrabber.util.ValidateUtils;
/**
 * Grab 도메인 객체 uid,pwd,검색어,기본경로등을 담는다
 * @author bhkim
 * @since 2011.12.05
 */
public class SlrGrabContext {
	String userid = "";
	String password = "";
	String searchkeyword = "";
	String basefilepath = "";

	public SlrGrabContext() {
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSearchkeyword() {
		return searchkeyword;
	}

	public void setSearchkeyword(String searchkeyword) {
		this.searchkeyword = searchkeyword;
	}

	public String getBasefilepath() {
		return basefilepath;
	}

	public void setBasefilepath(String basefilepath) {
		this.basefilepath = basefilepath;
	}

	public static SlrGrabContext getInstance(String uid, String pwd,
			String skw, String filepath) {
		return new SlrGrabContext(uid, pwd, skw, filepath);
	}

	private SlrGrabContext(String uid, String pwd, String skw, String filepath) {
		ValidateUtils.throwIfNullOrEmpty(uid);
		ValidateUtils.throwIfNullOrEmpty(pwd);
		ValidateUtils.throwIfNullOrEmpty(skw);
		ValidateUtils.throwIfNullOrEmpty(filepath);
		this.userid = uid;
		this.password = pwd;
		this.searchkeyword = skw;
		this.basefilepath = filepath;
	}
}
