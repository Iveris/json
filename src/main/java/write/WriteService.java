package write;

public interface WriteService {
	public WriteService getWriter(String filename);
	public void write(Object data);
	public void readComplete(boolean complete);
	public void closeWriter();
}
