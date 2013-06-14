package com.darkusha.jbdriver.steps;

import com.darkusha.jbdriver.pages.IndexPage;
import com.darkusha.jbdriver.pages.LoginPage;
import com.darkusha.jbdriver.pages.Pages;
import com.darkusha.jbdriver.pages.SeoAdminConsolePage;
import org.jbehave.core.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@Component
public class UISteps extends UIAbstractSteps{

    @Autowired
    Pages pages;

    @Autowired
    LoginPage loginPage;

    @Autowired
    IndexPage indexPage;

    @Autowired
    SeoAdminConsolePage seoAdminConsolePage;

    @Given("User is successfully logged in to the MASS service")
    public void openTheLoginPage() {
        loginPage.open();
        loginPage.authenticate();
        assertThat("The Login Page weren't opened", true, equalTo(loginPage.isThePageOpened()));
    }

    @When("User is redirected to the index page")
    public void isUserRedirectedToTheIndexPage(){
        assertThat("The Index Page weren't opened", true, equalTo(indexPage.isThePageOpened()));
    }

    @Then("the '$linkText' link is available from the 'Index' page")
    public void isAdminConsoleLinkAvailable(String linkText){
        assertThat("The Index doesn't contain the "+linkText+" link", true, equalTo(indexPage.isTheLinkAvailable(linkText)));
    }

    @When("User click on the '$linkText' link")
    public void whenUserClickOnTheLink(String linkText){
        indexPage.clickOnTheLink(linkText);
    }
    
    @Then("the 'SEO Admin Console' page is opened")
    public void isTheSeoAdminConsolePageOpened(){
        assertThat("The SEO Admin Console weren't opened", true, equalTo(seoAdminConsolePage.isThePageOpened()));
    }
    
    @Then("the '$tabName' Tab is presented on the page")
    public void isTabPresented(String tabName){
        assertThat("The SEO Admin Console doesn't contain the "+tabName+" Tab", true, equalTo(seoAdminConsolePage.isThePageContainTab(tabName)));
    }

    @Then("the '$tabName' Tab is opened by default")
    public void isTabDefault(String tabName){
        assertThat("The  "+tabName+" is not opened by default", true, equalTo(seoAdminConsolePage.isTabOpened(tabName)));
    }

    @Then("User opens the '$tabName' Tab")
    public void openTheTab(String tabName){
        seoAdminConsolePage.openTheTab(tabName);
    }

    @When("User try to filter by '$keyword' keyword")
    public void userTryToFilterByKeyword(String keyword){
        seoAdminConsolePage.setFilterKeyword(keyword);
    }

    @When("click the '$btnName' button")
    public void clickTheButton(String btnName){
        seoAdminConsolePage.clickTheButton(btnName);
    }

    @Then("table with results is displayed")
    public void isTableDisplayed(){
        assertThat("The table with results is not displayed", true, equalTo(seoAdminConsolePage.isTableDisplayed()));
    }

    @Then("the table contains the '$columnName' column")
    public void isColumnPresentedInTheTable(String columnName){
        assertThat("The Table does not contain the '"+columnName+"' column", true, equalTo(seoAdminConsolePage.isTheColumnDisplayed(columnName)));
    }

    @Then("the records in the table are sorted by '$columnName' '$sortingRule' by default")
    public void areRecordsSorted(@Named("columnName") String columnName,@Named("sorting") String sortingRule){
        assertThat("Records don't sorted appropriately", true, equalTo(seoAdminConsolePage.areRecordsSortedBy(columnName,sortingRule)));
    }

    @Then("the table is able to be sorted '$columnName' '$sortingRule'")
    public void isTableAbleToBeSorted(@Named("columnName") String columnName,@Named("sortingRule") String sortingRule){
        assertThat("The table is not able to be sorted by '"+columnName+"' '"+sortingRule+"'", true,
                equalTo(seoAdminConsolePage.isTableAbleToBeSortedBy(columnName,sortingRule)));
    }

    @Then("records do not sorted by any other column")
    public void areRecordsDontSortedByAnotherColumns(){
        assertThat("Records are sorted but should not", true, equalTo(seoAdminConsolePage.areOtherRecordsDontSoredInOtherColumns()));
    }

    @Then("'$btnName' button is presented on the screen")
    public void isTheButtonPresented(String btnName){
        assertThat("The "+btnName+" is not presented on the Page", true, equalTo(seoAdminConsolePage.isTheButtonPresented(btnName)));
    }

    @Then("the User is able the save results by clicking the '$btnName' button")
    public void isUserAbleToDownloadResults(String btnName){
        assertThat("The user is not able to save results", true, equalTo(seoAdminConsolePage.isUserAbleToSaveWith(btnName)));
    }
}