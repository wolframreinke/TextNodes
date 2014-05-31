package de.tungsten.textnodes.parsing;

public enum Preposition {

	WITH 	( new String[] {"with", "using"} ),

	TO		( new String[] {"to", "into", "onto", "over", "under", "at"} ),

	OF		( new String[] {"of", "in", "on", "from"} );
	
	String[] content;
	
	Preposition( String[] s ) {
		this.content = s;
	}

	public static Preposition fromString( String s ) {
		
		for (Preposition current : Preposition.values()) {
			for (String content : current.content) {
				if ( content.equalsIgnoreCase( s ) ) return current;
			}
		}
		
		return null;
	}
	
	@Override
	public String toString() {
		return content[0];
	}
}
