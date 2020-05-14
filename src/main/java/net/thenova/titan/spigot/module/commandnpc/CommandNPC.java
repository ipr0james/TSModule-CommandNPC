package net.thenova.titan.spigot.module.commandnpc;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.command.CommandManager;
import net.thenova.titan.library.Titan;
import net.thenova.titan.library.command.data.Command;
import net.thenova.titan.library.database.connection.IDatabase;
import net.thenova.titan.library.database.sql.table.DatabaseTable;
import net.thenova.titan.spigot.data.message.MessageHandler;
import net.thenova.titan.spigot.module.commandnpc.commands.CitizensCommand;
import net.thenova.titan.spigot.module.commandnpc.handler.NPCHandler;
import net.thenova.titan.spigot.module.commandnpc.listeners.InteractEvent;
import net.thenova.titan.spigot.plugin.IPlugin;
import net.thenova.titan.spigot.users.user.module.UserModule;
import org.bukkit.event.Listener;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

/**
 * Copyright 2019 ipr0james
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public final class CommandNPC implements IPlugin {
    @Override
    public String name() {
        return "CommandNPC";
    }

    @Override
    public void load() {
        NPCHandler.INSTANCE.load();

        this.register();
    }

    @Override
    public void messages(final MessageHandler handler) {
        handler.add("module.commandnpc.command.reset", "%prefix.info% Commands for '&d%reset%&7' have been reset");
        handler.add("module.commandnpc.command.added", "%prefix.info% Command '&d%command%&7' has been added to the NPC '&d%npc%&7a'");
    }

    @Override
    public void reload() {

    }

    @Override
    public void shutdown() {

    }

    @Override
    public IDatabase database() {
        return null;
    }

    @Override
    public List<DatabaseTable> tables() {
        return null;
    }

    @Override
    public List<Listener> listeners() {
        return Collections.singletonList(new InteractEvent());
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<Command> commands() {
        return null;
    }

    @Override
    public List<Class<? extends UserModule>> user() {
        return null;
    }

    private void register() {
        try {
            Field field = CitizensAPI.getPlugin().getClass().getDeclaredField("commands");
            field.setAccessible(true);
            Object obj = field.get(CitizensAPI.getPlugin());
            CommandManager manager = (CommandManager)obj;
            manager.register(CitizensCommand.class);
            field.setAccessible(false);
        } catch (Exception ex) {
            Titan.INSTANCE.getLogger().info("[CommandNPC] - Failed to register command", ex);
        }
    }
}
