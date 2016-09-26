package ua.khpi.model;

import java.io.Serializable;
import java.util.List;

public class TransferData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 666;
	
	private Double results;
	
	private String generatedLayers;

	public Double getResults() {
		return results;
	}

	public void setResults(Double results) {
		this.results = results;
	}

	public String getGeneratedLayers() {
		return generatedLayers;
	}

	public void setGeneratedLayers(String generatedLayers) {
		this.generatedLayers = generatedLayers;
	}

	@Override
	public String toString() {
		return "TransferData [results=" + results + ", generatedLayers=" + generatedLayers + "]";
	}

	
}
