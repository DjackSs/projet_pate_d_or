package bll;

import java.util.ArrayList;
import java.util.List;

public class BLLException extends Exception {

	private static final long serialVersionUID = -8402728185430838053L;
  
	private List<String> errors = new ArrayList<>();

	public BLLException() {
	}

	public BLLException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public void addError(String error) {
		errors.add(error);
	}
	
	public List<String> getErrors() {
		return errors;
	}
}
