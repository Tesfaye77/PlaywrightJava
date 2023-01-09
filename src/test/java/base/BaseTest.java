package base;

import Factory.PlaywrightFactory;
import Pages.HomePage;
import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class BaseTest {
    PlaywrightFactory pf;
    Page page;
    protected Properties prop;

    protected HomePage homePage;

    public String UserName;

    public String Password;

    @Parameters({ "browser","username","password" })
    @BeforeTest
    public void setup(String browserName,String username, String passWord) {
        pf = new PlaywrightFactory();
        UserName = username;
        Password = passWord;
        prop = pf.init_prop();

        if (browserName != null) {
            prop.setProperty("browser", browserName);

        }

        page = pf.initBrowser(prop);
        homePage = new HomePage(page);

        File index = new File("C:/Users/Pawk39/IdeaProjects/Playwrite/allure-results");
        if (index.exists()) {
            String[] entries = index.list();
            for (String s : entries) {
                File currentFile = new File(index.getPath(), s);
                currentFile.delete();
            }
            index.delete();
        }

        File index1 = new File("C:/Users/Pawk39/.jenkins/workspace/ZtrainPipeline/allure-results");
        if (index1.exists()) {
            String[] entries = index1.list();
            for (String s : entries) {
                File currentFile = new File(index1.getPath(), s);
                currentFile.delete();
            }
            index1.delete();
        }


        File index2 = new File("C:/Users/Pawk39/IdeaProjects/Playwrite/videos");
        if (index2.exists()) {
            String[] entries = index2.list();
            for (String s : entries) {
                File currentFile = new File(index2.getPath(), s);
                currentFile.delete();
            }
            index2.delete();
        }

        File index3 = new File("C:/Users/Pawk39/.jenkins/workspace/ZtrainPipeline/videos");
        if (index3.exists()) {
            String[] entries = index3.list();
            for (String s : entries) {
                File currentFile = new File(index3.getPath(), s);
                currentFile.delete();
            }
            index3.delete();

        }
    }

    @AfterTest
    public void tearDown() {

            page.context().browser().close();
        byte[] byteArr2 = new byte[0];
        try {
            Path path = page.video().path();
            // file to byte[], Path
            byteArr2 = Files.readAllBytes(path);
            Allure.addAttachment("Video", "video/mp4", new ByteArrayInputStream(byteArr2), "mp4");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
 }
