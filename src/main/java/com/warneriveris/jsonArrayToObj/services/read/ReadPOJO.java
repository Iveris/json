package com.warneriveris.jsonArrayToObj.services.read;

import java.util.Objects;

/**
 * Class to encapsulate data read from input file as a single entry. It is used 
 * to transfer a single JSON object entry to validation and write services.
 * 
 * @author Warner Iveris
 */

public class ReadPOJO {
	private String url;
	private String path;
	private int size;
	
	public ReadPOJO(String url, String path, int size) {
		this.url = url;
		this.path = path;
		this.size = size;
	}
	
	public String getUrl() {
		return url;
	}
	public String getPath() {
		return path;
	}
	public int getSize() {
		return size;
	}
	
	@Override
	public String toString() {
		return getPath() + ":\n\t" + getUrl() + "\n\t" + getSize() + "\n";
	}
	
	@Override
	public boolean equals(Object o) {
		if  (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ReadPOJO p = (ReadPOJO) o;
		return	Objects.equals(getUrl(), p.getUrl()) &&
				Objects.equals(getPath(), p.getPath()) &&
				Objects.equals(getSize(), p.getSize());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getUrl(), getPath(), getSize());
	}
}
