package com.darkusha.jbdriver.steps;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.web.selenium.PropertyWebDriverProvider;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class UIAbstractSteps {

    @Autowired
    PropertyWebDriverProvider driverProvider;

    @BeforeScenario
    public void beforeScenario() {
        driverProvider.initialize();
    }

    @AfterScenario
    public void afterScenario() {
//      The following is workaround for error of jQuery and HTMLUnitDriver
//      http://sourceforge.net/tracker/index.php?func=detail&aid=3029700&group_id=47038&atid=448266
        if(!"htmlunit".equals(System.getProperty("browser")))
            driverProvider.get().quit();
    }
}
