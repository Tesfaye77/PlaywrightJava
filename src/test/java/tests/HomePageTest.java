package tests;

import base.BaseTest;

import com.microsoft.playwright.Page;
import constants.AppConstants;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;



public class HomePageTest extends BaseTest {
    private Page page;


    @Test(priority = 1)
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
    @Severity(SeverityLevel.BLOCKER)
    public void appLoginTest() {
        Assert.assertTrue(homePage.doLogin(prop.getProperty("username").trim(),
                prop.getProperty("password").trim()),"Email ou mot de passe incorrect");
                homePage.emptyTheCart();
    }
//
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
//
//    @Test(priority = 5)
//    public void homePageURLTest() {
//        String actualURL = homePage.getHomePageURL();
//        Assert.assertEquals(actualURL, prop.getProperty("url"));
//    }

//    @DataProvider
//    public Object[][] getProductData() {
//        return new Object[][] {
//                { "T-shirt" },
//                { "xoxo" },
//                { "chaise" },
//                { "ulrich" }
//        };
//    }
//
//    @Test(dataProvider = "getProductData",priority = 6)
//    @Severity(SeverityLevel.BLOCKER)
//    public void searchTest(String productName)  {
//        homePage.doSearch(productName);
//        Locator p = homePage.page.locator(homePage.searchResult)
//                .filter(new Locator.FilterOptions().setHasText(productName));
//
//        int count = p.count();
//        if(count==0)
//            count++;
//        for (int i = 0; i < count; ++i) {
//            String s = homePage.getResultSearch(i, productName);
//
//        if(s.equals("ok"))
//            System.out.println("Le produit est bien present");
//        else if (s.equals("Aucun_produit_trouvé")) {
//            Assert.fail("Produit inexistant dans la base de donnée");
//        } else if (s.equals("not_ok")) {
//            Assert.fail("Pas de correspondance entre le resulat et l'élement recherché");
//        }
//
//        }
//}

//
    @DataProvider
    public Object[][] getProductDataForAdd() {
        return new Object[][] {
                { "Ampoule Vecteur Incandescent",3 },
                { "T-shirt en coton biologique",1 },
                { "Chaussures Hommes de Ville",5 }
        };
    }
    @Test(priority = 4,dataProvider = "getProductDataForAdd")
    public void addToCartTest(String productName,int X) {
        homePage.page.fill("id=style_input_navbar_search__Scaxy","");
//        homePage.emptyTheCart();
        Boolean b = homePage.ClickOnAnArticle(productName);
        Assert.assertTrue(b,"Article inexistant");
        homePage.ClickOnAddToCart(X);
        Assert.assertTrue(homePage.VerifyArticleInCart(productName),"Article absent du panier");
        homePage.page.click("text=LES PRODUITS");
//
    }

    @Test(priority = 5,dataProvider = "getProductDataForAdd")
    public void suppressFromCartTest(String productName, int X) throws InterruptedException {
//
        homePage.ClickOnCartIcon();
        for (int i=0;i<X;i++)
        {homePage.DeleteFromCart(productName);
        }
        Assert.assertFalse(homePage.VerifyArticleDeletion(productName),"Article toujours présent dans le panier");
//
    }

}
