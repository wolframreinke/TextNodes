package de.tungsten.textnodes.nodes;

/**
 * A search condition which can be applied to instances of the generic type
 * argument <code>T</code>. This interface contains one method, which tests
 * whether the given instance of <code>T</code> matches the condition.
 * 
 * @author tungsten
 * @version 1.0
 *
 * @param <T>	The type, the implemented search condition can be applied to.
 */
public interface IPredicate<T> {

	/**
	 * Tests, whether the given probe matches the search condition of
	 * this <code>IPredicate</code>. If so, this method returns <code>true</code>,
	 * if not, it returns <code>false</code>.
	 * 
	 * @param probe		The object to be tested.
	 * @return			<code>true</code>, if the given probe matches the search
	 * 					condition, <code>false</code> otherwise.
	 */
	public abstract boolean matches( T probe );
}
