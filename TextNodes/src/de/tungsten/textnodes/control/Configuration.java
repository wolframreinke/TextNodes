package de.tungsten.textnodes.control;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Configuration {

	public static final String LOG_NAME = "(CORE) Configuration";

	private Map<String, String> values = null;

	private boolean useDefaultValues = false;

	private static Configuration instance = null;

	private Configuration() {
	}

	public static Configuration getInstance() {
		if ( instance == null )
			instance = new Configuration();

		return instance;
	}

	public Object getValue( String key, ConfigurationType type,
			Object defaultValue ) {

		if ( useDefaultValues )
			return defaultValue;

		// Wenn die Konfigurationsdatei noch nicht geladen wurde, wird
		// eine Exception geworfen.
		if ( values == null )
			throw new ConfigurationNotLoadedException();

		String value = values.get( key );

		if ( value == null ) {
			// Wert existiert nicht in der HashMap
			return defaultValue;
		}
		else {

			if ( type == null )
				return value;

			Object result = cast( value, type );

			if ( result == null ) {
				// Semantischer Fehler: Typen stimmen nicht �berein.
				Logger.getInstance().log(
						LogLevel.WARNING,
						LOG_NAME,
						"Semantic error in configuration file: \"" + key
								+ "\" must have the type " + type + "." );
				return defaultValue;
			}
			else {

				return result;
			}
		}
	}

	public void useDefaultValues() {
		useDefaultValues = true;
	}

	public void loadFromFile( String configFilePath ) {

		values = new HashMap<String, String>();

		if ( configFilePath == null )
			return;

		File configFile = new File( configFilePath );
		try {

			Logger.getInstance().log( LogLevel.INFO, LOG_NAME,
					"Loading configuration file \"" + configFilePath + "\"." );
			Scanner fileScanner;

			fileScanner = new Scanner( configFile );

			int line = 1;
			while ( fileScanner.hasNextLine() ) {

				String configLine = fileScanner.nextLine();
				Logger.getInstance().log(
						LogLevel.DEBUG,
						LOG_NAME,
						"Read configuration entry: \""
								+ configLine.replace( "\t", "" ) + "\"." );

				String[] tokens = configLine.split( "=" );

				if ( tokens.length == 2 ) {

					values.put( tokens[0].trim(), tokens[1].trim() );

				}
				else
					Logger.getInstance().log(
							LogLevel.WARNING,
							LOG_NAME,
							"Syntax error in configuration file at line "
									+ line + "." );

				line++;
			}

			fileScanner.close();
		}
		catch ( FileNotFoundException e ) {

			Logger.getInstance().log( LogLevel.WARNING, LOG_NAME,
					"No configuration file at " + configFilePath + "." );
		}
	}

	private Object cast( String object, ConfigurationType type ) {

		try {
			
			switch ( type ) {
			
			case INTEGER:
				return Integer.parseInt( object );
				
			case NUMERICAL:
				return Double.parseDouble( object );

			default:
				return object;
				
			}
		}
		catch ( Exception e ) {
			return null;
		}
	}
}
