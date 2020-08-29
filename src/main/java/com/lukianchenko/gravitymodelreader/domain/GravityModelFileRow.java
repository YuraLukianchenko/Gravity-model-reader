package com.lukianchenko.gravitymodelreader.domain;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GravityModelFileRow {

  private int rowNumber;
  private int degree;
  private int order;
  private float coefficientC;
  private float coefficientS;
  private float dC;
  private float dS;

  public GravityModelFileRow(int degree, int order, float coefficientC,
      float coefficientS, float dC, float dS) {
    this.degree = degree;
    this.order = order;
    this.coefficientC = coefficientC;
    this.coefficientS = coefficientS;
    this.dC = dC;
    this.dS = dS;
    this.rowNumber = degreeCount(degree);
  }

  private int degreeCount(int degree) {
    return degree * (degree + 1) / 2 + 1;
  }
}
