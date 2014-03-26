package com.datacert.surveysystem.dto;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "renotifyList")
public class ReNotifyList{

	ArrayList<ReNotify> reNotifyList;
	
	@XmlElement(name = "renotify")
	public ArrayList<ReNotify> getReNotifyList() {
		return reNotifyList;
	}

	public void setReNotifyList(ArrayList<ReNotify> reNotifyList) {
		this.reNotifyList = reNotifyList;
	}
}
