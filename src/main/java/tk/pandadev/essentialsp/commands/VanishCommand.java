package tk.pandadev.essentialsp.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import tk.pandadev.essentialsp.Main;
import tk.pandadev.essentialsp.utils.VanishManager;

import java.util.ArrayList;
import java.util.List;

public class VanishCommand implements CommandExecutor, TabCompleter {

    public Plugin plugin;

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        VanishManager manager = Main.getInstance().getVanishManager();

        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getPrefix() + "§6Du musst diesen Command als Spieler ausführen!");
            return false;
        }

        Player player = (Player) (sender);

        if (args.length == 1) {

            if (player.hasPermission("essentialsp.vanish.other")) {

                Player target = Bukkit.getPlayer(args[0]);

                if (target != null){
                    if (manager.isVanished(target)){
                        manager.setVanished(target, false);
                        player.sendMessage(Main.getPrefix() + "§7Der Spieler §a" + target.getName() + "§7 ist nicht mehr im Vanish");
                    } else{
                        manager.setVanished(target, true);
                        player.sendMessage(Main.getPrefix() + "§7Der Spieler §a" + target.getName() + "§7 ist jetzt im Vanish");
                    }
                } else {
                    player.sendMessage(Main.getInvalidPlayer());
                }

            } else {
                player.sendMessage(Main.getNoPerm());
            }


        } else if (args.length == 0){

            if (player.hasPermission("essentialsp.vanish")) {

                if (manager.isVanished(player)){
                    manager.setVanished(player, false);
                    player.sendMessage(Main.getPrefix() + "§7Du bist nicht mehr Vanish");
                } else{
                    manager.setVanished(player, true);
                    player.sendMessage(Main.getPrefix() + "§7Du bist nun im Vanish");
                }

            } else {
                player.sendMessage(Main.getNoPerm());
            }


        } else {
            player.sendMessage(Main.getPrefix() + "§c/vanish <player>");
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        ArrayList<String> list = new ArrayList<String>();
        Player playert = (Player)(sender);

        if (args.length == 1 && playert.hasPermission("essentialsp.vanish.other")){
            for (Player player : Bukkit.getOnlinePlayers()) {
                list.add(player.getName());
            }
        }

        ArrayList<String> completerList = new ArrayList<String>();
        String currentarg = args[args.length - 1].toLowerCase();
        for (String s : list) {
            String s1 = s.toLowerCase();
            if (!s1.startsWith(currentarg)) continue;
            completerList.add(s);
        }

        return completerList;
    }
}