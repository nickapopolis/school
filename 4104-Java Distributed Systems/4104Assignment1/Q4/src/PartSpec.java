import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

/**
 * This class acts as a "specification" of a part. It cannot do any building.
 * @author 9a3eedi
 *
 */
public class PartSpec{
	
	public final String partId;
	private Vector<PartSpec> dependencies;
	private Set<PartSpec> reverseDependencies;
	private Set<PartSpec> beforeSpecs;
	
	/**
	 * Constructs a new Part Specification
	 * @param id An identifier for this part
	 * @throws NullPointerException if id is null
	 */
	public PartSpec(String id){
		if(id == null)
			throw new NullPointerException();
		
		partId = id;
		
		dependencies = new Vector<PartSpec>();
		reverseDependencies = Collections.synchronizedSet(new HashSet<PartSpec>());
		beforeSpecs = Collections.synchronizedSet(new HashSet<PartSpec>());
	}
	
	/**
	 * This method makes the part defined by this spec "depend" on the completion of another part.
	 * 
	 * NOTE: This makes the assumption that the calling thread behaves properly and
	 * doesn't cause a cyclic dependency!
	 * @param p Another part that this part depends on.
	 */
	public void addDependency(PartSpec p){
		if(p == null)
			return;
		
		p.addReverseDependency(this);
		dependencies.add(p);
	}
	
	/**
	 * This method adds a reverse dependency to its set of reverse dependencies
	 * @param p Another part that depends on this part.
	 */
	private void addReverseDependency(PartSpec p){
		if(p == null)
			return;
		
		reverseDependencies.add(p);
	}
	
	/**
	 * Sets this PartSpec to be built before another partspec
	 * @param p the partspec that this one needs to be built before.
	 */
	public void setBefore(PartSpec p){
		if(p == null)
			return;
		
		beforeSpecs.add(p);
	}
	
	/**
	 * This function checks if this part is in the "top" level of the dependency tree
	 * That is, it checks if there are any parts that depend on it.
	 * @return true if there are no other parts that depend on this.
	 */
	public boolean isTopLevel(){
		if(reverseDependencies.isEmpty())
			return true;
		
		return false;
	}
	
	/**
	 * Compares the partId of two different parts
	 */
	@Override
	public boolean equals(Object b){
		try{
			return ((PartSpec)b).partId.equals(this.partId);
		}catch(Exception e){
			
		}
		return false;
	}
	
	/**
	 * Returns a clone of the set of dependencies of this spec
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Vector<PartSpec> getDependenciesList(){
		return (Vector<PartSpec>) dependencies.clone();
	}
	
	/**
	 * This method is used to get the id of the spec
	 * @return the id of the part spec
	 */
	public String getId(){ return partId; }
	
	/**
	 * @return a new part based off this spec
	 */
	public Part createPart(){
		return new Part(this);
	}
	
	
}