package de.tungsten.textnodes.parsing;

import java.util.Iterator;
import java.util.List;

public class NodeLocation implements Iterable<String> {

	private List<String> identifiers;

	public NodeLocation( List<String> identifiers ) {
		if ( identifiers == null )
			throw new IllegalArgumentException( "NodeLocation: Identifiers must not be null." );
		
		this.identifiers = identifiers;
	}

	public String getTarget() {
		if ( identifiers.size() > 0 )
			return identifiers.get( identifiers.size() - 1 );
		else
			return ""; 
	}
	
	@Override
	public Iterator<String> iterator() {
		return identifiers.iterator();
	}
	
	@Override
	public String toString() {
		
		String result = "";
		for (String identifier : identifiers) {
			result += "-> " + identifier;
		}
		
		return result.substring( 3 );
	}
}
