/*
 * Copyright (c) 2013-2019 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package eapli.base.app.backoffice.console.presentation;

import eapli.base.Application;
import eapli.base.app.backoffice.console.presentation.authz.AddUserUI;
import eapli.base.app.backoffice.console.presentation.authz.DeactivateUserAction;
import eapli.base.app.backoffice.console.presentation.authz.ListUsersAction;
import eapli.base.app.backoffice.console.presentation.clientuser.AcceptRefuseSignupRequestAction;
import eapli.base.app.backoffice.console.presentation.deposits.BootstrapDepositAction;
import eapli.base.app.backoffice.console.presentation.deposits.RegisterDepositAction;
import eapli.base.app.backoffice.console.presentation.factoryfloor.ExportFactoryFloorAction;
import eapli.base.app.backoffice.console.presentation.factoryfloor.ExportXMLAction;
import eapli.base.app.backoffice.console.presentation.factoryfloor.GenerateFactoryFloorXSDAction;
import eapli.base.app.backoffice.console.presentation.notifications.ArchiveNotificationAction;
import eapli.base.app.backoffice.console.presentation.notifications.ConsultArchivedNotificationsAction;
import eapli.base.app.backoffice.console.presentation.notifications.ConsultUntreatedNotificationsAction;
import eapli.base.app.backoffice.console.presentation.productionlines.AlterProcessingStateAction;
import eapli.base.app.backoffice.console.presentation.productionlines.BootstrapProdcutionLineAction;
import eapli.base.app.backoffice.console.presentation.productionlines.RegisterProductionLineAction;
import eapli.base.app.backoffice.console.presentation.productionorder.ConsultProductionOrdersOfOrderAction;
import eapli.base.app.backoffice.console.presentation.productionorder.ImportProductionOrderCsvAction;
import eapli.base.app.backoffice.console.presentation.productionorder.ListProductionOrdersByStatusAction;
import eapli.base.app.backoffice.console.presentation.productionorder.RegisterProductionOrderAction;
import eapli.base.app.backoffice.console.presentation.products.*;
import eapli.base.app.backoffice.console.presentation.rawmaterial.BootstrapRawMaterialAction;
import eapli.base.app.backoffice.console.presentation.rawmaterial.BootstrapRawMaterialCategoryAction;
import eapli.base.app.backoffice.console.presentation.rawmaterial.RegisterRawMaterialAction;
import eapli.base.app.backoffice.console.presentation.rawmaterial.RegisterRawMaterialCategoryAction;
import eapli.base.app.backoffice.presentation.machine.BootstrapMachineAction;
import eapli.base.app.backoffice.presentation.machine.ImportConfigTXTFileAction;
import eapli.base.app.backoffice.presentation.machine.RegisterMachineAction;
import eapli.base.app.backoffice.presentation.machine.SendConfigRequestAction;
import eapli.base.app.common.console.presentation.authz.MyUserMenu;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.actions.Actions;
import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.ExitWithMessageAction;
import eapli.framework.presentation.console.ShowMessageAction;
import eapli.framework.presentation.console.menu.HorizontalMenuRenderer;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.MenuRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;

/**
 * TODO split this class in more specialized classes for each menu
 *
 * @author Paulo Gandra Sousa
 */
public class MainMenu extends AbstractUI {

    private static final String RETURN_LABEL = "Return ";
    private static final int EXIT_OPTION = 0;
    // USERS
    private static final int ADD_USER_OPTION = 1;
    private static final int LIST_USERS_OPTION = 2;
    private static final int DEACTIVATE_USER_OPTION = 3;
    private static final int ACCEPT_REFUSE_SIGNUP_REQUEST_OPTION = 4;
    // SETTINGS
    private static final int SET_KITCHEN_ALERT_LIMIT_OPTION = 1;
    // MAIN MENU
    private static final int MY_USER_OPTION = 1;
    private static final int USERS_OPTION = 2;
    private static final int SETTINGS_OPTION = 4;
    private static final int PRODUCTS_OPTION = 10;
    private static final int MATERIAL_OPTION = 5;
    private static final int PRODUCTION_LINE_OPTION = 6;
    private static final int DEPOSIT_OPTION = 7;
    private static final int MACHINE_OPTION = 8;
    private static final int BOOTSTRAP_OPTION = 9;
    private static final int PRODUCTION_ORDER_OPTION = 11;
    private static final int FACTORYFLOOR_OPTION = 3;
    private static final int NOTIFICATION_OPTION = 11;


