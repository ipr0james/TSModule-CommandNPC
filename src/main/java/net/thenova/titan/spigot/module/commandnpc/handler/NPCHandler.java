package net.thenova.titan.spigot.module.commandnpc.handler;

import de.arraying.kotys.JSON;
import de.arraying.kotys.JSONArray;
import net.thenova.titan.library.file.FileHandler;
import net.thenova.titan.library.file.json.JSONFile;
import net.thenova.titan.spigot.module.data.JSONFileModuleData;

import java.util.ArrayList;
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
public enum NPCHandler {
    INSTANCE;

    private JSONFile file;

    public void load() {
        this.file = FileHandler.INSTANCE.loadJSONFile(new JSONFileModuleData("commandnpc", null));
    }

    /**
     * Retrieve commands bound to a specific NPC
     *
     * @param id - ID of the NPC
     * @return - Return commands
     */
    public List<String> getCommands(int id) {
        List<String> rtn = new ArrayList<>();
        JSONArray array = this.file.getJSON().array(String.valueOf(id));

        if(array == null) {
            return null;
        }

        for(int i = 0; i < array.length(); i++) {
            rtn.add(array.string(i));
        }

        return rtn;
    }

    /**
     * Add a command to execute for an NPC
     *
     * @param id - ID of the NPC
     * @param cmd - Command to be executed
     */
    public void addCommand(int id, String cmd) {
        final JSON json = this.file.getJSON();
        final JSONArray commands = json.has(String.valueOf(id)) ? json.array(String.valueOf(id)) : new JSONArray();
        commands.append(cmd);

        json.put(String.valueOf(id), commands);
        this.save();
    }

    /**
     * Reset commands for an NPC
     *
     * @param id - ID of the NPC
     */
    public void resetCommands(int id) {
        final JSON json = this.file.getJSON();

        if(json.has(String.valueOf(id))) {
            json.remove(String.valueOf(id));
            this.save();
        }
    }

    /**
     * Saves the updated JSON back to JSON File
     */
    private void save() {
        this.file.save();
    }
}
