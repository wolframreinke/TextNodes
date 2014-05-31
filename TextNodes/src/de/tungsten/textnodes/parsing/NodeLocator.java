package de.tungsten.textnodes.parsing;

import java.util.List;

import de.tungsten.textnodes.nodes.IPredicate;
import de.tungsten.textnodes.nodes.Node;
import de.tungsten.textnodes.nodes.Player;

public abstract class NodeLocator {

	public static Node findSubNode( final Class<? extends Node> type, Node parent, int depth ) {
		
		IPredicate<Node> classComparison = new IPredicate<Node>() {
			@Override
			public boolean matches( Node node ) {
				return node.getClass().isAssignableFrom( type );
			}
		};
		
		List<Node> result = parent.find( classComparison, depth );
		if ( result != null && result.size() > 0 )
			return result.get( 0 );
		else
			return null;
	}
	
	public static Node findSubNode( final String name, Node parent, int depth ) {

		IPredicate<Node> nameComparison = new IPredicate<Node>() {
			@Override
			public boolean matches( Node node ) {
				
				for (String nodeName : node.getNames()) {
					if ( nodeName.equals( name ) )
						return true;
				}
				return false;
			}
			
		};
		
		Node result = null;
		result = parent.findByIdentifier( name, depth );
		
		if ( result == null ) {
			
			List<Node> candidates = parent.find( nameComparison, depth );

			
			if ( candidates != null && candidates.size() > 0 )
				result = candidates.get( 0 ); 
		}
		
		return result;
	}
	public static Node findNodeAtPlayer( String name, Player player ) {

		Node result = findSubNode( name, player, -1 );

		if ( result == null )
			result = findSubNode( name, player.getParent(), 1 );
		
		return result;
	}

	public static Node findNodeUsingLocators( NodeLocation locators, Player player ) {

		boolean lookAtPlayer = true;
		Node currentNode = null;

		for (String locator : locators) {

			if ( lookAtPlayer ) {

				lookAtPlayer = false;
				currentNode = findNodeAtPlayer( locator, player );
			} else {
	
				currentNode = findSubNode( locator, currentNode, 1 );	
			}
			
			if ( currentNode == null )
					return null;
		}
		
		
		return currentNode;
		
	}
}
