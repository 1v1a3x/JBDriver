package com.darkusha.jbdriver.pages;

import org.jbehave.web.selenium.PropertyWebDriverProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SeoAdminConsolePage implements Page{
    private PropertyWebDriverProvider driverProvider;

    public static final String PAGE_URL = System.getProperty("test.server.url")+"web/admin/";

    @Autowired
    public SeoAdminConsolePage(PropertyWebDriverProvider driverProvider) {
        this.driverProvider = driverProvider;
    }

    @Override
    public void init() {
        PageFactory.initElements(driverProvider.get(), this);
    }

    @Override
    public void clickTheButton(String btnName) {
        driverProvider.get().findElement(By.xpath("//div[@class='ui-tabs-panel ui-widget-content ui-corner-bottom']/*//input[@value='" + btnName + "']")).click();

    }

    @Override
    public boolean isThePageOpened() {
        return driverProvider.get().getCurrentUrl().toLowerCase().contains(PAGE_URL);
    }

    public boolean isThePageContainTab(final String tabName){
/*        Enable the following block in case test waits for a condition                             */
//        new WebDriverWait(driverProvider.get(),10).until(new ExpectedCondition<Boolean>() {
//            @Override
//            public Boolean apply(WebDriver driver) {
//                driverProvider.get().findElement(By.xpath("//a[contains(text(),'"+tabName+"')]"));
//                return true;
//            }
//        });
        return driverProvider.get().findElement(By.xpath("//a[contains(text(),'"+tabName+"')]")).isDisplayed();
    }
    
    public boolean isTabOpened(String tabName){
        return driverProvider.get().findElement(By.xpath("//li[@class='ui-state-default ui-corner-top ui-tabs-selected ui-state-active']/a[text()='"+tabName+"']")).isDisplayed();
    }
    
    public void openTheTab(String tabName){
        driverProvider.get().findElement(By.xpath("//li[@class='ui-state-default ui-corner-top']/a[text()='"+tabName+"']")).click();
    }

    public void setFilterKeyword(String keyword) {
        driverProvider.get().findElement(By.xpath("//div[@class='ui-tabs-panel ui-widget-content ui-corner-bottom']/*//input[@type='text']")).sendKeys(keyword);
    }

    public boolean isTableDisplayed() {
        return driverProvider.get().findElement(By.xpath("//div[@class='ui-tabs-panel ui-widget-content ui-corner-bottom']/*//table")).isDisplayed();
    }

    public boolean isTheColumnDisplayed(String columnName) {
        return driverProvider.get().findElement(By.xpath("//div[@class='ui-tabs-panel ui-widget-content ui-corner-bottom']/*//th[contains(text(), '" + columnName + "')]")).isDisplayed();
    }

    public boolean areRecordsSortedBy(String columnName, String sortingRule) {
        String sortingCriterion = "ASCENDING";
        if (sortingRule.equals(sortingCriterion)){
            sortingCriterion = "DESCENDING";
        }
        return driverProvider.get().findElement(By.xpath("//div[@class='ui-tabs-panel ui-widget-content ui-corner-bottom']/*//th[contains(text(), '" + columnName + "') and contains(@onclick, 'sortingOrder=" + sortingCriterion + "')]")).isDisplayed();
    }

    public boolean areOtherRecordsDontSoredInOtherColumns() {
        List<WebElement> list = driverProvider.get().findElements(By.xpath("//div[@class='ui-tabs-panel ui-widget-content ui-corner-bottom']/*//th[@class='headerSort header ']"));
        if (list.size() ==1){
            return false;
        }
        return true;
    }

    public boolean isTableAbleToBeSortedBy(String columnName, String sortingRule) {
        List<WebElement> list = driverProvider.get().findElements(By.xpath("//div[@class='ui-tabs-panel ui-widget-content ui-corner-bottom']/*//th[contains(@class,'headerSort header') and not(contains(text(),'"+columnName+"'))]"));
        list.get(1).click();

        if(sortingRule.toUpperCase().equals("ASCENDING")){
            driverProvider.get().findElement(By.xpath("//div[@class='ui-tabs-panel ui-widget-content ui-corner-bottom']/*//th[contains(@class,'headerSort header ') and contains(text(),'"+columnName+"')]")).click();
            driverProvider.get().findElement(By.xpath("//div[@class='ui-tabs-panel ui-widget-content ui-corner-bottom']/*//th[contains(@class,'headerSort header headerSortUp') and contains(text(),'" + columnName + "')]"));
            return true;
        }
        if(sortingRule.toUpperCase().equals("DESCENDING")){
            driverProvider.get().findElement(By.xpath("//div[@class='ui-tabs-panel ui-widget-content ui-corner-bottom']/*//th[contains(@class,'headerSort header ') and contains(text(),'"+columnName+"')]")).click();
            driverProvider.get().findElement(By.xpath("//div[@class='ui-tabs-panel ui-widget-content ui-corner-bottom']/*//th[contains(@class,'headerSort header ') and contains(text(),'" + columnName + "')]")).click();
            driverProvider.get().findElement(By.xpath("//div[@class='ui-tabs-panel ui-widget-content ui-corner-bottom']/*//th[contains(@class,'headerSort header headerSortDown') and contains(text(),'" + columnName + "')]"));
            return true;
        }
        else return false;
    }


    public boolean isTheButtonPresented(String btnName) {
        return driverProvider.get().findElement(By.xpath("//input[@type='button' and @value='" + btnName + "']")).isDisplayed();
    }

    public boolean isUserAbleToSaveWith(String btnName) {
        return driverProvider.get().findElement(By.xpath("//input[@type='button' and @value='" + btnName + "' and contains(@onclick,'http://some.') and contains(@onclick,'"+btnName+"')]")).isDisplayed();
    }
}

