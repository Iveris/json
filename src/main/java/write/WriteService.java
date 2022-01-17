package write;

public interface WriteService<K, V> {
	public WriteService<K, V> getWriter(String filename);
	public void write(Object data);
	public void readComplete(boolean complete);
	public void closeWriter();
}
