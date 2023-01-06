package Pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;
import com.microsoft.playwright.options.AriaRole;

public class LoginPage {
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

    public String searchResult = ".style_card__gNEqX";


    // 2. page constructor:
    public LoginPage(Page page) {
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


    public LoginPage navigateToLoginPage() {
        page.click(handle_mouse);
        page.click(logout_bouton);
        return new LoginPage(page);
    }


}
