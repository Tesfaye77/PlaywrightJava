package base;

import Factory.PlaywrightFactory;
import Pages.HomePage;
import com.aventstack.extentreports.gherkin.model.Scenario;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import io.qameta.allure.Allure;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.io.File;
import java.nio.file.Paths;
import java.util.Properties;

public class BaseTest {
    PlaywrightFactory pf;
    Page page;
    protected Properties prop;

    protected HomePage homePage;



    @Parameters({ "browser","username","password" })
    @BeforeTest
    public void setup(String browserName,String userName, String passWord) {
        pf = new PlaywrightFactory();
        prop = pf.init_prop();

        if (browserName != null) {
            prop.setProperty("browser", browserName);
            prop.setProperty("username", userName);
            prop.setProperty("password", passWord);
        }

        page = pf.initBrowser(prop);
        homePage = new HomePage(page);

        File index = new File("C:/Users/Pawk39/IdeaProjects/Playwrite/allure-results");
        if (index.exists()) {
            String[]entries = index.list();
            for(String s: entries){
                File currentFile = new File(index.getPath(),s);
                currentFile.delete();
            }
            index.delete();
        }

        File index1 = new File("C:/Users/Pawk39/.jenkins/workspace/ZtrainPipeline/allure-results");
        if (index1.exists()) {
            String[]entries = index1.list();
            for(String s: entries){
                File currentFile = new File(index1.getPath(),s);
                currentFile.delete();
            }
            index1.delete();
        }
//        File index2 = new File("C:/Users/hambe/Desktop/playwrightDemo/target/videos");
//        if (index2.exists()) {
//            String[]entries = index2.list();
//            for(String s: entries){
//                File currentFile = new File(index2.getPath(),s);
//                currentFile.delete();
//            }
//            index2.delete();
//        }



    }

    @AfterTest
    public void tearDown() {

            page.context().browser().close();

    }
}
