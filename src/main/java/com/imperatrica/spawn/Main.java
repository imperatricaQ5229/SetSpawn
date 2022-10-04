package com.imperatrica.spawn;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.Listener;
import cn.nukkit.level.Location;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import java.io.File;

public class Main extends PluginBase implements Listener {
    public Main() {
    }

    public void onEnable() {
        this.getDataFolder().mkdirs();
        this.saveResource("config.yml");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        Config cfg = new Config(new File(this.getDataFolder(), "config.yml"));
        if (cmd.getName().toLowerCase().equals("setspawn")) {
            double x = (double)player.getFloorX();
            double y = (double)player.getFloorY();
            double z = (double)player.getFloorZ();
            String world = player.getLevel().getName();
            cfg.set("x", x);
            cfg.set("y", y);
            cfg.set("z", z);
            cfg.set("world", world);
            cfg.save();
            player.sendMessage(TextFormat.colorize("&l&bВы &fустановили точку &cспавна!"));
        }

        if (cmd.getName().toLowerCase().equals("spawn")) {
            if (cfg.get("x") != null && cfg.get("y") != null && cfg.get("z") != null && cfg.get("world") != null) {
                player.teleport(new Location(cfg.getDouble("x"), cfg.getDouble("y"), cfg.getDouble("z"), this.getServer().getLevelByName(cfg.getString("world"))));
                player.sendMessage(TextFormat.colorize(cfg.getString("message")));
            } else {
                sender.sendMessage(TextFormat.colorize("&l&bТочка спавна &cне установлена &fна &cсервере."));
            }
        }

        return false;
    }
}