package net.dsdstudio.slrclubgrabber.logic;

import org.jdom.Element;
/**
 * 
 * @author bohyungkim
 * @param <T>
 */
public interface XpathCallback<T> {
	T parseSomethingWithElement(Element el, T value);
}
