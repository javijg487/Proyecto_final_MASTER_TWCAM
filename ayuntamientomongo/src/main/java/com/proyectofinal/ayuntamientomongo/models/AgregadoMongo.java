package com.proyectofinal.ayuntamientomongo.models;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document
public class AgregadoMongo {
	@JsonProperty("timeStamp")
	private String timeStamp;
	@JsonProperty("aggregatedData")
	private List<AggregatedData> aggregatedData;
	

	public AgregadoMongo() {}

	public AgregadoMongo(String timeStamp, List<AggregatedData> aggregatedData) {
		this.timeStamp = timeStamp;
		this.aggregatedData = aggregatedData;
	}



	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public List<AggregatedData> getAggregatedData() {
		return aggregatedData;
	}

	public void setAggregatedData(List<AggregatedData> aggregatedData) {
		this.aggregatedData = aggregatedData;
	}
}
