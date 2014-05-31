package de.tungsten.textnodes.parsing;

import de.tungsten.textnodes.connect.IPlayerConnection;


public interface IParser {
	

	public abstract String[] getKeywords();

	public abstract void parse( String input, IPlayerConnection connection );
}
