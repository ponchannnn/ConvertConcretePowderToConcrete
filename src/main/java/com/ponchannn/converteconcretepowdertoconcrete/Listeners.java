package com.ponchannn.converteconcretepowdertoconcrete;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Listeners implements Listener {
    private final ConverteConcretePowderToConcrete converteConcretePowderToConcrete;
    private final Map<UUID, BukkitRunnable> itemTasks = new HashMap<>();

    public Listeners (ConverteConcretePowderToConcrete converteConcretePowderToConcrete) {
        this.converteConcretePowderToConcrete = converteConcretePowderToConcrete;
    }

    @EventHandler
    public void onItemSpawn(ItemSpawnEvent event) {
        // アイテムがスポーンしたときのイベントをキャッチ
        Item item = event.getEntity();
        if (isConcretePowder(event.getEntity().getItemStack().getType())) {
            BukkitRunnable task = new BukkitRunnable() {
                @Override
                public void run() {
                    // アイテムが水に触れたか確認
                    if (item.isInWater()) {
                        // コンクリートに変換
                        item.getItemStack().setType(getConcreteType(item.getItemStack().getType()));
                        this.cancel(); // タスクを終了
                        itemTasks.remove(item.getUniqueId()); // マップからタスクを削除
                    }
                }
            };

            task.runTaskTimer(converteConcretePowderToConcrete, 0L, 5L);
            itemTasks.put(item.getUniqueId(), task);
        }
    }
    // アイテムが拾われたとき、またはデスポーンしたときにタスクをキャンセルする
    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent event) {
        cancelTaskIfExists(event.getItem());
    }

    @EventHandler
    public void onItemDespawn(ItemDespawnEvent event) {
        cancelTaskIfExists(event.getEntity());
    }

    // アイテムに関連付けられたタスクが存在する場合にキャンセルするメソッド
    private void cancelTaskIfExists(Item item) {
        UUID itemId = item.getUniqueId();
        if (itemTasks.containsKey(itemId)) {
            itemTasks.get(itemId).cancel(); // タスクをキャンセル
            itemTasks.remove(itemId); // マップから削除
        }
    }
    // コンクリートパウダーの種類かどうかを確認
    private boolean isConcretePowder(Material material) {
        return material == Material.WHITE_CONCRETE_POWDER ||
                material == Material.ORANGE_CONCRETE_POWDER ||
                material == Material.MAGENTA_CONCRETE_POWDER ||
                material == Material.LIGHT_BLUE_CONCRETE_POWDER ||
                material == Material.YELLOW_CONCRETE_POWDER ||
                material == Material.LIME_CONCRETE_POWDER ||
                material == Material.PINK_CONCRETE_POWDER ||
                material == Material.GRAY_CONCRETE_POWDER ||
                material == Material.LIGHT_GRAY_CONCRETE_POWDER ||
                material == Material.CYAN_CONCRETE_POWDER ||
                material == Material.PURPLE_CONCRETE_POWDER ||
                material == Material.BLUE_CONCRETE_POWDER ||
                material == Material.BROWN_CONCRETE_POWDER ||
                material == Material.GREEN_CONCRETE_POWDER ||
                material == Material.RED_CONCRETE_POWDER ||
                material == Material.BLACK_CONCRETE_POWDER;
    }

    // コンクリートパウダーをコンクリートに変換
    private Material getConcreteType(Material powder) {
        switch (powder) {
            case WHITE_CONCRETE_POWDER: return Material.WHITE_CONCRETE;
            case ORANGE_CONCRETE_POWDER: return Material.ORANGE_CONCRETE;
            case MAGENTA_CONCRETE_POWDER: return Material.MAGENTA_CONCRETE;
            case LIGHT_BLUE_CONCRETE_POWDER: return Material.LIGHT_BLUE_CONCRETE;
            case YELLOW_CONCRETE_POWDER: return Material.YELLOW_CONCRETE;
            case LIME_CONCRETE_POWDER: return Material.LIME_CONCRETE;
            case PINK_CONCRETE_POWDER: return Material.PINK_CONCRETE;
            case GRAY_CONCRETE_POWDER: return Material.GRAY_CONCRETE;
            case LIGHT_GRAY_CONCRETE_POWDER: return Material.LIGHT_GRAY_CONCRETE;
            case CYAN_CONCRETE_POWDER: return Material.CYAN_CONCRETE;
            case PURPLE_CONCRETE_POWDER: return Material.PURPLE_CONCRETE;
            case BLUE_CONCRETE_POWDER: return Material.BLUE_CONCRETE;
            case BROWN_CONCRETE_POWDER: return Material.BROWN_CONCRETE;
            case GREEN_CONCRETE_POWDER: return Material.GREEN_CONCRETE;
            case RED_CONCRETE_POWDER: return Material.RED_CONCRETE;
            case BLACK_CONCRETE_POWDER: return Material.BLACK_CONCRETE;
            default: return powder; // 万が一のためのデフォルト
        }
    }
}
