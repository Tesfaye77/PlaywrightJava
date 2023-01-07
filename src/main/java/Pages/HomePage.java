package Pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;
import com.microsoft.playwright.options.WaitUntilState;
import org.testng.Assert;

import java.util.regex.Pattern;

public class HomePage {
    public Page page;


    // 1. String Locators - OR
    String pageTitlelogin = "text=Connexion";
    String emailUser = "id=email_login";
    String password = "id=password_login";
    String clickLogin = "id=btn_login";
    String confirmationLogin = "id=style_content_logo__pkvMP";
    String failedLogin = "text=Email ou mot de passe incorrect";
    String pageTitleHome = "id=style_content_logo__pkvMP";
    String searchBar = "id=style_input_navbar_search__Scaxy";
    String verifyproduct = "text=Votre panier à été mis à jour";
    String handle_mouse = "id=style_avatar_wrapper__pEGIQ";
    String logout_bouton = "text=Se déconnecter";

    String addToCartBtn ="id=style_btn_add_cart__gTXM7";

    String notifOfAdd = "text=Votre panier à été mis à jour";

    String badgeOfAdd = "#style_content_cart_wrapper__mqNbf >> text=0";
    String cartIcon = "id=style_content_cart_wrapper__mqNbf";
    String registerButton = "text=S'inscrire";
    String email_register = "id=email_register";
    String password_register = "id=password_register";
    String confirmPassword = "id=confirm_password_register";
    String validationButton = "id=btn_register";

    public String searchResult = ".style_card__gNEqX";

    String usedIDs = "text=Cet utilisateur existe déjà";

    String incorrectIDs = "text=Email ou mot de passe incorrect";
    String siteLogo ="id=style_header_home__8t_ie";


    // 2. page constructor:
    public HomePage(Page page) {
        this.page = page;
    }

    // 3. page actions/methods:
    public String getLoginPageTitle() {
        String title =  page.textContent(pageTitlelogin);
        System.out.println("page title: " + title);
        return title;
    }

    public boolean isForgotPwdLinkExist() {
        return page.isVisible(failedLogin);
    }

    public boolean doLogin(String appUserName, String appPassword) {
        System.out.println("App creds: " + appUserName + ":" + appPassword);
        page.fill(emailUser, appUserName);
        page.fill(password, appPassword);
        page.click(clickLogin);
        page.locator(confirmationLogin).waitFor();
        if(page.locator(confirmationLogin).isVisible()) {
            System.out.println("user is logged in successfully....");
            return true;
        }else {
            System.out.println("user is not logged in successfully....");
            return false;
        }
    }

    public String getSiteLogoVision() {

        try {
            page.waitForURL("**/home", new Page.WaitForURLOptions().setTimeout(15000));
            page.waitForURL("**/home", new Page.WaitForURLOptions().setWaitUntil(WaitUntilState.NETWORKIDLE));
//            page.waitForTimeout(3000);
        } catch (TimeoutError e) {
            System.out.println("Timeout!");
        }
        if (page.isVisible(incorrectIDs))
            return  ("wrong_IDs");
        else{
            if (page.isVisible(siteLogo))
                return  ("ok");
            else{
                if (page.isVisible(usedIDs))
                    return  ("used_IDs");
                else
                    return ("no_logo_seen");

            }
        }



    }

    public String getHomePageTitle() {
        String title =  page.textContent(pageTitleHome);
        System.out.println("page title: " + title);
        return title;
    }



    public String getHomePageURL() {
        String url =  page.url();
        System.out.println("page url : " + url);
        return url;
    }

    public String doSearch(String productName) {
        page.waitForTimeout(1000);
        page.fill(searchBar, productName);
        return productName;
    }
    public String getResultSearch(int x, String searchedTerms) {
        Locator p = page.locator(searchResult)
                .filter(new Locator.FilterOptions().setHasText(searchedTerms)).nth(x);
        try {
            p.waitFor(new Locator.WaitForOptions().setTimeout(15000));
        } catch (TimeoutError e) {
            System.out.println("Timeout pour le résultat de la recherche!");
        }
        if(page.isVisible("text=Aucun produit trouvé"))
            return ("Aucun_produit_trouvé");
        else if (p.isVisible()) {
            return ("ok");
        }
        else
            return ("not_ok");
    }


