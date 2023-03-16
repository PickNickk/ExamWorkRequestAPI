package Hooks;

import io.cucumber.java.Before;

public class CucumberHooks {
    @Before
    public void setUp() {
        new ApiHooks().beforeSetUp();
    }
}
