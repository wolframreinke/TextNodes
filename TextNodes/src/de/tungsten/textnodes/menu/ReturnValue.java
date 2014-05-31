package de.tungsten.textnodes.menu;

import de.tungsten.textnodes.connect.IConnection;


public class ReturnValue implements IMenu {

	Object value;
	
	public ReturnValue( Object value ) {
		this.value = value;
	}
	
	@Override
	public void setParent(IMenu parent) {
	}

	@Override
	public Object display( IConnection connection ) {
		return value;
	}

}
