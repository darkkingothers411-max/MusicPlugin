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

        switch (cmd.getName().toLowerCase()) {

            case "playsong" -> {
                if (args.length == 0) {
                    sender.sendMessage("§cUsage: §f/playsong <songname>");
                    sender.sendMessage("§7Use §f/songlist §7to see all songs.");
                    return true;
                }

                String songName = args[0].toLowerCase();

                if (!SONGS.contains(songName)) {
                    sender.sendMessage("§cSong not found: §e" + songName);
                    sender.sendMessage("§7Use §f/songlist §7to see available songs.");
                    return true;
                }

                // Play for ALL online players anywhere on the map
                for (Player p : getServer().getOnlinePlayers()) {
                    // Stop any current music first
                    getServer().dispatchCommand(
                        getServer().getConsoleSender(),
                        "stopsound " + p.getName()
                    );
                    // Play at the player's own location so it works anywhere
                    getServer().dispatchCommand(
                        getServer().getConsoleSender(),
                        "execute at " + p.getName() + " run playsound custom."
                        + songName + " music " + p.getName() + " ~ ~ ~ 100 1"
                    );
                }

                // Broadcast message to all players
                getServer().broadcastMessage("§6§l🎵 Now playing: §e" + songName
                    + " §7(requested by §f" + sender.getName() + "§7)");
                getServer().broadcastMessage("§7Type §f/stopsong §7to stop.");
            }

            case "stopsong" -> {
                // Stop music for ALL online players
                for (Player p : getServer().getOnlinePlayers()) {
                    getServer().dispatchCommand(
                        getServer().getConsoleSender(),
                        "stopsound " + p.getName()
                    );
                }
                getServer().broadcastMessage("§c🔇 Music stopped by §f" + sender.getName());
            }

            case "songlist" -> {
                sender.sendMessage("§6§l======= Song List =======");
                for (int i = 0; i < SONGS.size(); i++) {
                    sender.sendMessage("§7" + (i + 1) + ". §f" + SONGS.get(i));
                }
                sender.sendMessage("§6§l=========================");
                sender.sendMessage("§7Total: §f" + SONGS.size() + " songs");
                sender.sendMessage("§7Type: §f/playsong <songname>");
            }
        }
        return true;
    }

    @Override
    public void onDisable() {
        getLogger().info("MusicPlugin disabled.");
    }
}
