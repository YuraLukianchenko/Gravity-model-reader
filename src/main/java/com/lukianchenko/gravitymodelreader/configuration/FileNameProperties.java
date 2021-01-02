package com.lukianchenko.gravitymodelreader.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("model-file-names")
public class FileNameProperties {

  private String etalon;
  private String compared;
  private String spectralAmplitudes;

  public String getEtalon() {
    return etalon;
  }

  public void setEtalon(String etalon) {
    this.etalon = etalon;
  }

  public String getCompared() {
    return compared;
  }

  public void setCompared(String compared) {
    this.compared = compared;
  }

  public String getSpectralAmplitudes() {
    return spectralAmplitudes;
  }

  public void setSpectralAmplitudes(String spectralAmplitudes) {
    this.spectralAmplitudes = spectralAmplitudes;
  }
}
