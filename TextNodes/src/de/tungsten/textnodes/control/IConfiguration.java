package de.tungsten.textnodes.control;

/**
 * Implementations of <code>IConfiguration</code> provide configuration
 * information ot other components. Where they retrieve this information from
 * is not specified.
 * 
 * @author tungsten
 * @version 1.0
 *
 */
public interface IConfiguration {

	/**
	 * Returns a configuration entry.
	 * 
	 * @param key
	 * @param type
	 * @param defaultValue
	 * @return
	 */
	public abstract Object getEntry( String key, ConfigurationType type,
			Object defaultValue );
}
