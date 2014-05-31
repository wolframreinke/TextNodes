package de.tungsten.textnodes.control;

@SuppressWarnings("serial")
public class ConfigurationNotLoadedException extends RuntimeException {

	public ConfigurationNotLoadedException() {
		super( "Configuration has not been loaded before first access." );
	}
}
