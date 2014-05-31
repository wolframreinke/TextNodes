package de.tungsten.textnodes.map;

public interface IMapLoader {

	public abstract boolean canLoad( String mapName );
	
	public abstract Map load( String mapName ) throws Exception;
}
