package edu.buaa.sei.datamodel;

import java.util.ArrayList;
import java.util.List;

public class ClaInsFragment {

	String claInsName;
	List<TacticInfo> tiList;

	public ClaInsFragment(String claInsName, List<TacticInfo> tiList) {
		super();
		this.claInsName = claInsName;
		this.tiList = tiList;
	}

	public String getClaInsName() {
		return claInsName;
	}

	public List<TacticInfo> getTiList() {
		return tiList;
	}

}
