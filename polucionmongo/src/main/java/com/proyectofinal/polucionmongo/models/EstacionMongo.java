package com.proyectofinal.polucionmongo.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document
public class EstacionMongo {

	@Id
	private Integer id; //Mismo de MySQL mi id para identificar
	@JsonProperty("timeStamp")
	private String timeStamp;
	@JsonProperty("nitricOxides")
	private Float nitricOxides;
	@JsonProperty("nitrogenDioxides")
	private Float nitrogenDioxides;
	@JsonProperty("VOCs_NMHC")
	private Float vocsNMHC;
	@JsonProperty("PM2_5")
	private Float PM25;

	public EstacionMongo() {}

	public EstacionMongo(Integer id, String timeStamp, Float nitricOxides,Float nitrogenDioxides, Float vocsNMHC, Float PM25) {
		this.id = id;
		this.timeStamp = timeStamp;
		this.nitricOxides = nitricOxides;
		this.nitrogenDioxides = nitrogenDioxides;
		this.vocsNMHC = vocsNMHC;
		this.PM25 = PM25;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Float getPm2_5() {
		return PM25;
	}

	public void setPm2_5(Float PM25) {
		this.PM25 = PM25;
	}
  
}
