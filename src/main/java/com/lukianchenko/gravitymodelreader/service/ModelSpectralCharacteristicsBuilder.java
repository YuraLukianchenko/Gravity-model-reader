package com.lukianchenko.gravitymodelreader.service;

import com.lukianchenko.gravitymodelreader.domain.GravityModel;
import com.lukianchenko.gravitymodelreader.domain.GravityModelFileRow;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * This is service which builds file with spectral characteristics of models comparision.
 *
 * @author Yura
 */
public class ModelSpectralCharacteristicsBuilder {

  /**
   * Creates and saves spectral amplitudes to the file.
   *
   * @param standardGravityModel standard gravity field model;
   * @param comparedGravityModel tested gravity field model;
   * @param filePath path to file where spectral characteristics will be saved;
   * @author Yura
   */
  public void buildAndSaveToFile(GravityModel standardGravityModel,
      GravityModel comparedGravityModel,
      String filePath) {
    File file = new File(filePath);
    try (FileWriter fileWriter = new FileWriter(file, StandardCharsets.UTF_8);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
      int etalonModelDegree = standardGravityModel.getMaxDegree();
      int comparedModelDegree = comparedGravityModel.getMaxDegree();
      double[] etalonSpectralAmplitudes = new double[etalonModelDegree + 1];
      double[] comparedSpectralAmplitudes = new double[comparedModelDegree + 1];
      double[] differences = new double[comparedModelDegree + 1];
      double[] accumulatedDifferencesOfSpectralAmplitudes = new double[comparedModelDegree + 1];

      GravityModelFileRow[] etalonRows = new GravityModelFileRow[
          (etalonModelDegree + 1) * (etalonModelDegree + 2) / 2 + 1];
      GravityModelFileRow[] comparedRows = new GravityModelFileRow[
          (comparedModelDegree + 1) * (comparedModelDegree + 2) / 2 + 1];

      standardGravityModel.getModelRows()
          .forEach(row -> etalonRows[row.getRowNumber()] = row);
      comparedGravityModel.getModelRows()
          .forEach(row -> comparedRows[row.getRowNumber()] = row);

      int commonModelDegree =
          Math.min(etalonModelDegree, comparedModelDegree);

      for (int i = 0; i < commonModelDegree; i++) {
        for (int j = 0; j <= i; j++) {
          etalonSpectralAmplitudes[i] +=
              Math.pow(etalonRows[lineNumber(i, j)].getCoefficientC(), 2)
                  + Math.pow(etalonRows[lineNumber(i, j)].getCoefficientS(), 2);
          comparedSpectralAmplitudes[i] +=
              Math.pow(comparedRows[lineNumber(i, j)].getCoefficientC(), 2)
                  + Math.pow(comparedRows[lineNumber(i, j)].getCoefficientS(), 2);
          differences[i] += Math.pow(
              etalonRows[lineNumber(i, j)].getCoefficientC() - comparedRows[lineNumber(i, j)]
                  .getCoefficientC(), 2)
              + Math.pow(
              etalonRows[lineNumber(i, j)].getCoefficientS() - comparedRows[lineNumber(i, j)]
                  .getCoefficientS(), 2);
        }
        accumulatedDifferencesOfSpectralAmplitudes[i] = Math.sqrt(differences[i]) + (i == 0 ? 0
            : accumulatedDifferencesOfSpectralAmplitudes[i - 1]);
        bufferedWriter.write(
            i + " " + Math.sqrt(etalonSpectralAmplitudes[i]) * standardGravityModel.getRadius()
                + " " + Math.sqrt(comparedSpectralAmplitudes[i]) * comparedGravityModel.getRadius()
                + " " + Math.sqrt(differences[i]) * comparedGravityModel.getRadius()
                + " " + accumulatedDifferencesOfSpectralAmplitudes[i] * standardGravityModel
                .getRadius());
        bufferedWriter.newLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private int lineNumber(int degree, int order) {
    return degree * (degree + 1) / 2 + 1 + order;
  }
}
