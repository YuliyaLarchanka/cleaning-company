package by.larchanka.tiptopcleaning.command;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    CommandResponse execute(HttpServletRequest request);
}
