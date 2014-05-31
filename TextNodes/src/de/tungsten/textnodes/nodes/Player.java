package de.tungsten.textnodes.nodes;

public class Player extends Node {

	public Player() {
		
		super( null, new String[] { "player" }, "It's the player!" );
	}
	
	@Override
	public synchronized void move( Node target ) {

		if ( target instanceof Room )
			super.move( target );
		else
			throw new NodeException( getID(), "Players can only be moved to Rooms." );
	}
}
