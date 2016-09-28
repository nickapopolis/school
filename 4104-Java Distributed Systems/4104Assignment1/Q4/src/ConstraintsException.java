
public class ConstraintsException extends Exception {

	private static final long serialVersionUID = -6791819766959809409L;
	
	public static enum ConstraintsExceptionCause{
		UNKNOWN("Exception occured due to an unknown cause"),
		INVALID_CONSTRAINT("The constraint used in this line is invalid"),
		NOT_ENOUGH_WORDS("The line does not have the minimum number of words"),
		INVALID_NAME("One of the names is not valid");
		
		final public String causeString;
		
		private ConstraintsExceptionCause(String aString){
			causeString = aString;
		}
	}	
	
	private ConstraintsExceptionCause theCause;

	public ConstraintsException(){
		super();
		theCause = ConstraintsExceptionCause.UNKNOWN;
	}
	
	public ConstraintsException(ConstraintsExceptionCause aCause){
		super(aCause.toString());
		theCause = aCause;
	}
	
	public ConstraintsExceptionCause getConstraintsExceptionCause(){
		return theCause;
	}
	
}