    //FACTORYFLOOR
    private static final int EXPORT_ALL_OPTION = 1;
    private static final int GENERATE_XSD = 2;
    private static final int GENERATE_HTML=3;

    // PRODUCTS
    private static final int REGISTER_PRODUCT_OPTION = 3;
    private static final int IMPORT_PRODUCT = 2;
    private static final int NO_PRODUCTION_RECORD_OPTION = 4;
    private static final int SPECIFY_PRODUCTION_RECORD = 5;

    // RAW MATERIAL
    private static final int REGISTER_CATEGORY = 1;
    private static final int REGISTER_RAW_MATERIAL = 2;

    // PRODUCTION LINE
    private static final int REGISTER_PRODUCTION_LINE = 1;
    private static final int  ALTER_PROCESSING_STATE=2;

    // PRODUCTION LINE
    private static final int REGISTER_DEPOSIT = 1;


    // MACHINE
    private static final int REGISTER_MACHINE = 1;
    private static final int ADD_CONFIGURATION_FILE = 2;
    private static final int SEND_CONFIG_REQUEST = 3;

    // PRODUCTION ORDER
    private static final int IN_EXECUTION_PRODUCTION_ORDER = 1;
    private static final int IMPORT_PRODUCTION_ORDER_CSV = 2;
    private static final int CONSULT_PRODUCTION_ORDERS_OF_ORDER = 3;
    private static final int REGISTER_PRODUCTION_ORDER = 4;

    //NOTIFICATIONS
    private static final int ARCHIVE_NOTIFICATION = 1;
    private static final int CONSULT_UNTREATED_NOTIFICATIONS=2;
    private static final int CONSULT_ARCHIVED_NOTIFICATIONS=3;

    //BOOTSTRAP
    private static final int BOOTSTRAP_PRODUCTS = 1;
    private static final int BOOTSTRAP_RAW_MATERIALS = 2;
    private static final int BOOTSTRAP_RAW_MATERIALS_CATEGORIES = 3;
    private static final int BOOTSTRAP_MACHINES = 4;
    private static final int BOOTSTRAP_DEPOSITS = 5;
    private static final int BOOTSTRAP_PRODUCTIONLINES = 6;
    private static final int BOOTSTRAP_ALL = 7;


    private static final String SEPARATOR_LABEL = "--------------";

    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    @Override
    public boolean show() {
        drawFormTitle();
        return doShow();
    }

    /**
     * @return true if the user selected the exit option
     */
    @Override
    public boolean doShow() {
        final Menu menu = buildMainMenu();
        final MenuRenderer renderer;
        if (Application.settings().isMenuLayoutHorizontal()) {
            renderer = new HorizontalMenuRenderer(menu, MenuItemRenderer.DEFAULT);
        } else {
            renderer = new VerticalMenuRenderer(menu, MenuItemRenderer.DEFAULT);
        }
        return renderer.render();
    }

    @Override
    public String headline() {

        return authz.session().map(s -> "Base [ @" + s.authenticatedUser().identity() + " ]")
                .orElse("Base [ ==Anonymous== ]");
    }

