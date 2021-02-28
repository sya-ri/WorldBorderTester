package com.github.syari.test.worldborder

import com.github.syari.spigot.api.command.command
import com.github.syari.spigot.api.command.tab.CommandTabArgument.Companion.argument
import com.github.syari.spigot.api.util.string.toColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

@Suppress("unused")
class Main : JavaPlugin() {
    override fun onEnable() {
        command("wb") {
            tab {
                argument { add("reset", "size", "center", "damage-buffer", "damage-amount", "warning-time", "warning-distance", "is-inside") }
                argument("center") { add("here") }
            }
            execute {
                val player = sender as? Player ?: return@execute sender.send("&cプレイヤーからのみ実行できるコマンドです")
                val worldBorder = player.world.worldBorder
                when (args.lowerOrNull(0)) {
                    "reset" -> {
                        worldBorder.reset()
                        sender.send("&avoid reset()")
                    }
                    "size" -> {
                        val newSize = args.getOrNull(1)?.toDoubleOrNull()
                        if (newSize != null) {
                            val seconds = args.getOrNull(2)?.toLongOrNull()
                            if (seconds != null) {
                                worldBorder.setSize(newSize, seconds)
                                sender.send("&avoid setSize(double newSize&e[$newSize]&a, long seconds&e[$seconds]&a)")
                            } else {
                                worldBorder.size = newSize
                                sender.send("&avoid setSize(double newSize&e[$newSize]&a)")
                            }
                        } else {
                            val size = worldBorder.size
                            sender.send("&adouble getSize() &7-> &a$size")
                        }
                    }
                    "center" -> {
                        if (args.lowerOrNull(1) == "here") {
                            val location = player.location
                            worldBorder.center = location
                            sender.send("&avoid setCenter(Location location&e[$location]&a)")
                        } else {
                            val x = args.getOrNull(1)?.toDoubleOrNull()
                            val z = args.getOrNull(2)?.toDoubleOrNull()
                            if (x != null && z != null) {
                                worldBorder.setCenter(x, z)
                                sender.send("&avoid setCenter(double x&e[$x]&a, double z&e[$z]&a)")
                            } else {
                                val center = worldBorder.center
                                sender.send("&aLocation getCenter() &7-> &a$center")
                            }
                        }
                    }
                    "damage-buffer" -> {
                        val blocks = args.getOrNull(1)?.toDoubleOrNull()
                        if (blocks != null) {
                            worldBorder.damageBuffer = blocks
                            sender.send("&avoid setDamageBuffer(double blocks&e[$blocks]&a)")
                        } else {
                            val damageBuffer = worldBorder.damageBuffer
                            sender.send("&adouble getDamageBuffer() &7-> &a$damageBuffer")
                        }
                    }
                    "damage-amount" -> {
                        val damage = args.getOrNull(1)?.toDoubleOrNull()
                        if (damage != null) {
                            worldBorder.damageAmount = damage
                            sender.send("&avoid setDamageAmount(double damage&e[$damage]&a)")
                        } else {
                            val damageAmount = worldBorder.damageAmount
                            sender.send("&adouble getDamageAmount() &7-> &a$damageAmount")
                        }
                    }
                    "warning-time" -> {
                        val seconds = args.getOrNull(1)?.toIntOrNull()
                        if (seconds != null) {
                            worldBorder.warningTime = seconds
                            sender.send("&avoid setWarningTime(int seconds&e[$seconds]&a)")
                        } else {
                            val warningTime = worldBorder.warningTime
                            sender.send("&aint getWarningTime() &7-> &a$warningTime")
                        }
                    }
                    "warning-distance" -> {
                        val distance = args.getOrNull(1)?.toIntOrNull()
                        if (distance != null) {
                            worldBorder.warningDistance = distance
                            sender.send("&avoid setWarningDistance(int distance&e[$distance]&a)")
                        } else {
                            val warningDistance = worldBorder.warningDistance
                            sender.send("&aint getWarningDistance() &7-> &a$warningDistance")
                        }
                    }
                    "is-inside" -> {
                        val location = player.location
                        val isInside = worldBorder.isInside(location)
                        sender.send("&aboolean isInside(Location location&e[$location]&a) &7-> &a$isInside")
                    }
                    else -> {
                        sender.send(
                            """
                            &fコマンド一覧
                            &7- &a/$label reset &7void reset()
                            &7- &a/$label size &7double getSize()
                            &7- &a/$label size <newSize> &7void setSize(double newSize)
                            &7- &a/$label size <newSize> <seconds> &7void setSize(double newSize, long seconds)
                            &7- &a/$label center &7Location getCenter()
                            &7- &a/$label center <x> <z> &7void setCenter(double x, double z)
                            &7- &a/$label center here &7void setCenter(Location location)
                            &7- &a/$label damage-buffer &7double getDamageBuffer()
                            &7- &a/$label damage-buffer <blocks> &7void setDamageBuffer(double blocks)
                            &7- &a/$label damage-amount &7double getDamageAmount()
                            &7- &a/$label damage-amount <damage> &7void setDamageAmount(double damage)
                            &7- &a/$label warning-time &7int getWarningTime()
                            &7- &a/$label warning-time <seconds> &7void setWarningTime(int seconds)
                            &7- &a/$label warning-distance &7int getWarningDistance()
                            &7- &a/$label warning-distance <distance> &7void setWarningDistance(int distance)
                            &7- &a/$label is-inside &7boolean isInside(Location location)
                            """.trimIndent()
                        )
                    }
                }
            }
        }
    }

    private fun CommandSender.send(message: String) {
        sendMessage("&b[WorldBorder] &r$message".toColor())
    }
}
