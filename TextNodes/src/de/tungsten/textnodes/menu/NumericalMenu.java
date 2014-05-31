package de.tungsten.textnodes.menu;

import java.util.ArrayList;
import java.util.List;

import de.tungsten.textnodes.connect.IConnection;


public class NumericalMenu implements IMenu {


	private class Option {
		
		private String text;
		private IMenu target;
		
		public Option( String text, IMenu target ) {
			this.target = target;
			this.text = text;
		}
		
		public String getText() {
			return text;
		}
		
		public IMenu getTarget() {
			return target;
		}
	}
	

	private String message;
	
	private List<Option> options = new ArrayList<Option>();
	
	public NumericalMenu( String message ) {
		this( null, message );
	}

	public NumericalMenu( IMenu parent, String message ) {
		this.message = message;
		
		if ( parent != null )
			setParent( parent );
	}
	
	public NumericalMenu addOption( String message, IMenu target ) {
		
		options.add( new Option( message, target ) );
		target.setParent( this ); 
		
		return this;
	}
	
	@Override
	public void setParent( IMenu parent ) {
		options.add( new Option( "back", parent  ) );
	}
	

	@Override
	public Object display( IConnection connection ) {

		connection.write( "\n" + message );
		for (int i = 0; i < options.size(); i++) {
			connection.write( "\n [" + i + "] " + options.get( i ).getText() );
		}
		
		boolean found = false;
		Object result = null;
		
		while ( !found ) {
			
			connection.write( "\n> " );
			
			String input = connection.read();
			
			try {
				int choice = Integer.parseInt( input.trim() );
				if ( choice < 0 || choice > options.size() - 1 ) throw new Exception();
				
				result = options.get( choice ).getTarget().display( connection );
				found = true;
				
			} catch ( Exception e ) {
				connection.write( "\nEnter a valid number!" );
			}
			
		}
		
		return result;
		
	}

}
