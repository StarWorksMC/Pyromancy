package com.stapletonsinsanegaming.pyromancy;

import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.FireAbility;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

/**
 * A move that allows the bender to set a block on fire!
 * @author OutLaw
 */
public class pyromancy extends FireAbility implements AddonAbility {

    private Listener listener;
    private Permission perm;

    private Block fire;

    public pyromancy(Player player, Block block, BlockFace blockFace) {
        super(player);

        bPlayer.addCooldown(this);
        setOnFire(block, blockFace);
        start();
    }

    private void setOnFire(Block block, BlockFace blockFace) {
        fire = block.getRelative(blockFace);
        fire.setType(Material.FIRE);
    }

    @Override
    public void progress() {
        Location loc = fire.getLocation();
        loc.add(0.5, 0.5, 0.5);
        loc.getWorld().spawnParticle(Particle.FLAME, loc, 10, 0.5, 0.2, 0.8, 0.1);

        if (getStartTime() + 3000 < System.currentTimeMillis()) {
            remove();
        }
    }

    @Override
    public Location getLocation() {
        return player.getLocation();
    }

    @Override
    public long getCooldown() {
        return 1500;
    }

    @Override
    public String getDescription() {
        return "A cool small move that allows firebenders to set a block on fire!";
    }

    @Override
    public void load() {
        listener = new PyromancyListener();
        ProjectKorra.plugin.getServer().getPluginManager().registerEvents(listener, ProjectKorra.plugin);
        perm = new Permission("bending.ability.pyromancy");
        perm.setDefault(PermissionDefault.OP);
        ProjectKorra.plugin.getServer().getPluginManager().addPermission(perm);
    }

    @Override
    public void stop() {
        HandlerList.unregisterAll(listener);
        ProjectKorra.plugin.getServer().getPluginManager().removePermission(perm);
    }

    @Override
    public boolean isSneakAbility() {
        return false;
    }

    @Override
    public boolean isHarmlessAbility() {
        return false;
    }

    @Override
    public String getAuthor() {
        return "OutLaw";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public String getName() {
        return "Pyromancy";
    }
}