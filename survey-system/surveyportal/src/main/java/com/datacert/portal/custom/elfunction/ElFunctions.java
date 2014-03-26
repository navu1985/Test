package com.datacert.portal.custom.elfunction;

public class ElFunctions {

	public static Object formatStringForHtmlDisplay(String value) {
		if (value != null) {
			value = value.replace(">", "&gt;").replace("<", "&lt;").replace("\"", "&quot;").replace("“","&#8220;").replace("”","&#8221;").trim();
			return value.toString().replace("\n", "<br />");
		}
		return value;
	}

}
