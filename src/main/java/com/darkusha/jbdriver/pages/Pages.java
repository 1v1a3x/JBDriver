package com.darkusha.jbdriver.pages;

import org.jbehave.web.selenium.PropertyWebDriverProvider;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Pages {
    private final PropertyWebDriverProvider driverProvider;

    @Autowired
    public Pages(PropertyWebDriverProvider driverProvider) {
        this.driverProvider = driverProvider;
    }

    public Page getCurrentPage() {
        WebDriver driver = driver();

        String url = driver.getCurrentUrl();

        // matcher
        if (url.contains(LoginPage.PAGE_URL)) {
            return getLoginPage();
        }

        if (url.contains(IndexPage.PAGE_URL)){
            return getIndexPage();
        }

        if (url.contains(SeoAdminConsolePage.PAGE_URL)){
            return getSeoAdminConsolePage();
        }

        throw new RuntimeException("No page object found for url " + url);

    }

    private LoginPage getLoginPage() {
        LoginPage loginPage = new LoginPage(driverProvider);
        loginPage.init();
        return loginPage;
    }

    private IndexPage getIndexPage() {
        IndexPage indexPage = new IndexPage(driverProvider);
        indexPage.init();
        return indexPage;
    }

    private SeoAdminConsolePage getSeoAdminConsolePage() {
        SeoAdminConsolePage seoAdminConsolePage = new SeoAdminConsolePage(driverProvider);
        seoAdminConsolePage.init();
        return seoAdminConsolePage;
    }

    private WebDriver driver() {
        return driverProvider.get();
    }
}
