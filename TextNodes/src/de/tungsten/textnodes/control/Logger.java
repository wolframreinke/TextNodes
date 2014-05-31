package de.tungsten.textnodes.control;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Logger {

	private class OutputChannel {
		
		private OutputStreamWriter writer;
		private LogLevel logLevel;
		
		public OutputChannel( OutputStreamWriter writer, LogLevel logLevel ) {
			super();
			this.writer = writer;
			this.logLevel = logLevel;
		}
		
		public LogLevel getLogLevel() {
			return logLevel;
		}
		
		public void write( String message ) throws IOException {
			writer.write( message );
			writer.flush();
		}
	}

	private Set<OutputChannel> outputChannels;
	
	// Singleton implementation
	private static Logger instance = null;
	private Logger() {
		outputChannels = new HashSet<OutputChannel>();
	}
	
	public static Logger getInstance() {
		if ( instance == null )
			instance = new Logger();
		
		return instance;
	}
	
	public void addOutputChannel( int priority, OutputStream stream ) {
		LogLevel level = LogLevel.fromPriority( priority );
		addOutputChannel( level, stream );
	}
	
	public void addOutputChannel( LogLevel logLevel, OutputStream stream ) {
		
		outputChannels.add( new OutputChannel( new OutputStreamWriter( stream ), logLevel ) );
	}	
	
	public synchronized void log( int level, String clientName, String message ) {
		log( LogLevel.fromPriority( level ), clientName, message );
	}
	
	public synchronized void log( LogLevel level, String clientName, String message ) {
		
		final String output = level + " " + (new Date()) + " - " + clientName + ": " + message;
		
		for ( OutputChannel outputChannel : outputChannels ) {
			
			try {
				
				if ( outputChannel.getLogLevel().getPriority() <= level.getPriority() )
					outputChannel.write( output );
				
			}
			catch ( IOException e ) {}
				
		}
	}
}
