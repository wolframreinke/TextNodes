package de.tungsten.textnodes.test;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.tungsten.textnodes.connect.IConnection;
import de.tungsten.textnodes.nodes.Node;
import de.tungsten.textnodes.nodes.NodeException;
import de.tungsten.textnodes.nodes.Player;
import de.tungsten.textnodes.nodes.Room;

public class PlayerTest {

	private static String lastMessage = null;
	
	private static class SilentConnection implements IConnection {

		@Override
		public void close() throws IOException {
		}

		@Override
		public boolean write( String message ) {
			lastMessage = message;
			return true;
		}

		@Override
		public String read() {
			return "";
		}

	}

	private static final String NAME_ROOM1 	= "room1";
	private static final String DESC_ROOM1 	= "room1desc";
	private static final String NAME_ROOM2 	= "room2";
	private static final String DESC_ROOM2 	= "room2desc";
	private static final String NAME_PROBE 	= "probe";
	private static final String DESC_PROBE 	= "probedesc";
	
	private static final String TEST_MSG   	= "testmsg";

	private static Player player;
	private static Room room1, room2;
	private static Node probe;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		player = new Player( new SilentConnection() );
		room1 = new Room( new String[] { NAME_ROOM1 }, DESC_ROOM1 );
		room2 = new Room( new String[] { NAME_ROOM2 }, DESC_ROOM2 );
		probe = new Node( new String[] { NAME_PROBE }, DESC_PROBE );

		Node root = new Node();
		root.addChild( room1.addChild( probe ) )
			.addChild( room2 );
	}

	@Before
	public void setUp() throws Exception {

		room1.addChild( player );
	}

	@After
	public void tearDown() throws Exception {

		room1.removeChild( player );
		room2.removeChild( player );
	}

	/* =========================================================== */

	@Test
	public void testMove() {

		try {
			player.move( room2 );
		}
		catch ( Exception e ) {
			fail( "Moving player into a Room failed." );
		}
		
		try {
			player.move( probe );
			fail( "Moving the player into a Node did not fail." );
		}
		catch ( NodeException e ) {}
	}

	@Test
	public void testSay() {
		
		player.say( TEST_MSG );
		Assert.assertEquals( lastMessage, TEST_MSG );
	}

	@Test
	public void testDescribe() {
		fail( "Not yet implemented" );
	}

	@Test
	public void testTake() {
		fail( "Not yet implemented" );
	}

	@Test
	public void testPut() {
		fail( "Not yet implemented" );
	}

	@Test
	public void testOpen() {
		fail( "Not yet implemented" );
	}

	@Test
	public void testClose() {
		fail( "Not yet implemented" );
	}

	@Test
	public void testLock() {
		fail( "Not yet implemented" );
	}

	@Test
	public void testUnlock() {
		fail( "Not yet implemented" );
	}

	@Test
	public void testGo() {
		fail( "Not yet implemented" );
	}

}
