package com.proyectofinal.ayuntamientomongo.models;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AggregatedData {
    @Id
	private String _id;
	@JsonProperty("id")
	private Integer identificador; 
    @JsonProperty("average_bikesAvailable")
    private Float averageBikesAvailable;
	@JsonProperty("air_quality")
	private AirQuality airQuality;


public AggregatedData() {}

	public AggregatedData(Integer identificador, Float averageBikesAvailable, AirQuality airQuality) {
		this.identificador = identificador;
		this.averageBikesAvailable = averageBikesAvailable;
        this.airQuality = airQuality;
	}

    public Integer getIdentificador() {
        return identificador;
    }

    public void setIdentificador(Integer identificador) {
        this.identificador = identificador;
    }

    public Float getAverageBikesAvailable() {
        return averageBikesAvailable;
    }

    public void setAverageBikesAvailable(Float averageBikesAvailable) {
        this.averageBikesAvailable = averageBikesAvailable;
    }

    public AirQuality getAirQuality() {
        return airQuality;
    }

    public void setAirQuality(AirQuality airQuality) {
        this.airQuality = airQuality;
    }

}
