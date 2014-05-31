package de.tungsten.textnodes.nodes;

import de.tungsten.textnodes.connect.IConnection;

public class Player extends Node {

	private enum Accessibility {
		ACCESSIBLE,
		ANCESTOR_CLOSED,
		OUTSIDE_THIS_ROOM;
	}
	
	private IConnection connection;

	public Player( IConnection connection ) {

		super( null, new String[] { "player" }, "It's the player!" );
		this.connection = connection;
	}

	@Override
	public synchronized void move( Node target ) {

		if ( target instanceof Room )
			super.move( target );
		else
			throw new NodeException( getID(),
					"Players can only be moved to Rooms." );
	}

	public void connect( IConnection connection ) {
		this.connection = connection;
	}

	public void disconnect() {
		this.connection = null;
	}

	public boolean isConnected() {
		return this.connection != null;
	}

	public boolean write( String message ) {
		if ( isConnected() )
			return connection.write( message );
		else
			return false;
	}
	
	public void say( String message ) {
		
		write( message );
	}
	
	public void describe( Node node ) {
		
		if ( isAccessible( node ) ) {
			write( node.getDescription() );
		}
	}
	
	public void take( Node node ) {
		
		if ( isAccessible( node ) ) {
			node.move( this );
			write( "Taken." );
		}
	}
	
	public void put( Node node, Node targetLocation ) {
		
		if ( isAccessible( node ) && isAccessible( targetLocation ) ) {
			node.move( targetLocation );
			write( "Done." );
		}
	}
	
	public void open( Node node ) {
		
		if ( isAccessible( node ) ) {
			if ( node instanceof CloseableNode ) {
				CloseableNode box = (CloseableNode) node;
				
				if ( box.open() ) 
					write( "Opened." );
				else 
					write( "The " + node.getIdentifier() + " is locked." );
			}
			else write( "The " + node.getIdentifier() + " cannot be opened." );
		}
	}
	
	public void close( Node node ) {
		
		if ( isAccessible( node ) ) {
			if ( node instanceof CloseableNode ) {
				CloseableNode box = (CloseableNode) node;
				
				if ( box.close() ) 
					write( "Closed." );
				else 
					write( "The " + node.getIdentifier() + " is locked." );
			}
			else write( "The " + node.getIdentifier() + " cannot be closed." );
		}
	}
	
	public void lock( Node node, Node key ) {
		
		if ( isAccessible( node ) ) {
			if ( node instanceof LockableNode ) {
				LockableNode lock = (LockableNode) node;
				
				if ( lock.lock( key ) )
					write( "Locked." );
				else
					write( "You cannot lock the " + lock.getIdentifier() + " with the " + key.getIdentifier() + "." );
			}
			else write( "The " + node.getIdentifier() + " cannot be locked." );
		}
	}
	
	public void unlock( Node node, Node key ) {
		
		if ( isAccessible( node ) ) {
			if ( node instanceof LockableNode ) {
				LockableNode lock = (LockableNode) node;
				
				if ( lock.unlock( key ) )
					write( "Unlocked." );
				else
					write( "You cannot unlock the " + lock.getIdentifier() + " with the " + key.getIdentifier() + "." );
			}
			else write( "The " + node.getIdentifier() + " cannot be unlocked." );
		}
	}
	
	public void go( Direction direction ) {
		
		Room room = (Room) super.getParent();
		
		if ( room.canAccess( direction ) ) {
			this.move( room.getAdjacentRoom( direction ) );
			write( super.getParent().getDescription() );
		}
		else
			write( "You cannot go that way." );
	}
	
	private boolean isAccessible( Node node ) {
		
		switch ( getAccessibility( node ) ) {
		
		case ACCESSIBLE:
			return true;
			
		case OUTSIDE_THIS_ROOM:
			write( "There is no " + node.getIdentifier() + " in this room." );
			break;
			
		case ANCESTOR_CLOSED:
			write( "The " + node.getIdentifier() + " is inside a closed object." );
			break;
		}
		
		return false;
	}
	
	private Accessibility getAccessibility( Node node ) {
		
		if ( !super.getParent().contains( node ) )
			return Accessibility.OUTSIDE_THIS_ROOM;
		
		Node current = node.getParent();
		while ( current != super.getParent() ) {
			
			if ( current instanceof CloseableNode ) {
				
				if ( ((CloseableNode) current).isClosed() )
					return Accessibility.ANCESTOR_CLOSED;
			}
			
			if ( current.getParent() != null )
				current = current.getParent();
			else
				return Accessibility.OUTSIDE_THIS_ROOM; 
		}
		
		return Accessibility.ACCESSIBLE;
	}
}
