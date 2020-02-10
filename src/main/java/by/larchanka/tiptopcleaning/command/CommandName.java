package by.larchanka.tiptopcleaning.command;

import by.larchanka.tiptopcleaning.command.impl.AddMoney;
import by.larchanka.tiptopcleaning.command.impl.AddPromoCode;
import by.larchanka.tiptopcleaning.command.impl.AllCatalogItemsCommand;
import by.larchanka.tiptopcleaning.command.impl.AuthenticationCommand;
import by.larchanka.tiptopcleaning.command.impl.ChangeAccountOrderStatusCommand;
import by.larchanka.tiptopcleaning.command.impl.ChangeLocaleCommand;
import by.larchanka.tiptopcleaning.command.impl.ChangeUserTypeCommand;
import by.larchanka.tiptopcleaning.command.impl.CreateCatalogItemCommand;
import by.larchanka.tiptopcleaning.command.impl.CreateOrderCommand;
import by.larchanka.tiptopcleaning.command.impl.DeleteCatalogItemByIdCommand;
import by.larchanka.tiptopcleaning.command.impl.DeletePromoCodeByIdCommand;
import by.larchanka.tiptopcleaning.command.impl.EditCatalogItemByIdCommand;
import by.larchanka.tiptopcleaning.command.impl.EditProfileCommand;
import by.larchanka.tiptopcleaning.command.impl.FindAllOrdersCommand;
import by.larchanka.tiptopcleaning.command.impl.FindAllPromoCodes;
import by.larchanka.tiptopcleaning.command.impl.FindCatalogItemsCommand;
import by.larchanka.tiptopcleaning.command.impl.FindUserInfoCommand;
import by.larchanka.tiptopcleaning.command.impl.FindUserOrdersCommand;
import by.larchanka.tiptopcleaning.command.impl.FindUsersCommand;
import by.larchanka.tiptopcleaning.command.impl.LogoutCommand;
import by.larchanka.tiptopcleaning.command.impl.RegistrationCommand;

public enum CommandName {
    REGISTER_USER(new RegistrationCommand()),
    AUTHENTICATE_USER(new AuthenticationCommand()),
    EDIT_PROFILE(new EditProfileCommand()),
    PROFILE(new FindUserInfoCommand()),
    CHANGE_LOCALE(new ChangeLocaleCommand()),
    CATALOG(new FindCatalogItemsCommand()),
    CREATE_ORDER(new CreateOrderCommand()),
    ORDERS(new FindUserOrdersCommand()),
    LOGOUT(new LogoutCommand()),
    USERS(new FindUsersCommand()),
    CHANGE_USER_TYPE(new ChangeUserTypeCommand()),
    ALL_ORDERS(new FindAllOrdersCommand()),
    CHANGE_STATUS(new ChangeAccountOrderStatusCommand()),
    ALL_PROMOCODES(new FindAllPromoCodes()),
    DELETE_PROMOCODE(new DeletePromoCodeByIdCommand()),
    ADD_PROMOCODE(new AddPromoCode()),
    CATALOG_ITEMS(new AllCatalogItemsCommand()),
    DELETE_CATALOG_ITEM(new DeleteCatalogItemByIdCommand()),
    EDIT_CATALOG_ITEM(new EditCatalogItemByIdCommand()),
    CREATE_CATALOG_ITEM(new CreateCatalogItemCommand()),
    ADD_MONEY(new AddMoney());

    private Command command;

    CommandName(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
