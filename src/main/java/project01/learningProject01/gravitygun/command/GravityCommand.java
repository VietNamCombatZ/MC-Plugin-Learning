package project01.learningProject01.gravitygun.command;

import project01.learningProject01.gravitygun.GravityGunItem;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class GravityCommand implements CommandExecutor, TabCompleter {

    private final JavaPlugin plugin;

    public GravityCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    // ================= COMMAND =================

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            sender.sendMessage("§cSử dụng: /gravity <give|reload>");
            return true;
        }

        switch (args[0].toLowerCase()) {

            case "give":
                handleGive(sender, args);
                break;

            case "reload":
                handleReload(sender);
                break;

            default:
                sender.sendMessage("§cLệnh không hợp lệ!");
        }

        return true;
    }

    // ================= SUB COMMANDS =================

    private void handleGive(CommandSender sender, String[] args) {

        if (!sender.hasPermission("gravity.admin")) {
            sender.sendMessage("§cBạn không có quyền!");
            return;
        }

        Player target;

        if (args.length >= 2) {
            target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage("§cKhông tìm thấy người chơi!");
                return;
            }
        } else {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cChỉ player mới dùng được!");
                return;
            }
            target = (Player) sender;
        }

        target.getInventory().addItem(GravityGunItem.create(plugin));
        sender.sendMessage("§aĐã đưa Súng Trọng Lực cho §e" + target.getName());
    }

    private void handleReload(CommandSender sender) {

        if (!sender.hasPermission("gravity.admin")) {
            sender.sendMessage("§cBạn không có quyền!");
            return;
        }

        plugin.reloadConfig();
        sender.sendMessage("§aĐã reload config GravityGun!");
    }

    // ================= TAB COMPLETER =================

    @Override
    public List<String> onTabComplete(
            CommandSender sender,
            Command command,
            String alias,
            String[] args
    ) {
        List<String> list = new ArrayList<>();

        if (args.length == 1) {
            list.add("give");
            list.add("reload");
            return list;
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("give")) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                list.add(p.getName());
            }
        }

        return list;
    }
}
