package com.lukianchenko.gravitymodelreader.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This is pojo for one row of gravity field model.
 *
 * @author Yura
 */
@Setter
@Getter
@NoArgsConstructor
public class GravityModelFileRow {

  private int rowNumber;
  private int degree;
  private int order;
  private float coefficientC;
  private float coefficientS;
  private float diffC;
  private float diffS;

  /**
   * This is constructor with parameters.
   *
   * @param degree of the gravity model;
   * @param order of the gravity model;
   * @param coefficientC harmonic coefficient C;
   * @param coefficientS harmonic coefficient S;
   * @param estimationC accuracy of C coefficient;
   * @param estimationS accuracy of S coefficient;
   * @author Yura
   */
  public GravityModelFileRow(int degree, int order, float coefficientC,
      float coefficientS, float estimationC, float estimationS) {
    this.degree = degree;
    this.order = order;
    this.coefficientC = coefficientC;
    this.coefficientS = coefficientS;
    this.diffC = estimationC;
    this.diffS = estimationS;
    degreeCount();
  }

  public void degreeCount() {
    this.rowNumber = degree * (degree + 1) / 2 + 1 + order;
  }
}
