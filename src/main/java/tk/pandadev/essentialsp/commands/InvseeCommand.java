package tk.pandadev.essentialsp.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import tk.pandadev.essentialsp.Main;

import java.util.ArrayList;
import java.util.List;

public class InvseeCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getPrefix() + "§6Du musst diesen Command als Spieler ausführen!");
            return false;
        }

        Player player = (Player) (sender);

        if (args.length == 1){

            if (player.hasPermission("essentialsp.invsee")) {

                Player target = Bukkit.getPlayer(args[0]);

                if (target != null){

                    if (target != player){
                        player.openInventory(target.getInventory());
                    } else {
                        player.sendMessage(Main.getPrefix() + "§cDu kannst nicht dein eigenes Inventar anschauen");
                    }

                }else {
                    player.sendMessage(Main.getInvalidPlayer());
                }

            } else {
                player.sendMessage(Main.getNoPerm());
            }


        } else {
            player.sendMessage(Main.getPrefix() + "§c/invsee <player>");
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        ArrayList<String> list = new ArrayList<String>();
        Player playert = (Player)(sender);

        if (args.length == 1 && playert.hasPermission("essentialsp.invsee")){
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
