package com.example.musicplugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public class MusicPlugin extends JavaPlugin {

    private static final List<String> SONGS = Arrays.asList(
        "chismechisme",
        "darkage",
        "devilpunjab",
        "diraco",
        "dirtyshoe",
        "eeyuh",
        "goldenbrown",
        "growagarden",
        "heatwaves",
        "homage",
        "indila",
        "inna",
        "leanon",
        "lightitup",
        "loco",
        "memory",
        "midu",
        "mimami",
        "omelhor",
        "onandon",
        "parano",
        "playdate",
        "slickback",
        "slickback1",
        "stereohearts",
        "supersonic",
        "sweater",
        "trillium",
        "violin",
        "washing",
        "xonada"
    );

    private int getMinorVersion() {
        try {
            String version = Bukkit.getBukkitVersion();
            String[] parts = version.split("\\.");
            return Integer.parseInt(parts[1].split("-")[0]);
        } catch (Exception e) {
            return 21;
        }
    }

    private void stopForPlayer(Player p) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
            "stopsound " + p.getName());
    }

    private void playForPlayer(Player p, String songName) {
        stopForPlayer(p);
        int version = getMinorVersion();
        if (version >= 20) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                "execute at " + p.getName() + " run playsound custom."
                + songName + " music " + p.getName() + " ~ ~ ~ 100 1");
        } else {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                "playsound custom." + songName + " music " + p.getName()
                + " " + p.getLocation().getBlockX()
                + " " + p.getLocation().getBlockY()
                + " " + p.getLocation().getBlockZ()
                + " 100 1");
        }
    }

    @Override
    public void onEnable() {
        getLogger().info("MusicPlugin enabled! " + SONGS.size() + " songs loaded.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd,
                             String label, String[] args) {

        String command = cmd.getName().toLowerCase();

        if (command.equals("playsong")) {
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
            for (Player p : Bukkit.getOnlinePlayers()) {
                playForPlayer(p, songName);
            }
            Bukkit.broadcastMessage("§6§l🎵 Now playing for everyone: §e" + songName
                + " §7(by §f" + sender.getName() + "§7)");
            Bukkit.broadcastMessage("§7Type §f/stopsong §7to stop.");

        } else if (command.equals("playmine")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cOnly players can use this command!");
                return true;
            }
            if (args.length == 0) {
                sender.sendMessage("§cUsage: §f/playmine <songname>");
                sender.sendMessage("§7Use §f/songlist §7to see all songs.");
                return true;
            }
            String songName = args[0].toLowerCase();
            if (!SONGS.contains(songName)) {
                sender.sendMessage("§cSong not found: §e" + songName);
                sender.sendMessage("§7Use §f/songlist §7to see available songs.");
                return true;
            }
            Player player = (Player) sender;
            playForPlayer(player, songName);
            player.sendMessage("§a🎵 Now playing for you: §e" + songName);
            player.sendMessage("§7Type §f/stopmine §7to stop.");

        } else if (command.equals("stopsong")) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                stopForPlayer(p);
            }
            Bukkit.broadcastMessage("§c🔇 Music stopped for everyone by §f" + sender.getName());

        } else if (command.equals("stopmine")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cOnly players can use this command!");
                return true;
            }
            Player player = (Player) sender;
            stopForPlayer(player);
            player.sendMessage("§c🔇 Your music stopped.");

        } else if (command.equals("songlist")) {
            sender.sendMessage("§6§l======= Song List =======");
            for (int i = 0; i < SONGS.size(); i++) {
                sender.sendMessage("§7" + (i + 1) + ". §f" + SONGS.get(i));
            }
            sender.sendMessage("§6§l=========================");
            sender.sendMessage("§7Total: §f" + SONGS.size() + " songs");
            sender.sendMessage("§aFor everyone: §f/playsong <name>");
            sender.sendMessage("§aFor yourself: §f/playmine <name>");
        }

        return true;
    }

    @Override
    public void onDisable() {
        getLogger().info("MusicPlugin disabled.");
    }
}
