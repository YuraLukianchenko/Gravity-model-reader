package com.lukianchenko.gravitymodelreader.service;

import com.lukianchenko.gravitymodelreader.domain.GravityModel;
import com.lukianchenko.gravitymodelreader.domain.GravityModelFileRow;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class GravityModelReader {

  private final static String BEGIN_OF_HEAD_STRING = "begin_of_head";
  private final static String END_OF_HEAD_STRING = "end_of_head";

  private final static String MODEL_NAME_STRING = "modelname";
  private final static String EARTH_GRAVITY_CONSTANT_STRING = "earth_gravity_constant";
  private final static String RADIUS_STRING = "radius";
  private final static String MAX_DEGREE_STRING = "max_degree";

  private final static int DEGREE_ORDER_IN_ROW = 1;
  private final static int ORDER_ORDER_IN_ROW = 2;
  private final static int COEFFICIENT_C_ORDER_IN_ROW = 3;
  private final static int COEFFICIENT_S_ORDER_IN_ROW = 4;
  private final static int DC_ORDER_IN_ROW = 5;
  private final static int DS_ORDER_IN_ROW = 6;

  private File file;

  public GravityModelReader(@Value("${file-name}") String filePath) {
    file = new File(filePath);
  }

  public GravityModel read() {
    try {
      GravityModel gravityModel = new GravityModel();
      gravityModel.setModelRows(new HashSet<>());
      FileReader fileReader = new FileReader(file);
      BufferedReader bufferedReader = new BufferedReader(fileReader);

      skipModelInfo(bufferedReader);
      readModelMetData(gravityModel, bufferedReader);
      readMainRowsWithCoefficients(gravityModel, bufferedReader);

      System.out.println(gravityModel.getName());
      System.out.println(gravityModel.getEarthGravityConstant());
      System.out.println(gravityModel.getRadius());
      System.out.println(gravityModel.getMaxDegree());
      System.out.println(gravityModel.getModelRows().size());

    } catch (FileNotFoundException e) {
      System.out.println("Gravity model file not found");
    } catch (IOException e) {
      System.out.println("Failed to read gravity model file");
    }
    return null;
  }

  private void readModelMetData(GravityModel gravityModel, BufferedReader bufferedReader)
      throws IOException {
    boolean flag = true;
    while (flag) {
      String line = bufferedReader.readLine();
      if (!StringUtils.isEmpty(line)) {
        String[] splittedString = line.split("\\s+");
        String splittedKey = StringUtils.trimAllWhitespace(splittedString[0]);
        String splittedValue = StringUtils.trimAllWhitespace(splittedString[1]);

        switch (splittedKey) {
          case MODEL_NAME_STRING:
            gravityModel.setName(splittedValue);
            break;
          case EARTH_GRAVITY_CONSTANT_STRING:
            gravityModel.setEarthGravityConstant(Float.parseFloat(splittedValue));
            break;
          case RADIUS_STRING:
            gravityModel.setRadius(Float.parseFloat(splittedValue));
            break;
          case MAX_DEGREE_STRING:
            gravityModel.setMaxDegree(Integer.parseInt(splittedValue));
            break;
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
      row.setDC(Float.parseFloat(splittedLine[DC_ORDER_IN_ROW]));
      row.setDS(Float.parseFloat(splittedLine[DS_ORDER_IN_ROW]));
      gravityModel.getModelRows().add(row);
      System.out.println(row.getRowNumber());
    }
  }
}
