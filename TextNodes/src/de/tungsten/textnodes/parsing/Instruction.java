package de.tungsten.textnodes.parsing;

import java.util.EnumMap;
import java.util.Map.Entry;
import java.util.Set;


public class Instruction {

	private String verb;

	private Set<NodeLocation> prefix;

	private EnumMap<Preposition, Set<NodeLocation>> arguments;
	
	public Instruction( 
			String verb, 
			Set<NodeLocation> prefix, 
			EnumMap<Preposition, Set<NodeLocation>> arguments ) {
		
		this.verb = verb;
		this.prefix = prefix;
		this.arguments = arguments;
	}

	public String getVerb() {
		return verb;
	}

	public Set<NodeLocation> getPrefix() {
		return prefix;
	}

	public EnumMap<Preposition, Set<NodeLocation>> getArguments() {
		return arguments;
	}
	
	@Override
	public String toString() {
		String result = "VERB =\n\t" + verb + "\nPREFIX =\n";
		for (NodeLocation location : prefix) {	
			result += "\t" + location + "\n";
		}
		
		result += "ARGUMENTS =\n";
		for (Entry<Preposition, Set<NodeLocation>> argument : arguments.entrySet()) {
			result += "\t" + argument.getKey().toString() + "\n";
			for (NodeLocation location : argument.getValue()) {
				result += "\t\t" + location + "\n";
			}
		}
		
		return result;
	}
}
