package de.tungsten.textnodes.nodes;

import de.tungsten.textnodes.connect.IConnection;

public class Player extends Node {

	private IConnection connection;

	public Player( IConnection connection ) {

		super( null, new String[] { "player" }, "It's the player!" );
		this.connection = connection;
	}

	@Override
	public synchronized void move( Node target ) {

		if ( target instanceof Room )
			super.move( target );
		else
			throw new NodeException( getID(),
					"Players can only be moved to Rooms." );
	}

	public void connect( IConnection connection ) {
		this.connection = connection;
	}

	public void disconnect() {
		this.connection = null;
	}

	public boolean isConnected() {
		return this.connection != null;
	}

	public boolean write( String message ) {
		if ( isConnected() )
			return connection.write( message );
		else
			return false;
	}
}
