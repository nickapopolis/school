import java.util.ArrayList;


public class Feature 
{
	String featureName;
	ArrayList<Value> values;
	Value selectedValue;
	
	public Feature(String name)
	{
		featureName = name;
		values = new ArrayList<Value>();
	}

	public String getName()
	{
		return featureName;
	}
	public int getNumValues()
	{
		return values.size();
	}
	public void addValue(Value value)
	{
		values.add(value);
	}
	public Value getValue(int index)
	{
		if(index >values.size()-1)
			return null;
		
		return values.get(index);
	}
	public Value getSelectedValue()
	{
		return selectedValue;
	}
}
