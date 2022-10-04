package api;

import org.testng.annotations.Test;
import pages.LoginPage;

public class initTest extends BaseTest {

    @Test
    public void Test () {
        new LoginPage();
    }
}