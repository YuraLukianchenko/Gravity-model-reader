package com.lukianchenko.gravitymodelreader.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

  public GravityModelFileRow(int degree, int order, float coefficientC,
      float coefficientS, float diffC, float diffS) {
    this.degree = degree;
    this.order = order;
    this.coefficientC = coefficientC;
    this.coefficientS = coefficientS;
    this.diffC = diffC;
    this.diffS = diffS;
    degreeCount();
  }

  public void degreeCount() {
    this.rowNumber = degree * (degree + 1) / 2 + 1 + order;
  }
}
