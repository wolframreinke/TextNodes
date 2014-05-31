package de.tungsten.textnodes.control;

public enum LogLevel {
	
	DEBUG	( 4, "[ DEBUG ]" ),
	INFO	( 3, "[ INFO  ]" ),
	WARNING	( 2, "[ WARN  ]" ),
	ERROR	( 1, "[ ERROR ]" ),
	FATAL	( 0, "[ FATAL ]" );
	
	private int priority;
	private String representation;
	
	LogLevel( int level, String representation ) {
		this.priority = level;
		this.representation = representation;
	}
	
	public static LogLevel fromPriority( int level ) {
		for (LogLevel l : LogLevel.values()) {
			if ( l.priority == level ) return l;
		}
		return DEBUG;
	}
	
	@Override
	public String toString() {
		return representation;
	}

	public int getPriority() {
		return priority;
	}
}
