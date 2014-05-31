package de.tungsten.textnodes.menu;

import java.util.ArrayList;
import java.util.List;

import de.tungsten.textnodes.connect.IConnection;


public class MenuSequence implements IMenu {

	private List<IMenu> targets = new ArrayList<IMenu>();
	
	public MenuSequence addMenu( IMenu menu ) {
		targets.add( menu );
		
		return this;
	}
	
	@Override
	public void setParent(IMenu parent) {
		
	}

	@Override
	public List<Object> display( IConnection connection ) {

		List<Object> results = new ArrayList<Object>();
		
		for (IMenu current : targets) {
			results.add( current.display( connection ) );
		}
		
		return results;
	}

	
}
