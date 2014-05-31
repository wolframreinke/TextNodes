package de.tungsten.textnodes.menu;

import de.tungsten.textnodes.connect.IConnection;


public interface IMenu {

	public abstract void setParent( IMenu parent );
	
	public abstract Object display( IConnection connection );
}
