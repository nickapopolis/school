import java.io.PrintStream;
import java.util.Vector;


public class Question4 {
	
	// used to help debugging (should enable/disable verbose output)
	final public static boolean DEBUG = true;

	final public static String usageString[] = {
		"Usage:",
		"------",
		"java Question4 -c <constraints file> -b <Part 1> <Part 2> ... <Part n>",
		"	<constraints file> is the path of the filename defining the constraints on construction",
		"	<Part> is the name of the part you want to build",
		""
	};

	final public static String constraintFileOperator = "-c";
	final public static String buildListOperator = "-b";
	
	
	public static void printUsage(PrintStream o){
		for(String s: usageString)
			o.println(s);
	}

	public static void main(String[] args) {

		// get command line arguments
		String constraintsFilename = null;
		Vector<String> partsToBuild = new Vector<String>();
		
		for(int i = 0; i < args.length; i++){
			// handle -c
			if(args[i].equals(constraintFileOperator) && args.length > i+1){
				constraintsFilename = args[i+1];
				i++; // skip the next argument
				continue;
			}
			
			// handle -b
			if(args[i].equals(buildListOperator) && args.length > i+1){
				int j = 1;
				while(args.length > i + j  && !args[i+j].equals(constraintFileOperator)){
					partsToBuild.add(args[i+j]);
					j++;
				}
				i += j;
				continue;				
			}
		}
		
		if(constraintsFilename == null || partsToBuild.size() == 0){
			printUsage(System.out);
			System.exit(0);
		}
		
		try{
			BuildManager manager = new BuildManager();
			manager.parseConstraintsFile(constraintsFilename);
			manager.build(partsToBuild);
		}catch(ConstraintsException e){
			System.err.println(e.getMessage());
			if(DEBUG)
				e.printStackTrace();
			System.exit(1);
		}catch(Exception e){
			System.err.println("Unknown exception has occured");
			e.printStackTrace();
			System.exit(2);
		}
		
		System.out.println("DONE!");
		
		System.exit(0);
		
		
	}

}
