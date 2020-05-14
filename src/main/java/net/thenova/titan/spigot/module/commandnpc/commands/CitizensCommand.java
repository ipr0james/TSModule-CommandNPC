package net.thenova.titan.spigot.module.commandnpc.commands;

import net.citizensnpcs.api.command.Command;
import net.citizensnpcs.api.command.CommandContext;
import net.citizensnpcs.api.command.Requirements;
import net.citizensnpcs.api.npc.NPC;
import net.thenova.titan.spigot.data.message.MessageHandler;
import net.thenova.titan.spigot.data.message.placeholders.Placeholder;
import net.thenova.titan.spigot.module.commandnpc.handler.NPCHandler;
import org.bukkit.command.CommandSender;

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
@Requirements(selected=true, ownership=true)
public final class CitizensCommand {

    @Command(aliases={"npc"}, usage="cmdadd <command...>", desc="Add a command to a NPC", modifiers={"cmdadd"}, min=2, permission="titan.module.commandnpc.admin")
    public void addCmd(CommandContext args, CommandSender sender, NPC npc) {
        final String cmd = args.getJoinedStrings(1);

        if (cmd != null) {
            NPCHandler.INSTANCE.addCommand(npc.getId(), cmd);
            MessageHandler.INSTANCE.build("module.commandnpc.command.added")
                    .placeholder(new Placeholder("command", cmd), new Placeholder("npc", npc.getId() + ""))
                    .send(sender);
        } else {
            MessageHandler.INSTANCE.build("error.command.usage.length" +
                    "")
                    .placeholder(new Placeholder("usage", "/npc cmdadd <command>"))
                    .send(sender);

        }
    }

    @Command(aliases={"npc"}, usage="cmdreset", desc="Reset the commands on the NPC.", modifiers={"cmdreset"}, min=1, max=1, permission="titan.module.commandnpc.admin")
    public void resetCmd(CommandContext args, CommandSender sender, NPC npc) {
        NPCHandler.INSTANCE.resetCommands(npc.getId());
        MessageHandler.INSTANCE.build("module.commandnpc.command.reset")
                .placeholder(new Placeholder("npc", npc.getId() + ""))
                .send(sender);
    }
}
