package ua.khpi.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The class used for transfering parents data.
 * 
 * @author Anton
 *
 */
public class Parents {

	List<TransferData> father;
	
	List<TransferData> mother;

	public List<TransferData> getFather() {
		return father;
	}

	public void setFather(List<TransferData> father) {
		this.father = father;
	}

	public List<TransferData> getMother() {
		return mother;
	}

	public void setMother(List<TransferData> mother) {
		this.mother = mother;
	}
	
	
	
}
