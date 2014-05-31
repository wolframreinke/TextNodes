package de.tungsten.textnodes.connect;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

import de.tungsten.textnodes.control.Configuration;
import de.tungsten.textnodes.control.ConfigurationType;
import de.tungsten.textnodes.control.LogLevel;
import de.tungsten.textnodes.control.Logger;


public abstract class Server extends Thread {
	
	private static final String LOG_NAME		= "(CORE) Server";
	
	private static final String CONFIG_PORT		= "port";
	private static final int	DEFAULT_PORT	= 10024;
	
	private final Configuration configuration;
	private final Logger logger;
	
	private Set<IConnection> connections = new HashSet<IConnection>();
	
	public Server( Configuration config, Logger logger ) {
		this.configuration = config;
		this.logger = logger;
	}			
	
	@Override
	public void run() {
		
		try {
			int port = (int) configuration.getValue( 
					CONFIG_PORT, ConfigurationType.INTEGER, DEFAULT_PORT );
			
			ServerSocket server = new ServerSocket( port );
			logger.log( LogLevel.INFO, LOG_NAME, "Listening at port " + port + "." );
			
			while ( !isInterrupted() ) {
				
				Socket client = server.accept();
				
				logger.log( LogLevel.INFO, LOG_NAME, "New Connection to " + client.getInetAddress() + "." );
				
				IConnection connection = createConnection( client );
				connections.add( connection );
			}
			
			server.close();
			logger.log( LogLevel.INFO, LOG_NAME, "Server down." );
			
		} catch (IOException e) {
			logger.log( LogLevel.FATAL, LOG_NAME, "Couldn't set up server. Detailed Message: \"" + e.getMessage() +"\"." );
		}
	}
	
	public void shutDown() {
		
		for ( IConnection current : connections ) {
			try {
				current.close();
			}
			catch ( IOException e ) {}
		}
		interrupt(); 	
	}
	
	protected abstract IConnection createConnection( Socket socket );
}
