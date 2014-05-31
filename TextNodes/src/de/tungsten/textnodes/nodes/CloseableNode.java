package de.tungsten.textnodes.nodes;

public class CloseableNode extends Node {

	protected boolean closed;
	
	public CloseableNode( String[] names, String description, boolean closed ) {
		this( null, names, description, closed );
	}

	public CloseableNode(Node parent, String[] names, String description, boolean closed) {
		super( parent, names, description );
		
		this.closed = closed;
	}
	

	public boolean open() {
		
		closed = false;
		return true;	
	}
	
	public boolean close() {
		closed = true;
		return true;	
	}

	public boolean isClosed() {
		return closed;
	}

	@Override
	public Node addChild( Node child ) {
		
		if ( !closed )
			return super.addChild( child );
		else return null;
	}
	
	@Override
	public String getDescription( int depth ) {
		
		String result = super.getDescription( 0 );
		result += " This " + getIdentifier() + " is closed.";
		
		return result;
	}
}
