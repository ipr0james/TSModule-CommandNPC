package net.thenova.titan.spigot.module.commandnpc.listeners;

import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.event.NPCRemoveEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.thenova.titan.spigot.data.message.MessageHandler;
import net.thenova.titan.spigot.module.commandnpc.handler.NPCHandler;
import net.thenova.titan.spigot.util.UBungee;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

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
public final class InteractEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onNPCDelete(NPCRemoveEvent event) {
        NPCHandler.INSTANCE.resetCommands(event.getNPC().getId());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRight(NPCRightClickEvent event) {
        this.handle(event.getNPC().getId(), event.getClicker());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLeft(NPCLeftClickEvent event) {
        this.handle(event.getNPC().getId(), event.getClicker());
    }

    private void handle(int id, Player player) {
        final List<String> commands = NPCHandler.INSTANCE.getCommands(id);
        if(commands == null) {
            return;
        }

        commands.forEach(cmd -> {
            if(cmd.startsWith("server:")) {
                UBungee.sendPlayer(player, cmd.split(":")[1]);
            } else if(cmd.startsWith("message:")) {
                MessageHandler.INSTANCE.message(cmd.split(":")[1]).send(player);
            } else {
                player.chat("/" + cmd);
            }
        });
    }
}
