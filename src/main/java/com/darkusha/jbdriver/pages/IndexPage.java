package com.darkusha.jbdriver.pages;

import org.jbehave.web.selenium.PropertyWebDriverProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IndexPage implements Page {

    private PropertyWebDriverProvider driverProvider;

    public static final String PAGE_URL = System.getProperty("test.server.url")+"index";

    @Autowired
    public IndexPage(PropertyWebDriverProvider driverProvider) {
        this.driverProvider = driverProvider;
    }

    @Override
    public void init() {
        PageFactory.initElements(driverProvider.get(), this);
    }

    @Override
    public void clickTheButton(String btnName) {
        driverProvider.get().findElement(By.xpath("//input[@value='" + btnName + "']"));

    }

    @Override
    public boolean isThePageOpened() {
        return driverProvider.get().getCurrentUrl().toLowerCase().contains(PAGE_URL);
    }
    
    public boolean isTheLinkAvailable(String linkText){
        return driverProvider.get().findElement(By.linkText(linkText)).isDisplayed() && driverProvider.get().findElement(By.linkText(linkText)).isEnabled();
    }

    public void clickOnTheLink(String linkText) {
        driverProvider.get().findElement(By.linkText(linkText)).click();
    }
}