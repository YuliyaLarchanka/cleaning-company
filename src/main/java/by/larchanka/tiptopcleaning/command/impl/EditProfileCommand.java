package by.larchanka.tiptopcleaning.command.impl;

import by.larchanka.tiptopcleaning.command.Command;
import by.larchanka.tiptopcleaning.command.CommandResponse;
import by.larchanka.tiptopcleaning.entity.User;
import by.larchanka.tiptopcleaning.service.AccountService;
import by.larchanka.tiptopcleaning.service.ServiceException;
import by.larchanka.tiptopcleaning.service.ServiceStorage;
import by.larchanka.tiptopcleaning.util.CryptorSHA256;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static by.larchanka.tiptopcleaning.command.PageParameterConstant.APARTMENT_NUMBER;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.CONFIRM_PASSWORD;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.EMAIL;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.ENTRANCE_NUMBER;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.FIRST_NAME;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.FLOOR_NUMBER;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.HOUSE_NUMBER;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.INTERCOM_CODE;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.LAST_NAME;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.PASSWORD;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.PHONE_NUMBER;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.STREET;
import static by.larchanka.tiptopcleaning.command.PageParameterConstant.USER_ID;
import static by.larchanka.tiptopcleaning.controller.PagePathConstant.PATH_PROFILE;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_DEFAULT_ERROR;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_UPDATE_PROFILE_ERROR;
import static by.larchanka.tiptopcleaning.service.MessageConstant.KEY_UPDATE_PROFILE_SUCCESS;

public class EditProfileCommand implements Command {
    @Override
    public CommandResponse execute(HttpServletRequest request) {
        String firstName = request.getParameter(FIRST_NAME);
        String lastName = request.getParameter(LAST_NAME);
        String email = request.getParameter(EMAIL).toLowerCase();
        String password = CryptorSHA256.cryptWithSHA256(request.getParameter(PASSWORD));
        String confirmationPassword = CryptorSHA256.cryptWithSHA256(request.getParameter(CONFIRM_PASSWORD));
        String phoneNumber = request.getParameter(PHONE_NUMBER);
        String street = request.getParameter(STREET);
        String house = request.getParameter(HOUSE_NUMBER);
        String apartment = request.getParameter(APARTMENT_NUMBER);
        String entrance = request.getParameter(ENTRANCE_NUMBER);
        String floor = request.getParameter(FLOOR_NUMBER);
        String intercomCode = request.getParameter(INTERCOM_CODE);
        HttpSession session = request.getSession(true);
        long userId = Long.parseLong(session.getAttribute(USER_ID).toString());

        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setFirstName(firstName);
        updatedUser.setLastName(lastName);
        updatedUser.setEmail(email);
        updatedUser.setPassword(password);
        updatedUser.setPhoneNumber(phoneNumber);
        updatedUser.setStreet(street);
        updatedUser.setHouse(house);
        updatedUser.setApartment(apartment);
        updatedUser.setEntrance(entrance);
        updatedUser.setFloor(floor);
        updatedUser.setIntercomCode(intercomCode);

        ServiceStorage creator = ServiceStorage.getInstance();
        AccountService accountService = creator.getAccountService();


        CommandResponse commandResponse = new CommandResponse();
        try {
            Optional<User> optionalUser = accountService.updateUser(updatedUser, confirmationPassword);

            if(optionalUser.isPresent()){
                commandResponse.setMessage(KEY_UPDATE_PROFILE_SUCCESS);
                commandResponse.setTargetURL(PATH_PROFILE);
            }else{
                commandResponse.setErrorStatus(true);
                commandResponse.setMessage(KEY_UPDATE_PROFILE_ERROR);
                commandResponse.setTargetURL(PATH_PROFILE);
            }
        } catch (ServiceException e){
            commandResponse.setErrorStatus(true);
            commandResponse.setMessage(KEY_DEFAULT_ERROR);
            commandResponse.setTargetURL(PATH_PROFILE);
        }

        return commandResponse;
    }
}