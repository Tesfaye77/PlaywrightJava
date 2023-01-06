package tests;

import base.BaseTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import constants.AppConstants;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginPageTest extends BaseTest {
    private Page page;

    @Test(priority = 1)
    public void loginPageNavigationTest() {
        String actLoginPageTitle = loginPage.getLoginPageTitle();
        System.out.println("page act title: " + actLoginPageTitle);
        Assert.assertEquals(actLoginPageTitle, AppConstants.LOGIN_PAGE_TITLE);
    }

//   @Ignore
//   @Test(priority = 2)
//    public void forgotPwdLinkExistTest() {
//        Assert.assertTrue(loginPage.isForgotPwdLinkExist());
//    }
//
    @Test(priority = 3)
    public void appLoginTest() {
        Assert.assertTrue(loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim()));
    }
//
//    @Test(priority = 7)
//    public void appLogoutTest() {
//        loginPage = loginPage.navigateToLoginPage();
//    }
//
//
//
//
//    @Test(priority = 4)
//    public void homePageTitleTest() {
//        String actualTitle = loginPage.getHomePageTitle();
//        Assert.assertEquals(actualTitle, AppConstants.HOME_PAGE_TITLE);
//    }
//
//    @Test(priority = 5)
//    public void homePageURLTest() {
//        String actualURL = loginPage.getHomePageURL();
//        Assert.assertEquals(actualURL, prop.getProperty("url"));
//    }

    @DataProvider
    public Object[][] getProductData() {
        return new Object[][] {
                { "T-shirt" },
                { "Ampoule" },
                { "chaise" }
        };
    }

    @Test(dataProvider = "getProductData",priority = 6)
    public void searchTest(String productName)  {
        loginPage.doSearch(productName);
        Locator p = loginPage.page.locator(loginPage.searchResult)
                .filter(new Locator.FilterOptions().setHasText(productName));

        int count = p.count();
        for (int i = 0; i < count; ++i) {
            String s = loginPage.getResultSearch(i, productName);

        if(s.equals("ok"))
            System.out.println("Le produit est bien present");
        else if (s.equals("Aucun_produit_trouvé")) {
            Assert.fail("Produit inexistant dans la base de donnée");
        } else if (s.equals("not_ok")) {
            Assert.fail("Pas de correspondance entre le resulat et l'élement recherché");
        }


}
}}
