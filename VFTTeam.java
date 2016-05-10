package vft;

 

import java.util.ArrayList;
import java.util.List;

public class VFTTeam {
	private String name;
	private List<VFTChar> characters;
	
	public VFTTeam(String name) {
		this.name = name;
		characters = new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}
	
	public List<VFTChar> getChars() {
		return characters;
	}
	public void addChar(VFTChar c) {
		characters.add(c);
	}
	public void delChar(VFTChar c) {
		characters.remove(c);
	}
	public boolean contains(VFTChar c) {
		return characters.contains(c);
	}
}
