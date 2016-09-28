import java.util.Random;


public class Password 
{
	private String passwordDomain;
	private int[] passwordValues;
	private int passwordLength = 7;
	private static Random randomNum = new Random(System.currentTimeMillis());
	
	public Password(int[] values)
	{
		passwordValues = values.clone();
	}
	public Password(String domain)
	{
		passwordDomain = domain;
		
		passwordValues = new int[passwordLength];
		int numValuesPerAttribute = getNumberOfValuesForLength(passwordLength);
		for(int i=0;i<passwordLength;i++)
			passwordValues[i] = randomNum.nextInt(numValuesPerAttribute);
		
	}
	
	/**
	 * Returns the number of values each attribute should have for a specific password 
	 * length so that the password space is 2^28
	 * @param length
	 * @return
	 */
	public static int getNumberOfValuesForLength(int length)
	{
		return (int)Math.ceil(Math.log(Math.pow(2,28))/ Math.log(length));
	}
	
	/**
	 * Returns the int value of attribute value at specific index
	 * @param attributeIndex
	 * @return
	 */
	public int getPasswordValueForAttribute(int attributeIndex)
	{
		return passwordValues[attributeIndex];
	}
	
	/**
	 * Returns the password domain
	 */
	public String getDomain()
	{
		return passwordDomain;
	}
	
	/**
	 * Returns true if passwords are equivalent
	 * @param password
	 * @return
	 */
	public boolean equals(Password password)
	{
		//if passwords are not the same length they are not equal
		if(this.passwordLength != password.passwordLength)
			return false;
					
		for(int i=0; i <passwordLength; i++)
		{
			//passwords are not equal if a single bit does not line up or if the password is invalid
			if( this.passwordValues[i] != password.passwordValues[i] || this.passwordValues[i] == -1)
				return false;
		}
		return true;
	}
	
	public String toString()
	{
		String ret = "";
		for(int i=0; i <passwordLength; i++)
			ret += passwordValues[i] + " ";
		return ret;
	}
	
}