    private Menu buildMainMenu() {
        final Menu mainMenu = new Menu();

        final Menu myUserMenu = new MyUserMenu();
        mainMenu.addSubMenu(MY_USER_OPTION, myUserMenu);

        if (!Application.settings().isMenuLayoutHorizontal()) {
            mainMenu.addItem(MenuItem.separator(SEPARATOR_LABEL));
        }

        if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.POWER_USER, BaseRoles.GESTOR_PRODUCAO)) {
            final Menu productMenu = buildProductManagerMenu();
            mainMenu.addSubMenu(PRODUCTS_OPTION, productMenu);

            final Menu materialMenu = buildAdminSettingsMenu();
            mainMenu.addSubMenu(SETTINGS_OPTION, materialMenu);

            final Menu settingsMenu = buildRawMaterialManagerMenu();
            mainMenu.addSubMenu(MATERIAL_OPTION, settingsMenu);

            final Menu productionOrderMenu = buildProductionOrderManagerMenu();
            mainMenu.addSubMenu(PRODUCTION_ORDER_OPTION, productionOrderMenu);

            final Menu factoryMenu = buildFactoryFloorMenuMenu();
            mainMenu.addSubMenu(FACTORYFLOOR_OPTION, factoryMenu);
        }

        if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.POWER_USER, BaseRoles.GESTOR_CHAO_FABRICA)) {
            final Menu ProductionLineMenu = buildProductionLineManagerMenu();
            mainMenu.addSubMenu(PRODUCTION_LINE_OPTION, ProductionLineMenu);

            final Menu DepositMenu = buildDepositManagerMenu();
            mainMenu.addSubMenu(DEPOSIT_OPTION, DepositMenu);

            final Menu settingsMenu = buildAdminSettingsMenu();
            mainMenu.addSubMenu(SETTINGS_OPTION, settingsMenu);

            final Menu machinesMenu = buildMachineManagerMenu();
            mainMenu.addSubMenu(MACHINE_OPTION, machinesMenu);

            final Menu notificationMenu = buildNotificationMenu();
            mainMenu.addSubMenu(NOTIFICATION_OPTION,notificationMenu);
        }
        if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.POWER_USER, BaseRoles.GESTOR_PROJETO)) {
            final Menu bootstrapMenu = buildBootstrapMenu();
            mainMenu.addSubMenu(BOOTSTRAP_OPTION, bootstrapMenu);
            final Menu settingsMenu = buildAdminSettingsMenu();
            mainMenu.addSubMenu(SETTINGS_OPTION, settingsMenu);
        }

        if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.POWER_USER, BaseRoles.ADMIN)) {
            final Menu usersMenu = buildUsersMenu();
            mainMenu.addSubMenu(USERS_OPTION, usersMenu);
            final Menu settingsMenu = buildAdminSettingsMenu();
            mainMenu.addSubMenu(SETTINGS_OPTION, settingsMenu);
        }

        if (!Application.settings().isMenuLayoutHorizontal()) {
            mainMenu.addItem(MenuItem.separator(SEPARATOR_LABEL));
        }

        mainMenu.addItem(EXIT_OPTION, "Exit", new ExitWithMessageAction("See ya later aligator :)"));

        return mainMenu;
    }

    private Menu buildBootstrapMenu() {
        final Menu menu = new Menu("Bootstrap Menu >");

        menu.addItem(BOOTSTRAP_PRODUCTS, "Bootstrap Products",
                new BootstrapProductsAction());
        menu.addItem(BOOTSTRAP_RAW_MATERIALS, "Bootstrap Raw Materials",
                new BootstrapRawMaterialAction());
        menu.addItem(BOOTSTRAP_RAW_MATERIALS_CATEGORIES, "Bootstrap Raw Materials Categories",
                new BootstrapRawMaterialCategoryAction());
        menu.addItem(BOOTSTRAP_MACHINES, "Bootstrap Machines",
                new BootstrapMachineAction());
        menu.addItem(BOOTSTRAP_DEPOSITS, "Bootstrap Deposits",
                new BootstrapDepositAction());
        menu.addItem(BOOTSTRAP_PRODUCTIONLINES, "Bootstrap Production Lines",
                new BootstrapProdcutionLineAction());
        menu.addItem(BOOTSTRAP_ALL, "Bootstrap Everything",
                new BootstrapAllAction());

        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);
        return menu;
    }

    private Menu buildFactoryFloorMenuMenu() {
        final Menu menu = new Menu("Factory Floor Menu >");

        menu.addItem(EXPORT_ALL_OPTION, "Export All",
                new ExportFactoryFloorAction());
        menu.addItem(GENERATE_XSD, "Generate XSD", 
                new GenerateFactoryFloorXSDAction());
        menu.addItem(GENERATE_HTML, "Export xml to specified format",
                new ExportXMLAction());

        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);
        return menu;
    }

    private Menu buildMachineManagerMenu() {
        final Menu menu = new Menu("Machine Menu >");
        menu.addItem(ADD_CONFIGURATION_FILE, "Add configuration file to a machine",
                new ImportConfigTXTFileAction());
        menu.addItem(REGISTER_MACHINE, "Register Machine",
                new RegisterMachineAction());
        menu.addItem(SEND_CONFIG_REQUEST, "Send Configuration Request to Machine",
                new SendConfigRequestAction());

        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);
        return menu;
    }

    private Menu buildDepositManagerMenu() {
        final Menu menu = new Menu("Deposit Menu >");

        menu.addItem(REGISTER_DEPOSIT, "Register Deposit",
                new RegisterDepositAction());

        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);
        return menu;
    }

    private Menu buildProductionLineManagerMenu() {
        final Menu menu = new Menu("Production Line >");

        menu.addItem(REGISTER_PRODUCTION_LINE, "Register ProductionLine",
                new RegisterProductionLineAction());


        menu.addItem(ALTER_PROCESSING_STATE, "Alter Processing State",
                new AlterProcessingStateAction());


        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);
        return menu;
    }

    private Menu buildProductManagerMenu() {
        final Menu menu = new Menu("Products >");

        menu.addItem(IMPORT_PRODUCT, "ImportProducts",
                new ImportProductsCsvAction());

        menu.addItem(REGISTER_PRODUCT_OPTION, "Register new product",
                new RegisterProductAction());

        menu.addItem(NO_PRODUCTION_RECORD_OPTION, "List products without production record",
                new ListProductNoProductionRecordAction());

        menu.addItem(SPECIFY_PRODUCTION_RECORD, "Spectify Production Record",
                new SpecifyProductionRecordAction());

        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

    private Menu buildRawMaterialManagerMenu() {
        final Menu menu = new Menu("Raw Material >");

        menu.addItem(REGISTER_CATEGORY, "Register Category",
                new RegisterRawMaterialCategoryAction());

        menu.addItem(REGISTER_RAW_MATERIAL, "Register raw Material",
                new RegisterRawMaterialAction());

        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);
        return menu;
    }

    private Menu buildProductionOrderManagerMenu() {
        final Menu menu = new Menu("Production Order >");
        menu.addItem(IN_EXECUTION_PRODUCTION_ORDER, "List production orders by status",
                new ListProductionOrdersByStatusAction());
        menu.addItem(IMPORT_PRODUCTION_ORDER_CSV, "Import Production Orders with CSV",
                new ImportProductionOrderCsvAction());
        menu.addItem(CONSULT_PRODUCTION_ORDERS_OF_ORDER, "Consult Production Orders of a Order",
                new ConsultProductionOrdersOfOrderAction());
        menu.addItem(REGISTER_PRODUCTION_ORDER, "Register Prodution Order",
                new RegisterProductionOrderAction());

        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);
        return menu;
    }

    private Menu buildNotificationMenu(){
        final Menu menu = new Menu("Notifications");
        menu.addItem(ARCHIVE_NOTIFICATION, "Archive notifications", new ArchiveNotificationAction());
        menu.addItem(CONSULT_UNTREATED_NOTIFICATIONS, "Consult Untreated Notifications", new ConsultUntreatedNotificationsAction());
        menu.addItem(CONSULT_ARCHIVED_NOTIFICATIONS, "Consult Archived Notifications", new ConsultArchivedNotificationsAction());
        return menu;
    }

    private Menu buildAdminSettingsMenu() {
        final Menu menu = new Menu("Settings >");

        menu.addItem(SET_KITCHEN_ALERT_LIMIT_OPTION, "Set kitchen alert limit",
                new ShowMessageAction("Not implemented yet"));
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

    private Menu buildUsersMenu() {
        final Menu menu = new Menu("Users >");

        menu.addItem(ADD_USER_OPTION, "Add User", new AddUserUI()::show);
        menu.addItem(LIST_USERS_OPTION, "List all Users", new ListUsersAction());
        menu.addItem(DEACTIVATE_USER_OPTION, "Deactivate User", new DeactivateUserAction());
        menu.addItem(ACCEPT_REFUSE_SIGNUP_REQUEST_OPTION, "Accept/Refuse Signup Request",
                new AcceptRefuseSignupRequestAction());
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

}
