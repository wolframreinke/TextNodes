package de.tungsten.textnodes.nodes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Node {
	
	/**
	 * <p>This static field is used to assign a unique ID ot to every node
	 * in a text-only game. Whenever a new node is created, the value of
	 * this attribute is assigned to the new node, and becomes incremented.</p>
	 * 
	 * <p>This attribute, together with the non-static {@link #id} can be
	 * used to unambiguously identify a node for debugging purposes.</p>
	 */
	private static int CURRENT_ID = Integer.MIN_VALUE;
	
	/**
	 * The unique identifier of this <code>Node</code>. This value is assigned,
	 * when this node was created. The id is immutable and can be used to
	 * unambiguously identify this node for debugging purposes.
	 */
	private final int id;
	
	/**
	 * <p>The names of this node. Each node has at least one name, which is the
	 * identifier of this node (not to be confused with the <code>id</code>). 
	 * This identifier is the string that is written to a connection, when 
	 * the game needs to refer to this node.</p>
	 * 
	 * <p>Additionally, a node may have an unbounded number of alternative names.
	 * As there is not necessarily a unique "word" for each object in the
	 * world, user might refer to this node using varying names. This attributes
	 * saves (hopefully) all names, a user could use to refer to this node.</p>
	 * 
	 * <p><b>Example:</b><br>
	 * The game world contains a cookie. There are several words, the user could 
	 * choose to refer to this cookie, for example "biscuit", "bickie", "snap"
	 * and others. To cover all possibilities, the <code>names</code> attribute
	 * for this case might look like this: <code>{ "cookie", "bisuit",
	 * "bickie", "snap" }</code>.</p>
	 */
	private String[] names;
	
	/**
	 * The description of this node. The description is a short text, which gives
	 * information about the appearance of this node. Some nodes may also
	 * give more essential information to the use, e.g. a <code>Room</code>
	 * could describe, where its doors are leading to.
	 */
	protected String description;
	
	/**
	 * <p>The parent-node of this node. This node is guaranteed to be a child-node
	 * of this parent-node. If this node is a/the root node, this value is
	 * <code>null</code>.</p>
	 * <p>The game world in text-only games is represented by a tree of nodes.
	 * This attribute is part of the bidirectional tree implementation.</p>
	 */
	protected Node parent;
	
	/**
	 * <p>The child-nodes of this node. This node is guaranteed to be the
	 * parent-node of each of its child-nodes.</p>
	 * <p>The game world in text-only games is represented by a tree of nodes.
	 * This attribute is part of the bidirectional tree implementation.</p>
	 */
	protected Set<Node> children;
	
	/**
	 * Creates a new root-<code>Node</code>, i.e. a node, that has no
	 * parent-node. The created node will have the name "root" and will have
	 * the description "The root node". To create a root with another name and/or
	 * description, use the constructor {@link #Node(String[], String)} instead.
	 */
	public Node() {
		this( new String[]{ "root" }, "The root node" );
	}

	/**
	 * <p>Creates a new root-<code>Node</code> with the given names and description.
	 * This constructor can be used in conjunction with {@link #addChild(Node)}.
	 * To add this node to another, already existing node, you can use this
	 * combination as follows:
	 * <pre>
	 * anotherNode.addChild( new Node( names, description ) );
	 * </pre>
	 * <code>addChild</code> will automatically set the newly created node's
	 * parent attribute.</p>
	 * 
	 * @param names			The names of this node. This array must at least
	 * 						contains one name (the identifier), otherwise a 
	 * 						<code>NodeException</code> is thrown.
	 * @param description	The description of this node. The description must
	 * 						not be <code>null</code>, otherwise a 
	 * 						<code>NodeException</code> is thrown.
	 */
	public Node( String[] names, String description ) {
		this( null, names, description );
	}
	
	/**
	 * Creates a new instance of <code>Node</code>. The newly created node will
	 * have the given names and description. Furthermore, it is added to the
	 * given parent node, if this parent-node is not <code>null</code>. Therefore,
	 * you do not have to explicitly call {@link #addChild(Node)}, when using
	 * this constructor. However, if you do, the parent-Node you pass to this
	 * constructor is simply overwritten by the <code>addChild</code> method.
	 * 
	 * @param parent		The parent-Node of this node. If <code>null</code> is
	 * 						used for this paremeter, the newly created node
	 * 						is a/the root-node.
	 * @param names			The names of the newly created node. This array must
	 * 						at least contain one name (the identifier), otherwise
	 * 						a <code>NodeException</code> is thrown.
	 * @param description	The description of the newly created node. This value
	 * 						must not be <code>null</code>, otherwise a 
	 * 						<code>NodeException</code> is thrown.
	 */
	public Node( Node parent, String[] names, String description ) {
		
		this.id = CURRENT_ID++;

		if ( names == null
				|| names.length < 1 
				|| description == null )
			throw new NodeException( this.id, "Node initialization failed." );
		
		this.names = names;
		
		this.parent = null;
		this.move( parent );
		
		this.description = description;
		normalizeDescription(); // Beschreibung normieren
		
		this.children = new HashSet<Node>();
	}

	/**
	 * Returns the unique {@link #id} of this node. This <code>id</code> can
	 * be used to unambiguously identify this node for debugging purposes.
	 * 
	 * @return	The unique <code>id</code> of this node.
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * <p>Returns the identifier of this node. The identifier is the first 
	 * {@link #names name} of this node.</p>
	 * 
	 * <p>Generally, the identifier is the string that should be written
	 * to connections when a component needs to refer to this node. It is the
	 * less ambiguous name of this node.</p>
	 * 
	 * @return	The (possibly ambiguous) idenifier of this node.
	 */
	public String getIdentifier() {
		return names[ 0 ];
	}

	/**
	 * <p>Returns the {@link #names} of this node. This names can be used
	 * by users to refer to this node. The returned array has at least one 
	 * element.</p>
	 * 
	 * @return	The names of this node.
	 */
	public String[] getNames() {
		return names;
	}
	
	/**
	 * <p>Returns the {@link #description} of this node. This short text gives 
	 * additional information about the appearance of this node. The returned 
	 * string is not <code>null</code>.</p> Attached to the description of this
	 * node, the output contains the names of all of its child nodes.
	 * 
	 * @return	The description of this node as well as the names of all
	 * 			direct subnodes.
	 */
	public String getDescription() {
		
		return getDescription( 1 );
	}
	
	/**
	 * <p>Returns the {@link #description} of this node. This short text gives 
	 * additional information about the appearance of this node. The returned 
	 * string is not <code>null</code>.</p> Attached to the description of this
	 * node, the output contains the names of all child-nodes, that are located
	 * at most <code>depth</code> layers below this node.
	 * 	
	 * @param depth		The maximum search depth.
	 * @return			The description of this node as well as the names of all
	 * 					subnodes that are located at most <code>depth</code> 
	 * 					layers below this node.
	 */
	public String getDescription( int depth ) {
		
		// TODO Not complemtely implemented
		
		String desc = description;
		
		if ( depth != 0 ) {
			
			for ( Node child : children ) {
				
				String prefix = "\n\t";
				for ( int i = 0; i < depth; i++ ) {
					prefix += "\t";
				}
				desc += prefix + child.getIdentifier();
				
			}
			
		}
		return desc;
	}
	
	/**
	 * <p>Returns the {@link #parent parent-node} of this node. If 
	 * <code>null</code> is returned, this node is a/the root-node.</p>
	 * 
	 * @return	The parent-node of this node.
	 */
	public Node getParent() {
		return parent;
	}

	/**
	 * Returns the set of {@link #children} of this node.
	 * 
	 * @return	The children of this node.
	 */
	public Set<Node> getChildren() {
		return children;
	}
	
	/**
	 * <p>Adds a node to the set of {@link #children} of this node. This node
	 * will be the new parent-node of the given node. If the given child is
	 * <code>null</code>, this method call is ignored.</p>
	 * 
	 * @param child		The node to add.
	 * @return			This node for chaining.
	 */
	public Node addChild( Node child ) {
		
		if ( child != null ) {
			
			child.parent = this;
			children.add( child );
		}
		
		return this;
	}

	/**
	 * Removes the given node from this node's set of {@link #children} and sets
	 * the given nodes parent-node to <code>null</code>.
	 * 
	 * @param child		The node to remove from this node.
	 */
	public void removeChild( Node child ) {
		
		if ( children.remove( child ) ) 
			child.parent = null;
	}

	public Node findByIdentifier( String identifier, int depth ) {
		
		return find( (x) -> x.getIdentifier() == identifier, depth ).get( 0 );
		
		// TODO Check if it works
		/*if ( names[ 0 ].equals( identifier ) ) return this;
		else {

			if ( depth == 0 ) return null;
			else {
				
				for (Node child : children) {
					
					Node result = child.findByIdentifier( identifier, depth - 1 );
					if ( result != null ) return result;
				}
				
			}
			
			return null;
		}*/
	}
	
	public List<Node> find( IPredicate<Node> predicate, int depth ) {
		
		List<Node> result = new ArrayList<Node>();
		
		if ( predicate.matches( this ) ) result.add( this );
		
		if ( depth == 0 ) return result;
		else {
			
			for (Node child : children) {
				result.addAll( child.find( predicate, depth - 1 ) );
			}
			
			return result;
		}
		
	}
	
	public synchronized void move( Node target ) {
		
		if ( target != null ) {
			
			// If this node has no parent, it can be moved though.
			if ( parent != null )
				parent.removeChild( this );
			
			target.addChild( this );
			
			parent = target;
		}
	}

	public Node getRoot() {
		if ( parent == null ) return this;
		else return parent.getRoot();
	}
	
	private void normalizeDescription() {
		
		while ( description.contains( "  " ) )
			description = description.replace( "  ", " " );
		
		description = description.replace( "\t", "" );
		description = description.replace( "\n ", "\n" );	

	}
	
	@Override
	public String toString() {
		return toString( 0 );
	}
	
	private String toString( int depth ) {
		String result = "";
		for ( int i = 0; i < depth; i++ ) {
			result += "\t";
		}
		
		result += getIdentifier();
		for ( Node child : getChildren() ) {
			result += "\n" + child.toString( depth + 1 );
		}
		
		return result;
	}
}
