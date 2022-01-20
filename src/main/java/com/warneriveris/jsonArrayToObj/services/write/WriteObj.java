package com.warneriveris.jsonArrayToObj.services.write;

import java.util.Objects;
/**
 * 
 * @author Warner
 */
public class WriteObj {
	private String url;
	private int size;
	
	public WriteObj(String url, int size) {
		this.url = url;
		this.size = size;
	}

	public String getUrl() {
		return url;
	}

	public int getSize() {
		return size;
	}
	
	@Override
	public boolean equals(Object o) {
		if  (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		WriteObj p = (WriteObj) o;
		return Objects.equals(getUrl(), p.getUrl()) &&
				Objects.equals(getSize(), p.getSize());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getUrl(), getSize());
	}
	
	@Override
	public String toString() {
		return "URL: " + getUrl() + "\tSize: " + getSize() + "\n";
	}
}
