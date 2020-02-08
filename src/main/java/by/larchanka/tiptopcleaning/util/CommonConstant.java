package by.larchanka.tiptopcleaning.util;

import by.larchanka.tiptopcleaning.command.CommandResponse;

import static by.larchanka.tiptopcleaning.controller.PagePathConstant.PATH_ERROR;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_GENERIC_ERROR;

public class CommonConstant {
    public static final String EMPTY = "";
    public static final String URL_SEPARATOR = "/";
    public static final String DASH = "-";
    public static final String UNDERSCORE = "_";

    public static final String ENCODING_UTF_8 = "UTF-8";
    public static final String CONTENT_TYPE_TEXT_HTML = "text/html";

    public static final String ERROR_KEY = "errorKey";
    public static final String SUCCESS_KEY = "successKey";

    public static final String LOCALE = "locale";
    public static final String PATH = "path";
    public static final String EN = "en";
    public static final String RU = "ru";

    public static final String SHA = "SHA-256";

    public static final CommandResponse ERROR_RESPONSE = new CommandResponse(true, KEY_GENERIC_ERROR, PATH_ERROR);
}
