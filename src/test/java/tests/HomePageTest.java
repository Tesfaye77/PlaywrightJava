package tests;

import Pages.HomePage;
import base.BaseTest;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;
import constants.AppConstants;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import util.TestUtil;


public class HomePageTest extends BaseTest {




    @Test(priority = 1)
    @Parameters({"browser"})
    @Severity(SeverityLevel.NORMAL)
    public void loginPageNavigationTest() {
        String actLoginPageTitle = homePage.getLoginPageTitle();
        System.out.println("page act title: " + actLoginPageTitle);
        Assert.assertEquals(actLoginPageTitle, AppConstants.LOGIN_PAGE_TITLE);
    }

//   @Ignore
//   @Test(priority = 2)
//    public void forgotPwdLinkExistTest() {
//        Assert.assertTrue(homePage.isForgotPwdLinkExist());
//    }
//
    @Test(priority = 3)
    @Parameters({"browser"})
    @Severity(SeverityLevel.BLOCKER)
    public void appLoginTest() {
        Assert.assertTrue(homePage.doLogin(prop.getProperty("username").trim(),
                prop.getProperty("password").trim()));
                String s = homePage.getSiteLogoVision();
                homePage.emptyTheCart();
        if(s.equals("ok"))
            System.out.println("ok");
        else if (s.equals("wrong_IDs")) {
            Assert.fail("Informations de connexion Incorrects");
        } else if (s.equals("no_logo_seen")) {
            Assert.fail("Impossible d'acceder à la page Home");
        }
    }

//    @Test(priority = 7)
//    public void appLogoutTest() {
//        homePage = homePage.navigateToLoginPage();
//    }
////
//
//
//
//    @Test(priority = 4)
//    public void homePageTitleTest() {
//        String actualTitle = homePage.getHomePageTitle();
//        Assert.assertEquals(actualTitle, AppConstants.HOME_PAGE_TITLE);
//    }
////
//    @Test(priority = 5)
//    public void homePageURLTest() {
//        String actualURL = homePage.getHomePageURL();
//        Assert.assertEquals(actualURL, prop.getProperty("url"));
//    }

    @DataProvider
    public Object[][] getProductData() {
        return new Object[][] {
                { "T-shirt" },
                { "xoxo" },
                { "chaise" },
                { "ulrich" }
        };
    }

    @Test(dataProvider = "getProductData",priority = 6)
    @Parameters({"browser"})
    @Severity(SeverityLevel.BLOCKER)
    public void searchTest(String productName)  {
        homePage.doSearch(productName);
        Locator p = homePage.page.locator(homePage.searchResult)
                .filter(new Locator.FilterOptions().setHasText(productName));

        int count = p.count();
        if(count==0)
            count++;
        for (int i = 0; i < count; ++i) {
            String s = homePage.getResultSearch(i, productName);

        if(s.equals("ok"))
            System.out.println("Le produit est bien present");
        else if (s.equals("Aucun_produit_trouvé")) {
            Assert.fail("Produit inexistant dans la base de donnée");
        } else if (s.equals("not_ok")) {
            Assert.fail("Pas de correspondance entre le resulat et l'élement recherché");
        }

        }
}

//
//    @DataProvider
//    public Object[][] getProductDataForAdd() {
//        return new Object[][] {
//                { "Ampoule Vecteur Incandescent",3 },
//                { "T-shirt en coton biologique",1 },
//                { "Chaussures Hommes de Ville",5 }
//        };
//    }
//    @Test(priority = 4,dataProvider = "getProductDataForAdd")
//    public void addToCartTest(String productName,int X) {
//        homePage.page.fill("id=style_input_navbar_search__Scaxy","");
////        homePage.emptyTheCart();
//        Boolean b = homePage.ClickOnAnArticle(productName);
//        Assert.assertTrue(b,"Article inexistant");
//        homePage.ClickOnAddToCart(X);
//        Assert.assertTrue(homePage.VerifyArticleInCart(productName),"Article absent du panier");
//        homePage.page.click("text=LES PRODUITS");
////
//    }
//
//    @Test(priority = 5,dataProvider = "getProductDataForAdd")
//    public void suppressFromCartTest(String productName, int X) throws InterruptedException {
////
//        homePage.ClickOnCartIcon();
//        for (int i=0;i<X;i++)
//        {homePage.DeleteFromCart(productName);
//        }
//        Assert.assertFalse(homePage.VerifyArticleDeletion(productName),"Article toujours présent dans le panier");
////
//    }

//    @DataProvider(name = "getRegistrationTestData")
//    public Object[][] getRegistrationTestData() {
//        Object usersData[][] = TestUtil.getTestData(AppConstants.CONTACTS_SHEET_NAME);
//        return usersData;
//    }
//
//
//
//    @Test(dataProvider = "getRegistrationTestData", priority = 2)
//    public void createNewUserTest(String email, String password, String passwordconf) {
//        homePage.page.navigate("https://ztrain-web.vercel.app/auth/login");
//        try{
//            homePage.page.waitForURL("https://ztrain-web.vercel.app/auth/login",
//                    new Page.WaitForURLOptions().setTimeout(10000));}
//        catch (TimeoutError ignored){}
//      homePage.clickRegisterButton(email, password, passwordconf);
//        String i = homePage.getSiteLogoVision();
//        switch (i) {
//            case "ok":
//                System.out.println("ok");
//                break;
//            case "no_logo_seen":
//                Assert.fail("Impossible d'acceder à la page Home");
//                break;
//            case "short_Pswd":
//                Assert.fail("Mot de passe trop court");
//            case "same_Pswds":
//                Assert.fail("Les mot de passe ne correspondent pas");
//            case "used_IDs":
//                Assert.fail("L'utilisateur existe déjà");
//            case "invalidIDs":
//                Assert.fail("L'adresse mail n'a pas un format valide");
//                break;
//        }
//    }

}
