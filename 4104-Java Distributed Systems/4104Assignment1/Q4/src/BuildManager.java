import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

/**
 * This class parses a filename for constraints and then builds any requested parts
 * @author 9a3eedi
 *
 */

public class BuildManager {

	Set<PartSpec> partSpecs;
	
	public BuildManager(){
		partSpecs = Collections.synchronizedSet(new HashSet<PartSpec>());
	}
	
	/**
	 * Opens a file and parses the constraints defined by it
	 * @param filename the path to the file
	 * @throws ConstraintsException thrown when something in the file does not make sense or if there's an error while parsing
	 * @throws FileNotFoundException thrown if the file was not found
	 * @throws IOException thrown when there is an IO error
	 */
	public synchronized void parseConstraintsFile(String filename) 
			throws ConstraintsException, FileNotFoundException, IOException
	{
		if(Question4.DEBUG)
			System.out.println("reading file and parsing");
			
		BufferedReader in = new BufferedReader(new FileReader(filename));
		ArrayList<ConstraintStringParser> lines = new ArrayList<ConstraintStringParser>();
		String temp = null;
		while( (temp = in.readLine()) != null){
			lines.add(new ConstraintStringParser(temp));
		}
		in.close();
		in = null;
		
		// create empty part specs
		temp = null;
		for(ConstraintStringParser p: lines){
			for(String s: p.getParts())
				partSpecs.add(new PartSpec(s));
		}
		
		// set "has-parts" and "befores" (very bad code here)
		for(ConstraintStringParser p: lines){
			PartSpec theSpec = getPartSpec(p.getName());
			ConstraintStringParser.Constraint theConstraint = p.getConstraint();
			String[] namesList = p.getNameList();
			
			if(theConstraint == ConstraintStringParser.Constraint.HASPARTS){
				for(String s: namesList){
					theSpec.addDependency(getPartSpec(s));
				}
			}else if(theConstraint == ConstraintStringParser.Constraint.BEFORE){
				// can't implement this :/
			}
			
		}
		
	}
	
	/**
	 * Builds a requested set of parts. This method is blocking
	 * @param parts a list of parts that need to be built
	 */
	public synchronized void build(Vector<String> parts){
		
		Vector<Part> requestedParts = new Vector<Part>();
		for(String p: parts){			
			requestedParts.add(this.getPartSpec(p).createPart());
		}
		
		for(Part p: requestedParts){
			if(Question4.DEBUG)
				System.out.println("Building part: " + p);
			p.build();
		}
		
		for(Part p: requestedParts){
			p.waitForBuild();
		}
	}
	
	/**
	 * Gets a defined PartSpec denoted by aPart
	 * @param aPart the id of the part needed
	 * @return the PartSpec defined by aPart, or null if the part doesn't exit
	 */
	public synchronized PartSpec getPartSpec(String aPartSpecId){
		for(PartSpec s: partSpecs){
			if(s.getId().equals(aPartSpecId)){
				return s;
			}
		}
		return null;
	}
	

	
}
