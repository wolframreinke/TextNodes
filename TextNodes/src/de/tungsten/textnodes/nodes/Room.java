package de.tungsten.textnodes.nodes;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

public class Room extends LockableNode {

	
	private EnumMap<Direction, Room> adjacentRooms = new EnumMap<Direction, Room>( Direction.class );
	
	public Room( String[] names, String description ) {
		this( null, names, description, false, false );
	}
	
	public Room( String[] names, String description, 
			boolean closed, boolean locked ) {
		this( null, names, description, closed, locked );
	}
	
	public Room( Node parent, String[] names, String description, 
			boolean closed, boolean locked ) {
		
		super( parent, names, description, closed, locked );
		
	}

	public boolean canAccess( Direction direction ) {
		
		if ( isLocked() ) return false;
		
		Room target = adjacentRooms.get( direction );
		if ( target == null || target.isLocked() ) return false;
		else return true;
	
	}
	
	public Iterable<Room> getAdjacentRooms() {

		Set<Room> result = new HashSet<Room>();
		for (Entry<Direction, Room> door : adjacentRooms.entrySet()) {

			if ( this.canAccess( door.getKey() ) )
				result.add( door.getValue() );
		}
		
		return result;
	}
	
	public Room getAdjacentRoom( Direction direction ) {

		return adjacentRooms.get( direction );
	}

	public Room setAdjacentRoom( Direction direction, Room room ) {
		adjacentRooms.put( direction, room );
		return this;
	}
}
