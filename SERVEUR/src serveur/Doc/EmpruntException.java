package Doc;

public class EmpruntException extends Exception {
	private static final long serialVersionUID = 1L;
	private String msg;
	
	public EmpruntException(String msg) {
		this.msg = msg;
	}
	
	public String toString() {
		return msg;
	}
}
