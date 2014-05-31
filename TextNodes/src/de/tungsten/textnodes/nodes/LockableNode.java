package de.tungsten.textnodes.nodes;

import java.util.HashSet;
import java.util.Set;

public class LockableNode extends CloseableNode {

	private boolean locked;

	private Set<Node> keys = new HashSet<Node>();

	public LockableNode( String[] names, String description, boolean closed,
			boolean locked ) {
		this( null, names, description, closed, locked );
	}

	public LockableNode( Node parent, String[] names, String description,
			boolean closed, boolean locked ) {
		super( parent, names, description, closed );

		this.locked = locked;
	}

	@Override
	public boolean open() {

		if ( !locked )
			closed = false;
		return !locked;
	}

	public boolean lock( Node key ) {
		if ( keys.contains( key ) ) {
			locked = true;
			return true;
		}
		else
			return false;
	}

	public boolean unlock( Node key ) {
		if ( keys.contains( key ) ) {
			locked = false;
			return true;
		}
		else
			return false;
	}

	public boolean isLocked() {
		return locked;
	}

	public LockableNode addKey( Node identifier ) {

		keys.add( identifier );
		return this;
	}
}
