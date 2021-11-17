package com.Revature.Upposit.screens;

import com.Revature.Upposit.util.Logger;
import com.Revature.Upposit.util.ScreenRouter;

import java.io.BufferedReader;

public abstract class Screen {

    protected String name;
    protected String route;
    protected BufferedReader consoleReader;
    protected ScreenRouter router;
    protected Logger logger = Logger.getLogger(true);

    public Screen(String name, String route, BufferedReader consoleReader, ScreenRouter router) {
        this.name = name;
        this.route = route;
        this.consoleReader = consoleReader;
        this.router = router;
    }

    public final String getName() {
        return name;
    }

    public final String getRoute() {
        return route;
    }

    public abstract void render() throws Exception;

}
