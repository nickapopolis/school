import java.util.ArrayList;


/**
 * This class aids in the parsing of the constraints file
 * @author 9a3eedi
 *
 */

public class ConstraintStringParser {
	
	/*
	 * GRAMMAR		::= (NAME CONSTRAINT NAME_LIST)+
	 * CONSTRAINT	::= before | has-parts
	 * NAME		::= (a..z|A..Z)+ 
	 * NAME_LIST	::= NAME+
	 * 
	 */
	
	
	String line;
	
	public static enum Constraint{ 
		HASPARTS("has-parts"), 
		BEFORE("before");
		
		public final String operator;
		Constraint(String aString){
			this.operator = aString;
		}
		
		public String toString(){
			return operator;
		}
	}
	
	public ConstraintStringParser(String aLine) throws ConstraintsException{
		if(aLine == null)
			throw new NullPointerException();
		
		// basic check: string must have at least 3 words
		if(aLine.split(" ").length < 3){
			throw new ConstraintsException(ConstraintsException.ConstraintsExceptionCause.NOT_ENOUGH_WORDS);
		}
		
		line = aLine;
	}
	
	/**
	 * Gets the "Name" part of the line (we assume it's the first "word")
	 * @return the "Name" part of the line
	 * @throws ConstraintsException if the name has not passed the validity test
	 */
	public String getName() throws ConstraintsException{
		String[] temp = line.split(" ");
		assert(temp.length >= 3);
		if(!isValidName(temp[0]))
			throw new ConstraintsException(ConstraintsException.ConstraintsExceptionCause.INVALID_NAME);
		
		return temp[0];
	
	}
	
	/**
	 * Gets the constraint type of the line (we assume that it's the second "Word");
	 * @return the constraint of the line as an enum
	 * @throws ConstraintsException if the constraint type of the line is not valid
	 */
	public Constraint getConstraint() throws ConstraintsException{
		String[] temp = line.split(" ");
		assert(temp.length >= 3);
		if(temp[1].equals(Constraint.BEFORE.toString()))
			return Constraint.BEFORE;
		else if(temp[1].equals(Constraint.HASPARTS.toString()))
			return Constraint.HASPARTS;
		
		throw new ConstraintsException(ConstraintsException.ConstraintsExceptionCause.INVALID_CONSTRAINT);
		
	}
	
	/**
	 * Gets the Name List part of the line (we assume it's the 3rd word onwards)
	 * @return a list of names as a String array
	 * @throws ConstraintsException if one of the names in the list is not valid
	 */
	public String[] getNameList() throws ConstraintsException{
		String[] temp = line.split(" ");
		assert(temp.length >= 3);
		String[] nameList = new String[temp.length -2];
		System.arraycopy(temp, 2, nameList, 0, temp.length-2);
		for(String s: nameList){
			if(!isValidName(s))
				throw new ConstraintsException(ConstraintsException.ConstraintsExceptionCause.INVALID_NAME);
		}
		
		return nameList;
	}
	
	/**
	 * 
	 * @return a String array that lists the parts mentioned in this line
	 */
	public String[] getParts() throws ConstraintsException{
		ArrayList<String> partsList = new ArrayList<String>();
		partsList.add(this.getName());
		
		for(String s: this.getNameList()){
			partsList.add(s);
		}
		String[] s = new String[1];
		return partsList.toArray(s);
	}
	
	/**
	 * Tests whether a name is valid. A name is only valid if it conforms to the following
	 * NAME		::= (a..z|A..Z)+ 
	 * @param name The name string to be tested
	 * @return true if the string is valid, false otherwise
	 */
	public static boolean isValidName(String name){
		if(name == null)
			return false;
		
		if(name.contains(" ") || name.matches("[0-9]+"))
			return false;
		
		return true;
	}
}
