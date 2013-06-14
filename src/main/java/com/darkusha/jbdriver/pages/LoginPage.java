package com.darkusha.jbdriver.pages;

import org.jbehave.web.selenium.PropertyWebDriverProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginPage implements Page {

    public static final String PAGE_URL = System.getProperty("test.server.url");
    @FindBy(name = "username")
    private WebElement fldUsername;
    @FindBy(name = "password")
    private WebElement fldPassword;
    @FindBy(xpath = "//input[@type='submit']")
    private WebElement btnLogin;
    @FindBy(xpath = "//input[@type='reset']")
    private WebElement btnClear;

    private PropertyWebDriverProvider driverProvider;

    @Autowired
    public LoginPage(PropertyWebDriverProvider driverProvider) {
        this.driverProvider = driverProvider;
    }

    public void open() {
        driverProvider.get().get(PAGE_URL);
        init();
    }

    @Override
    public void init() {
        PageFactory.initElements(driverProvider.get(), this);
    }

    public String getTitle(){
        return driverProvider.get().getTitle();
    }

    public boolean isThePageOpened() {
        return driverProvider.get().getCurrentUrl().toLowerCase().contains(PAGE_URL);
    }


    public void authenticate(){
        fldUsername.sendKeys(System.getProperty("user.login"));
        fldPassword.sendKeys(System.getProperty("user.password"));
        btnLogin.click();
//        TODO remove the following line. It is just a workaround for favicon issue
        driverProvider.get().navigate().to(PAGE_URL);
    }

    @Override
    public void clickTheButton(String btnName) {
        driverProvider.get().findElement(By.xpath("//input[@value='"+btnName+"']"));
    }
}