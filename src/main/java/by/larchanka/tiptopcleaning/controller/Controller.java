package by.larchanka.tiptopcleaning.controller;

import by.larchanka.tiptopcleaning.command.Command;
import by.larchanka.tiptopcleaning.command.CommandName;
import by.larchanka.tiptopcleaning.command.CommandResponse;
import by.larchanka.tiptopcleaning.connection.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static by.larchanka.tiptopcleaning.command.PageParameterConstant.COMMAND_NAME;
import static by.larchanka.tiptopcleaning.util.CommonConstant.DASH;
import static by.larchanka.tiptopcleaning.util.CommonConstant.EMPTY;
import static by.larchanka.tiptopcleaning.util.CommonConstant.ERROR_KEY;
import static by.larchanka.tiptopcleaning.util.CommonConstant.ERROR_RESPONSE;
import static by.larchanka.tiptopcleaning.util.CommonConstant.SUCCESS_KEY;
import static by.larchanka.tiptopcleaning.util.CommonConstant.UNDERSCORE;
import static by.larchanka.tiptopcleaning.util.CommonConstant.URL_SEPARATOR;

@MultipartConfig
public class Controller extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void init() {
        ConnectionPool.getInstance();
    }

    @Override
    public void destroy() {
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connectionPool.destroyPool();
        } catch (InterruptedException e) {
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String commandName = request.getServletPath().replace(URL_SEPARATOR, EMPTY);
        commandName = commandName.replace(DASH, UNDERSCORE);

        CommandResponse commandResponse = processRequest(request, commandName);

        if (commandResponse.isErrorStatus()) {
            request.setAttribute(ERROR_KEY, commandResponse.getMessage());
        }

        request.getRequestDispatcher(commandResponse.getTargetURL()).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String commandName = request.getParameter(COMMAND_NAME);

        CommandResponse commandResponse = processRequest(request, commandName);

        HttpSession session = request.getSession(true);

        if (!commandResponse.isErrorStatus()) {
            session.setAttribute(SUCCESS_KEY, commandResponse.getMessage());
        } else {
            session.setAttribute(ERROR_KEY, commandResponse.getMessage());
        }

        response.sendRedirect(commandResponse.getTargetURL());
    }

    private CommandResponse processRequest(HttpServletRequest request, String commandName) {
        CommandName commandType = Enum.valueOf(CommandName.class, commandName.toUpperCase());
        Command command = commandType.getCommand();

        if (command == null) {
            return ERROR_RESPONSE;
        }

        CommandResponse commandResponse = command.execute(request);
        if (commandResponse == null) {
            return ERROR_RESPONSE;
        }

        return commandResponse;
    }
}