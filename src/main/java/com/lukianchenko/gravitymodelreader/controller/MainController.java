package com.lukianchenko.gravitymodelreader.controller;

import com.lukianchenko.gravitymodelreader.configuration.FileNameProperties;
import com.lukianchenko.gravitymodelreader.domain.GravityModel;
import com.lukianchenko.gravitymodelreader.service.GravityModelReader;
import com.lukianchenko.gravitymodelreader.service.ModelSpectralCharacteristicsBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

  private GravityModelReader gravityModelReader;
  private FileNameProperties fileNameProperties;

  public MainController(GravityModelReader gravityModelReader,
      FileNameProperties fileNameProperties) {
    this.gravityModelReader = gravityModelReader;
    this.fileNameProperties = fileNameProperties;
  }

  @GetMapping("/greetings")
  public ModelAndView greetings() {
//@Value("${file-name}")
    GravityModel etalonGravityModel = gravityModelReader.read(fileNameProperties.getEtalon());
    GravityModel comparedGravityModel = gravityModelReader.read(fileNameProperties.getCompared());

    ModelSpectralCharacteristicsBuilder characteristicsBuilder = new ModelSpectralCharacteristicsBuilder();
    characteristicsBuilder.buildAndSaveToFile(etalonGravityModel,
        comparedGravityModel,
        fileNameProperties.getSpectralAmplitudes());

    ModelAndView mav = new ModelAndView();
    mav.addObject("line", "done");
    return mav;
  }

}
