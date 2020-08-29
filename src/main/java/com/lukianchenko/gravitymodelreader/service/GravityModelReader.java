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
      boolean flag = true;
      while (flag) {
        String line = bufferedReader.readLine();
        flag = !StringUtils.startsWithIgnoreCase(line, BEGIN_OF_HEAD_STRING);
      }
      flag = true;
      while (flag) {
        String line = bufferedReader.readLine();
        readModelParameters(line, gravityModel);
        flag = !StringUtils.startsWithIgnoreCase(line, END_OF_HEAD_STRING);
      }

      String line = "";
      while ((line = bufferedReader.readLine()) != null && line.length() != 0) {
        String[] splittedLine = line.split("\\s+");
        GravityModelFileRow row = new GravityModelFileRow();
        row.setDegree(Integer.parseInt(splittedLine[1]));
        row.setOrder(Integer.parseInt(splittedLine[2]));
        row.degreeCount();
        row.setCoefficientC(Float.parseFloat(splittedLine[3]));
        row.setCoefficientS(Float.parseFloat(splittedLine[4]));
        row.setDC(Float.parseFloat(splittedLine[5]));
        row.setDS(Float.parseFloat(splittedLine[6]));
        gravityModel.getModelRows().add(row);
        System.out.println(row.getRowNumber());
      }

      System.out.println(gravityModel.getName());
      System.out.println(gravityModel.getEarthGravityConstant());
      System.out.println(gravityModel.getRadius());
      System.out.println(gravityModel.getMaxDegree());
      System.out.println(gravityModel.getModelRows().size());

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  private void readModelParameters(String line, GravityModel gravityModel) {
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
  }
}