    public void emptyTheCart() {
        if (!page.isVisible(badgeOfAdd)){
            try {
                page.waitForSelector(cartIcon, new Page.WaitForSelectorOptions().setTimeout(15000));
                page.click(cartIcon);
                page.click("text=Vider le panier");
            } catch (TimeoutError e) {
                System.out.println("Timeout to empty the cart!");
            }
        }
    }

    public Boolean ClickOnAnArticle(String articleToAdd) {

        try {
            Locator p = page.locator(searchResult)
                    .filter(new Locator.FilterOptions().setHasText(articleToAdd)).first();
            p.waitFor(new Locator.WaitForOptions().setTimeout(15000));
            p.click();
            return true;
        } catch (TimeoutError e) {
            System.out.println("Timeout to click on article");
            return false;
        }

    }

    public void ClickOnAddToCart(int X) {


        try {
            page.waitForSelector(addToCartBtn, new Page.WaitForSelectorOptions().setTimeout(15000));
            page.fill(".style_input_quantity__xZDIb",String.valueOf(X));
            page.waitForLoadState();
            page.click(addToCartBtn);
        } catch (TimeoutError e) {
            System.out.println("Timeout to click on article");
        }
    }

    public Boolean VerifyArticleInCart(String productName) {

        try {
            page.click("#style_content_cart_wrapper__mqNbf > span");
            String[] productNames = productName.split(" ");
            Locator p = page.locator(".style_card__JLMp6")
                    .filter(new Locator.FilterOptions().setHasText(Pattern.compile(productNames[0])));
            p.waitFor();
            return p.isVisible();
        } catch (TimeoutError e) {
            System.out.println("Timeout when looking in cart!");
            return page.locator(".style_card__JLMp6")
                    .filter(new Locator.FilterOptions().setHasText(Pattern.compile(productName))).isVisible();

        }

    }

    public void DeleteFromCart(String s) throws InterruptedException {
        try {

            String[] productNames = s.split(" ");
            Locator p = page.locator(".style_card__JLMp6")
                    .filter(new Locator.FilterOptions().setHasText(Pattern.compile(productNames[0])));
            p.locator(".style_quantity_dec__nm5ig").click(new Locator.ClickOptions().setTimeout(3000));
        } catch (TimeoutError e) {
            Assert.fail("Impossible de supprimer l'article");
            System.out.println("Timeout to press on reduce button!");
        }

        page.waitForTimeout(3000);
    }
    public void ClickOnCartIcon() {
        try {
            page.waitForTimeout(2000);
            page.waitForSelector(cartIcon, new Page.WaitForSelectorOptions().setTimeout(1500));
            if(page.isVisible(cartIcon))
                page.click(cartIcon, new Page.ClickOptions().setTimeout(100));
        } catch (TimeoutError e) {
            System.out.println("Timeout to press on cart icon!");
        }
    }


    public Boolean VerifyArticleDeletion(String s) {
        String[] productNames = s.split(" ");
        Locator p = page.locator(".style_card__JLMp6")
                .filter(new Locator.FilterOptions().setHasText(Pattern.compile(productNames[0])));
        try {
//            page.waitForSelector("class=style_card__gNEqX",new Page.WaitForSelectorOptions().setTimeout(15000));
            p.waitFor(new Locator.WaitForOptions().setTimeout(2000));
//            page.waitForSelector(searchResult, new Page.WaitForSelectorOptions().setTimeout(15000));
        } catch (TimeoutError e) {
            System.out.println("Timeout pour le résultat de la recherche!");
        }
        if(p.isVisible())
            return (true);
        else
            return (false);

    }


    public void clickRegisterButton(String email,String password,String passconf) {
        page.hover(registerButton);
        page.click(registerButton);
        page.fill(email_register, email);
        page.fill(password_register, password);
        page.fill(confirmPassword, passconf);
        page.click(validationButton);}





    public HomePage navigateToLoginPage() {
        page.click(handle_mouse);
        page.click(logout_bouton);
        return new HomePage(page);
    }


}
