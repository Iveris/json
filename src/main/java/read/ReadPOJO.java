package read;

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
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	@Override
	public String toString() {
		return getPath() + ":\n\t" + getUrl() + "\n\t" + getSize() + "\n";
	}
}
