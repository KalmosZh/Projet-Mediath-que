package Doc;

public class ReservationException extends Exception {
	private static final long serialVersionUID = 1L;
	private String msg;
	
	public ReservationException(String msg) {
		this.msg = msg;
	}
	
	public String toString() {
		return msg;
	}
	

}
