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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((generatedLayers == null) ? 0 : generatedLayers.hashCode());
		result = prime * result + ((results == null) ? 0 : results.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransferData other = (TransferData) obj;
		if (generatedLayers == null) {
			if (other.generatedLayers != null)
				return false;
		} else if (!generatedLayers.equals(other.generatedLayers))
			return false;
		if (results == null) {
			if (other.results != null)
				return false;
		} else if (!results.equals(other.results))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TransferData [results=" + results + ", generatedLayers=" + generatedLayers + "]";
	}

	
	
}
