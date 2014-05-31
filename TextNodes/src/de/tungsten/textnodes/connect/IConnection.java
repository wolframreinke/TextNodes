package de.tungsten.textnodes.connect;

import java.io.Closeable;

public interface IConnection extends Closeable  {


	public static final char MESSAGE_SEPARATOR = 0x17;
	public static final String MESSAGE_SEPARATOR_ESCAPE = "%0x17;";
	

	public boolean write( String message );
	
	public String read();
}
