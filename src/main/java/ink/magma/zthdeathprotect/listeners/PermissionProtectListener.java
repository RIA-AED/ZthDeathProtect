package ink.magma.zthdeathprotect.listeners;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PermissionProtectListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (event.getKeepInventory()) return; // 如果已经死亡不掉落不做处理
        Player player = event.getPlayer();

        // 检查玩家是否有特定权限
        if (player.hasPermission("zth.deathprotect.fullkeep")) {
            event.setKeepInventory(true); // 保留物品
            event.getDrops().clear(); // 清空掉落物
            event.setShouldDropExperience(false); // 不掉落经验
            player.sendMessage(MiniMessage.miniMessage().deserialize("<gray>您触发了一次死亡不掉落。</gray>"));
        }
    }
}