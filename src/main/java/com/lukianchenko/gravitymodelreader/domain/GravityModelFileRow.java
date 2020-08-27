package com.lukianchenko.gravitymodelreader.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GravityModelFileRow {

    private int order;
    private int degree;
    private int CoefficientC;
    private int CoefficientS;
    private int dC;
    private int dS;
}
