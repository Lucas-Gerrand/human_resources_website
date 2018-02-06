package hrrss.ui.logout;

import hrrss.ui.BasePage;
import hrrss.ui.home.Home;

public class Logout extends BasePage {
    public Logout() {
        getSession().setAttribute("isLogin", "false");
        getSession().setAttribute("personTyp", "");
        getSession().setAttribute("id", "");
        
        setResponsePage(Home.class);
    }
}
