package by.larchanka.tiptopcleaning.command;

import javax.servlet.http.HttpServletRequest;

public interface Command {

    /**
     * Parses request parameters, calls necessary service method and returns command response
     *
     * @param request a {@code HttpServletRequest} object to parse parameters from
     * @return {@code CommandResponse} object containing information about operation result
     */
    CommandResponse execute(HttpServletRequest request);
}


