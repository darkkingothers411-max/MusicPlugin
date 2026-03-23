package com.example.musicplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public class MusicPlugin extends JavaPlugin {

    private static final List<String> SONGS = Arrays.asList(
        "assalamualayka",
        "assalamualayka1",
        "chismechisme",
        "darkage",
        "diraco",
        "dirtyshoe",
        "eeyuh",
        "goldenbrown",
        "growagarden",
        "heatwaves",
        "homage",
        "iloveyouso",
        "indila",
        "memory",
        "midu",
        "mimami",
        "muhammad",
        "muhammadnabina",
        "omelhor",
        "palpal",
        "palpal1",
        "parano",
        "slickback",
        "slickback1",
        "stereohearts",
        "supersonic",
        "sweater",
        "trillium",
        "washing",
        "xonada"
    );

    @Override
    public void onEnable() {
        getLogger().info("MusicPlugin enabled! " + SONGS.size() + " songs loaded.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd,
                             String label, String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        switch (cmd.getName().toLowerCase()) {

            case "playsong" -> {
                if (args.length == 0) {
                    player.sendMessage("§cUsage: §f/playsong <songname>");
                    player.sendMessage("§7Use §f/songlist §7to see all songs.");
                    return true;
                }

                String songName = args[0].toLowerCase();

                if (!SONGS.contains(songName)) {
                    player.sendMessage("§cSong not found: §e" + songName);
                    player.sendMessage("§7Use §f/songlist §7to see available songs.");
                    return true;
                }

                getServer().dispatchCommand(getServer().getConsoleSender(),
                    "stopsound " + player.getName() + " music *");

                getServer().dispatchCommand(getServer().getConsoleSender(),
                    "playsound custom." + songName + " music " +
                    player.getName() + " ~ ~ ~ 1 1");

                player.sendMessage("§aNow playing: §e" + songName);
                player.sendMessage("§7Use §f/stopsong §7to stop.");
            }

            case "stopsong" -> {
                getServer().dispatchCommand(getServer().getConsoleSender(),
                    "stopsound " + player.getName() + " music *");
                player.sendMessage("§cMusic stopped.");
            }

            case "songlist" -> {
                player.sendMessage("§6§l======= Song List =======");
                for (int i = 0; i < SONGS.size(); i++) {
                    player.sendMessage("§7" + (i + 1) + ". §f" + SONGS.get(i));
                }
                player.sendMessage("§6§l=========================");
                player.sendMessage("§7Total: §f" + SONGS.size() + " songs");
                player.sendMessage("§7Type: §f/playsong <name>");
            }
        }
        return true;
    }

    @Override
    public void onDisable() {
        getLogger().info("MusicPlugin disabled.");
    }
}
