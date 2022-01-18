package services.read;

public interface ReadService {
	public ReadService getReader(String filename);
	public void closeReader();
	public boolean hasNext();
	public Object next(Object type);
}
