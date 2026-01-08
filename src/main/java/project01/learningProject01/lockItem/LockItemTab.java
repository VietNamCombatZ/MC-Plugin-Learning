package project01.learningProject01.lockItem;


import org.bukkit.command.*;

import java.util.List;

public class LockItemTab implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender,
                                      Command command,
                                      String alias,
                                      String[] args) {
        return List.of();
    }
}

