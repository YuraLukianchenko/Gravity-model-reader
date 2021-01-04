package com.lukianchenko.gravitymodelreader.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * This is simple configuration properties contains names of two models which  will be compared and
 * name of spectral amplitudes file where result of comparision will be saved.
 *
 * @author Yura
 */
@Configuration
@ConfigurationProperties("model-file-names")
public class FileNameProperties {

  private String standard;
  private String compared;
  private String spectralAmplitudes;

  public String getStandard() {
    return standard;
  }

  public void setStandard(String standard) {
    this.standard = standard;
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
