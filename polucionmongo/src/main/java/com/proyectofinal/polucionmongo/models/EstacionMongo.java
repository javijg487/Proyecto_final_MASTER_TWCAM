package com.proyectofinal.polucionmongo.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document
public class EstacionMongo {
	@Id
	private String _id;
	@JsonProperty("id")
	private Integer identificador; //Mismo de MySQL mi id para identificar
	@JsonProperty("timeStamp")
	private String timeStamp;
	@JsonProperty("nitricOxides")
	private Float nitricOxides;
	@JsonProperty("nitrogenDioxides")
	private Float nitrogenDioxides;
	@JsonProperty("VOCs_NMHC")
	private Float vocsNMHC;
	@JsonProperty("PM2_5")
	private Float pm25;

	public EstacionMongo() {}

	public EstacionMongo(Integer identificador, String timeStamp, Float nitricOxides,Float nitrogenDioxides, Float vocsNMHC, Float pm25) {
		this.identificador = identificador;
		this.timeStamp = timeStamp;
		this.nitricOxides = nitricOxides;
		this.nitrogenDioxides = nitrogenDioxides;
		this.vocsNMHC = vocsNMHC;
		this.pm25 = pm25;
	}

	public Integer getIdentificador() {
		return identificador;
	}

	public void setIdentificador(Integer identificador) {
		this.identificador = identificador;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
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
