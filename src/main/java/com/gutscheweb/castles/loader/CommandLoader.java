package com.gutscheweb.castles.loader;

import org.bukkit.command.Command;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;

public class CommandLoader {
    private final String commandPackage;

    public CommandLoader(String commandPackage) {
        this.commandPackage = commandPackage;
    }
    public void load() {
        Reflections reflections = new Reflections(commandPackage);
        Set<Class<? extends Command>> allCommands = reflections.getSubTypesOf(Command.class);
        for (Class<? extends Command> clazz : allCommands) {
            getLogger().info("Trying to register " + clazz.getName());
            try {
                Command cmd = (Command) clazz.getDeclaredConstructors()[0].newInstance();
                getServer().getCommandMap().register(cmd.getName(), cmd);
                getLogger().info("Successfully registered " + cmd.getName());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                getLogger().severe("Error while trying to register " + clazz.getName() + ": " + e.getMessage());
            }
        }
    }
}
