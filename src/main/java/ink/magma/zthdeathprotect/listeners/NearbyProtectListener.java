package ink.magma.zthdeathprotect.listeners;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.ArrayList;
import java.util.List;

public class NearbyProtectListener implements Listener {
    List<String> protectedList = new ArrayList<>();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (event.getKeepInventory()) return; // 如果已经死亡不掉落不做处理
        Player player = event.getPlayer();
        Location deathLocation = player.getLocation();

        // 遍历当前世界的玩家, 找出此玩家附近 64*64 有多少玩家（4*4区块的范围）
        int nearByCount = 0;
        for (Player playerThisWorld : deathLocation.getWorld().getPlayers()) {
            int xDistance = (int) Math.abs(playerThisWorld.getLocation().getX() - deathLocation.getX());
            int zDistance = (int) Math.abs(playerThisWorld.getLocation().getZ() - deathLocation.getZ());

            if (xDistance <= 32 && zDistance <= 32) {
                nearByCount++;
            }
        }
        // 玩家不足 8 个, 放弃
        if (nearByCount < 8) return;

        event.getDrops().clear();
        event.setKeepInventory(true);
        protectedList.add(player.getName());
    }

    @EventHandler
    public void onDeathRespawn(PlayerRespawnEvent event) {
        if (protectedList.contains(event.getPlayer().getName())) {
            protectedList.remove(event.getPlayer().getName());
            event.getPlayer().sendMessage(MiniMessage.miniMessage().deserialize("""


                     <red>您刚才触发了一次死亡掉落保护, 这是因为您附近的玩家较多,
                     <red>为了避免您难以找回物品.
                     <gray>请小心您的随身物品! 这并不总是有效, 您的经验仍然损失了.

                    """));

        }
    }
}