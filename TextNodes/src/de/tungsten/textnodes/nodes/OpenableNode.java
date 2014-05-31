package de.tungsten.textnodes.nodes;

public class OpenableNode extends Node {

	protected boolean closed;
	
	public OpenableNode( String[] names, String description, boolean closed ) {
		this( null, names, description, closed );
	}

	public OpenableNode(Node parent, String[] names, String description, boolean closed) {
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
}
