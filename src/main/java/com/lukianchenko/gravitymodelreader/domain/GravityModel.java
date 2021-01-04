package com.lukianchenko.gravitymodelreader.domain;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * This is simple pojo for gravity field model.
 *
 * @author Yura
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GravityModel {

  private String name;
  private float earthGravityConstant;
  private float radius;
  private int maxDegree;
  private Set<GravityModelFileRow> modelRows;
}
