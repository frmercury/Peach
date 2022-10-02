package api;

import org.testng.annotations.Test;
import pages.LoginPage;

public class initTest extends BaseTest {

    @Test
    public void Test () throws InterruptedException {

        Thread.sleep(15000);
        new LoginPage();

    }
}
