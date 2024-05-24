package com.proyectofinal.ayuntamiento.models;


import com.fasterxml.jackson.annotation.JsonProperty;

public class AirQualityDTO {
    
	@JsonProperty("nitricOxides")
	private Float nitricOxides;
	@JsonProperty("nitrogenDioxides")
	private Float nitrogenDioxides;
	@JsonProperty("VOCs_NMHC")
	private Float vocsNMHC;
	@JsonProperty("PM2_5")
	private Float pm25;

	public AirQualityDTO() {}

	public AirQualityDTO(Float nitricOxides,Float nitrogenDioxides, Float vocsNMHC, Float pm25) {
		this.nitricOxides = nitricOxides;
		this.nitrogenDioxides = nitrogenDioxides;
		this.vocsNMHC = vocsNMHC;
		this.pm25 = pm25;
	}

	public Float getNitricOxides() {
		return nitricOxides;
	}

	public void setNitricOxides(Float nitricOxides) {
		this.nitricOxides = nitricOxides;
	}

	public Float getNitrogenDioxides() {
		return nitrogenDioxides;
	}

	public void setNitrogenDioxides(Float nitrogenDioxides) {
		this.nitrogenDioxides = nitrogenDioxides;
	}

	public Float getVOCsNMHC() {
		return vocsNMHC;
	}

	public void setVOCsNMHC(Float vocsNMHC) {
		this.vocsNMHC = vocsNMHC;
	}

	public Float getPM25() {
		return pm25;
	}

	public void setPM25(Float pm25) {
		this.pm25 = pm25;
	}
}
