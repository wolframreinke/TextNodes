package de.tungsten.textnodes.map;

import de.tungsten.textnodes.connect.IPlayerConnection;
import de.tungsten.textnodes.nodes.Node;

public class Map {

	private String name;
	private Node spawnPoint;

	public Map( String name, Node spawnPoint ) {
		this.name = name;
		this.spawnPoint = spawnPoint;
	}
	
	public synchronized void spawn( IPlayerConnection connection ) {
		
		connection.getPlayer().move( spawnPoint );
		
		// Info printen
		connection.write( "---------- " + this.name + " ----------\n\n" );
		connection.write( connection.getPlayer().getParent().getDescription() );
	}

	public String getName() {
		return name;
	}	
}
