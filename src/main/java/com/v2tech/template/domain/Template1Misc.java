package com.v2tech.template.domain;

import java.util.ArrayList;
import java.util.List;

public class Template1Misc {
	
String id;

String source;

List<String> comments = new ArrayList<String>();

public String getSource() {
	return source;
}

public void setSource(String source) {
	this.source = source;
}

public List<String> getComments() {
	return comments;
}

public void setComments(List<String> comments) {
	this.comments = comments;
}

public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}


}
