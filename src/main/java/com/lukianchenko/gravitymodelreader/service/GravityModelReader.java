package com.lukianchenko.gravitymodelreader.service;

import com.lukianchenko.gravitymodelreader.domain.GravityModel;
import com.lukianchenko.gravitymodelreader.domain.GravityModelFileRow;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * This is service for reading files with gravity field model.
 *
 * @author Yura
 */
@Service
@Slf4j
public class GravityModelReader {

  private static final String BEGIN_OF_HEAD_STRING = "begin_of_head";
  private static final String END_OF_HEAD_STRING = "end_of_head";

  private static final String MODEL_NAME_STRING = "model_name";
  private static final String EARTH_GRAVITY_CONSTANT_STRING = "earth_gravity_constant";
  private static final String RADIUS_STRING = "radius";
  private static final String MAX_DEGREE_STRING = "max_degree";

  private static final int DEGREE_ORDER_IN_ROW = 1;
  private static final int ORDER_ORDER_IN_ROW = 2;
  private static final int COEFFICIENT_C_ORDER_IN_ROW = 3;
  private static final int COEFFICIENT_S_ORDER_IN_ROW = 4;
  private static final int DC_ORDER_IN_ROW = 5;
  private static final int DS_ORDER_IN_ROW = 6;

  private GravityModelReader() {

  }

  /**
   * Method which reads gravity model file.
   *
   * @param filePath path to gravity field model;
   * @return GravityModel
   * @author Yura
   */
  public GravityModel read(String filePath) {
    File file = new File(filePath);
    try (FileReader fileReader = new FileReader(file, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(fileReader)) {
      GravityModel gravityModel = new GravityModel();
      gravityModel.setModelRows(new HashSet<>());

      skipModelInfo(bufferedReader);
      readModelMetData(gravityModel, bufferedReader);
      readMainRowsWithCoefficients(gravityModel, bufferedReader);

      log.info(gravityModel.getName());
      log.info(String.valueOf(gravityModel.getEarthGravityConstant()));
      log.info(String.valueOf(gravityModel.getRadius()));
      log.info(String.valueOf(gravityModel.getMaxDegree()));
      log.info(String.valueOf(gravityModel.getModelRows().size()));

      return gravityModel;

    } catch (FileNotFoundException e) {
      log.error("Gravity model file not found");
    } catch (IOException e) {
      log.error("Failed to read gravity model file");
    }
    return null;
  }

  private void readModelMetData(GravityModel gravityModel, BufferedReader bufferedReader)
      throws IOException {
    boolean flag = true;
    while (flag) {
      String line = bufferedReader.readLine();
      if (!StringUtils.isEmpty(line)) {
        String[] separatedStrings = line.split("\\s+");
        String key = StringUtils.trimAllWhitespace(separatedStrings[0]);
        String value = StringUtils.trimAllWhitespace(separatedStrings[1]);

        switch (key) {
          case MODEL_NAME_STRING:
            gravityModel.setName(value);
            break;
          case EARTH_GRAVITY_CONSTANT_STRING:
            gravityModel.setEarthGravityConstant(Float.parseFloat(value));
            break;
          case RADIUS_STRING:
            gravityModel.setRadius(Float.parseFloat(value));
            break;
          case MAX_DEGREE_STRING:
            gravityModel.setMaxDegree(Integer.parseInt(value));
            break;
          default:
            throw new RuntimeException();
        }
      }
      flag = !StringUtils.startsWithIgnoreCase(line, END_OF_HEAD_STRING);
    }
  }

  private void skipModelInfo(BufferedReader bufferedReader) throws IOException {
    boolean flag = true;
    while (flag) {
      String line = bufferedReader.readLine();
      flag = !StringUtils.startsWithIgnoreCase(line, BEGIN_OF_HEAD_STRING);
    }
  }

  private void readMainRowsWithCoefficients(GravityModel gravityModel,
      BufferedReader bufferedReader) throws IOException {
    String line;
    while ((line = bufferedReader.readLine()) != null && line.length() != 0) {
      String[] splittedLine = line.split("\\s+");
      GravityModelFileRow row = new GravityModelFileRow();
      row.setDegree(Integer.parseInt(splittedLine[DEGREE_ORDER_IN_ROW]));
      row.setOrder(Integer.parseInt(splittedLine[ORDER_ORDER_IN_ROW]));
      row.degreeCount();
      row.setCoefficientC(Float.parseFloat(splittedLine[COEFFICIENT_C_ORDER_IN_ROW]));
      row.setCoefficientS(Float.parseFloat(splittedLine[COEFFICIENT_S_ORDER_IN_ROW]));
      row.setDiffC(Float.parseFloat(splittedLine[DC_ORDER_IN_ROW]));
      row.setDiffS(Float.parseFloat(splittedLine[DS_ORDER_IN_ROW]));
      gravityModel.getModelRows().add(row);
    }
  }
}
