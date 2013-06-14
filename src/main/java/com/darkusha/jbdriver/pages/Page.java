package com.darkusha.jbdriver.pages;

public interface Page {

    public void init();
    public void clickTheButton(String btnName);
    public boolean isThePageOpened();
}
