package de.tungsten.textnodes.nodes;

/**
 * <p>A direction, a room's door may lead to. This includes the four cardinal
 * directions (north, south, ...), thier combinations (northeast,
 * southwest, ...), and the directions up and down.</p>
 * 
 * <p>Each direction is associated with a textual representation. This
 * string is returned by the <code>toString()</code> method and can be used
 * to retrieve a direction instance (using {@link #fromString(String)}).</p>
 * 
 * @author tungsten
 * @version 1.0
 * 
 * 
 */
public enum Direction {

	/**
	 * <p>Indicates that the object, this direction is associated with, points to
	 * the north. The textual representation of this direction in text-only games
	 * is <code>"north"</code>.</p>
	 */
	NORTH		( "north" ),
	
	/**
	 * <p>Indicates that the object, this direction is associated with, points to
	 * the south. The textual representation of this direction in text-only games
	 * is <code>"south"</code>.</p>
	 */
	SOUTH		( "south" ),
	
	/**
	 * <p>Indicates that the object, this direction is associated with, points to
	 * the west. The textual representation of this direction in text-only games
	 * is <code>"west"</code>.</p>
	 */
	WEST		( "west" ),
	
	/**
	 * <p>Indicates that the object, this direction is associated with, points to
	 * the east. The textual representation of this direction in text-only games
	 * is <code>"east"</code>.</p>
	 */
	EAST		( "east" ),
	
	/**
	 * <p>Indicates that the object, this direction is associated with, points
	 * northwestwards. The textual representation of this direction in text-only games
	 * is <code>"northwest"</code>.</p>
	 */
	NORTHWEST	( "northwest" ),
	
	/**
	 * <p>Indicates that the object, this direction is associated with, points
	 * northeastwards. The textual representation of this direction in text-only games
	 * is <code>"northeast"</code>.</p>
	 */
	NORTHEAST	( "northeast" ),
	
	/**
	 * <p>Indicates that the object, this direction is associated with, points
	 * southwestwards. The textual representation of this direction in text-only games
	 * is <code>"southwest"</code>.</p>
	 */
	SOUTHWEST	( "southwest" ),
	
	/**
	 * <p>Indicates that the object, this direction is associated with, points
	 * southeastwards. The textual representation of this direction in text-only games
	 * is <code>"southeast"</code>.</p>
	 */
	SOUTHEAST	( "southeast" ),
	
	/**
	 * <p>Indicates that the object, this direction is associated with, points
	 * upwards. The textual representation of this direction in text-only games
	 * is <code>"up"</code>.</p>
	 */
	UP			( "up" ),
	
	/**
	 * <p>Indicates that the object, this direction is associated with, points
	 * downwards. The textual representation of this direction in text-only games
	 * is <code>"down"</code>.</p>
	 */
	DOWN		( "down" );
	
	/**
	 * The textual representation of this <code>Direction</code> in text-only
	 * games. This is string can be written to a connection to a user
	 * to refer to this direction. Usually, user will use these strings to 
	 * refer to a direction, so this string can also be used to parse user input.
	 */
	private String representation;
	
	/**
	 * Creates a new instance of <code>Direction</code>. The new instance will
	 * have the given textual representation.
	 * 
	 * @param representation	The textual representation of the newly created
	 * 							<code>Direction</code>.
	 * @see #representation
	 */
	Direction( String representation ) {
		this.representation = representation;
	}
	
	/**
	 * <p>Returns the <code>Direction</code> instance which has the given textual
	 * representation. Therefore, passing "north" to this method will return
	 * the <code>Direction</code> instance <code>NORTH</code>.</p>
	 * 
	 * <p>If no instance of <code>Direction</code> has the given representation,
	 * <code>null</code> is returned.</p>
	 * 
	 * @param s		The textual representation of the desired 
	 * 				<code>Direction</code>.
	 * @return		The <code>Direction</code>, which has the given textual
	 * 				representation.
	 */
	public static Direction fromString( String s ) {
		for (Direction direction : Direction.values()) {
			if ( direction.representation.equals( s ) )
				return direction;
		}
		
		return null;
	}
	
	/**
	 * Returns the textual representation of this instance of
	 * <code>Direction</code>.
	 * 
	 * @return	The textual representation of this <code>Direction</code>.
	 */
	@Override
	public String toString() {
		return representation;
	}
}
