package com.proyectofinal.ayuntamiento.models;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AgregadoMongoDTO {
	@JsonProperty("timeStamp")
	private String timeStamp;
	@JsonProperty("aggregatedData")
	private List<AggregatedDataDTO> aggregatedData;
	

	public AgregadoMongoDTO() {}

	public AgregadoMongoDTO(String timeStamp, List<AggregatedDataDTO> aggregatedData) {
		this.timeStamp = timeStamp;
		this.aggregatedData = aggregatedData;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public List<AggregatedDataDTO> getAggregatedData() {
		return aggregatedData;
	}

	public void setAggregatedData(List<AggregatedDataDTO> aggregatedData) {
		this.aggregatedData = aggregatedData;
	}
}
