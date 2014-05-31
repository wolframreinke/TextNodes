package de.tungsten.textnodes;

import java.util.HashSet;
import java.util.Set;

import de.tungsten.textnodes.connect.IConnection;
import de.tungsten.textnodes.control.IConfiguration;
import de.tungsten.textnodes.map.IMapLoader;
import de.tungsten.textnodes.map.Map;
import de.tungsten.textnodes.nodes.Player;
import de.tungsten.textnodes.parsing.Parsers;

public abstract class TextAdventure {

	private static final String CONFIG_MAPNAME = "map";
	private IConfiguration configuration;
	
	private Map map;
	private Parsers parsers;
	private Set<Player> players = new HashSet<Player>();

	public TextAdventure( IConfiguration configuration ) 
			throws TextNodesException, IllegalArgumentException {
		
		if ( configuration == null )
			throw new IllegalArgumentException( "TextAdventure configuration must not be null" );
		
		this.configuration = configuration;	
		loadMap();
		parsers = createParsers();
	}
	
	public Player newPlayer( IConnection connection ) {
		
		Player player = createPlayer( connection );
		players.add( player );
		
		return player;
	}
	
	public Parsers getParsers() {
		return parsers;
	}
	
	public Set<Player> getPlayers() {
		return players;
	}

	private void loadMap() throws TextNodesException {

		String mapName = (String) configuration.getEntry( 
				CONFIG_MAPNAME, null, null );
		
		if ( mapName == null )
			throw new TextNodesException( "Map loading failed due to missing \"" + CONFIG_MAPNAME + "\"-entry in TextAdventure configuration" );

		Set<IMapLoader> mapLoaders = createMapLoaders();
		for ( IMapLoader loader : mapLoaders ) {

			try {
				if ( loader.canLoad( mapName ) )
					map = loader.load( mapName );
			}
			catch ( Exception e ) {}
		}
		
		if ( map == null )
			throw new TextNodesException( "No IMapLoader was able to load the map \"" + mapName + "\"" );
	}

	/**
	 * <p>This abstract method creates a set of <code>IMapLoader</code> 
	 * implementations. The returned implementations will be used to load the
	 * map, when a new TextAdventure object is created.</p>
	 * 
	 * @return	
	 */
	protected abstract Set<IMapLoader> createMapLoaders();
	
	/**
	 * <p>This abstract method creates a new Player using the given 
	 * <code>IConnection</code>. This method is called whenever a connection
	 * request a new player by calling {@link #newPlayer(IConnection)}.</p>
	 * 
	 * <p>Implementors do not need to remember the created player somehow. When
	 * the player is returned, it can be accessed using {@link #getPlayers()}.</p>
	 * 
	 * @param connection	Each player-node needs at least a 
	 * 						<code>IConnection</code> to be created. Use this
	 * 						instance to create the node.
	 * @return				The newly created player.
	 */
	protected abstract Player createPlayer( IConnection connection );
	
	/**
	 * <p>This abstract method creates a fully initialized Parsers object. This
	 * method is only called once in the constructor. The returned parsers
	 * are used by other components via {@link #getParsers()} subsequently.</p>
	 * 
	 * @return		The newly created <code>Parsers</code> object.
	 */
	protected abstract Parsers createParsers();

}
