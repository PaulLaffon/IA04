package sudoku;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Group9Cases {
	private Case[] cases;
	
	public Group9Cases() {}
	
	public Group9Cases(Case[] _cases) {
		cases = _cases;
	}
	
	public String toJSON() {
		ObjectMapper mapper = new ObjectMapper();
		String s = null;
		try {
			s = mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return s;
	}
	
	public static Group9Cases fromJSON(String s) {
		ObjectMapper mapper = new ObjectMapper();
		Group9Cases g = null;
		
		try {
			g = mapper.readValue(s, Group9Cases.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return g;
	}
	
	public boolean check() {
		Set<Integer> unique = new HashSet<Integer>();
		for(Case c : cases) {
			if(c.getValue() == 0)
				return false;
			else
				unique.add(c.getValue());
		}
		return unique.size() == 9;
	}
	
	public Case getCase(int index) { return cases[index]; }
	public Case[] getCases() { return cases; }
}
