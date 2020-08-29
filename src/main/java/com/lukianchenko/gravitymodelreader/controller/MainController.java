package com.lukianchenko.gravitymodelreader.controller;

import com.lukianchenko.gravitymodelreader.domain.GravityModel;
import com.lukianchenko.gravitymodelreader.service.GravityModelReader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

  private GravityModelReader gravityModelReader;

  public MainController(GravityModelReader gravityModelReader) {
    this.gravityModelReader = gravityModelReader;
  }

  @GetMapping("/greetings")
  public ModelAndView greetings() {

    GravityModel gravityModel = gravityModelReader.read();

    ModelAndView mav = new ModelAndView();
    mav.addObject("line", "done");
    return mav;
  }

}
