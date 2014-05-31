package de.tungsten.textnodes.map;

public interface IMapProvider {

	public abstract boolean canLoad( String mapName );
	
	public abstract Map provideMap( String mapName ) throws Exception;
}
