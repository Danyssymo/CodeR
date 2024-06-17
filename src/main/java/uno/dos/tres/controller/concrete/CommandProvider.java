package uno.dos.tres.controller.concrete;

import uno.dos.tres.controller.concrete.impl.*;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {

    private Map<CommandName, Command> commands = new HashMap<>();

    public CommandProvider() {
        commands.put(CommandName.NO_SUCH_COMMAND, new NoSuchCommand());
        commands.put(CommandName.GO_TO_WELCOME_PAGE, new GoToWelcomePage());
        commands.put(CommandName.GO_TO_MAIN_PAGE, new GoToMainPage());
        commands.put(CommandName.DO_AUTH, new DoAuth());
        commands.put(CommandName.DO_REGISTRATION, new DoRegistration());
        commands.put(CommandName.DO_LOGOUT, new Logout());
        commands.put(CommandName.CHANGE_LOCALE, new ChangeLocal());
        commands.put(CommandName.ADD_NEWS, new AddNews());
        commands.put(CommandName.DELETE_NEWS, new DeleteNews());
    }

    public Command takeCommand(String userCommand) {
        CommandName commandName;
        Command command;

        try {
            commandName = CommandName.valueOf(userCommand.toUpperCase());
            command = commands.get(commandName);
        }catch(IllegalArgumentException | NullPointerException e) {
            command = commands.get(CommandName.NO_SUCH_COMMAND);
        }

        return command;
    }

}
