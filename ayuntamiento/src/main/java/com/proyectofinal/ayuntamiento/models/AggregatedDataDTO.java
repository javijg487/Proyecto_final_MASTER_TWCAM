package com.proyectofinal.ayuntamiento.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AggregatedDataDTO {

	@JsonProperty("id")
	private Integer identificador; 
    @JsonProperty("average_bikesAvailable")
    private Float averageBikesAvailable;
	@JsonProperty("air_quality")
	private AirQualityDTO airQuality;


public AggregatedDataDTO() {}

	public AggregatedDataDTO(Integer identificador, Float averageBikesAvailable, AirQualityDTO airQuality) {
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

    public AirQualityDTO getAirQuality() {
        return airQuality;
    }

    public void setAirQuality(AirQualityDTO airQuality) {
        this.airQuality = airQuality;
    }

}
