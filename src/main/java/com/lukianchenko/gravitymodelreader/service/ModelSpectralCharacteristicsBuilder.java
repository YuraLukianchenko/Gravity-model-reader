package com.lukianchenko.gravitymodelreader.service;

import com.lukianchenko.gravitymodelreader.domain.GravityModel;
import com.lukianchenko.gravitymodelreader.domain.GravityModelFileRow;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ModelSpectralCharacteristicsBuilder {

  public void buildAndSaveToFile(GravityModel gravityModel, String filePath) {
    File file = new File(filePath);
    try (FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
      int modelDegree = gravityModel.getMaxDegree();
      float[] spectralAmplitudes = new float[modelDegree + 1];
      GravityModelFileRow[] rows = new GravityModelFileRow[(modelDegree + 1) * (modelDegree + 2) / 2
          + 1];
      gravityModel.getModelRows().stream().forEach(row -> rows[row.getRowNumber()] = row);
      for (int i = 0; i < spectralAmplitudes.length; i++) {
        for (int j = 0; j <= i; j++) {
          spectralAmplitudes[i] += Math.sqrt(
              Math.pow(rows[lineNumber(i, j)].getCoefficientC(), 2) +
                  Math.pow(rows[lineNumber(i, j)].getCoefficientS(), 2));
        }
        bufferedWriter.write(i + " " + spectralAmplitudes[i] * gravityModel.getRadius());
        bufferedWriter.newLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void buildAndSaveToFile(GravityModel gravityModel1, GravityModel gravityModel2,
      String filePath) {

  }

  private int lineNumber(int degree, int order) {
    return degree * (degree + 1) / 2 + 1 + order;
  }
}
