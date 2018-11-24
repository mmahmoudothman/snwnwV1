package com.snwnw.snwnw.EventBus;

/**
 * Created by hazemhabeb on 8/11/18.
 */

public class LogoutModel {
    private boolean logout;


    public LogoutModel(boolean logout) {
        this.logout = logout;
    }

    public boolean isLogout() {
        return logout;
    }

    public void setLogout(boolean logout) {
        this.logout = logout;
    }
}
