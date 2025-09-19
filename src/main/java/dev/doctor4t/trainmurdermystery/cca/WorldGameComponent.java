package dev.doctor4t.trainmurdermystery.cca;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WorldGameComponent implements AutoSyncedComponent {
    private final World world;

    private boolean running = false;
    private int fadeIn = -1;
    private int fadeOut = -1;

    private List<UUID> players = new ArrayList<>();
    private List<UUID> hitmen = new ArrayList<>();
    private List<UUID> detectives = new ArrayList<>();
    private List<UUID> targets = new ArrayList<>();

    public WorldGameComponent(World world) {
        this.world = world;
    }

    public void sync() {
        TMMComponents.GAME.sync(this.world);
    }

    public void start() {
        this.setRunning(true);
        this.sync();
    }

    public void stop() {
        this.setRunning(false);
        this.sync();
    }

    public int getFadeIn() {
        return fadeIn;
    }

    public void setFadeIn(int getFadeIn) {
        this.fadeIn = getFadeIn;
        this.sync();
    }

    public int getFadeOut() {
        return fadeOut;
    }

    public void setFadeOut(int fadeOut) {
        this.fadeOut = fadeOut;
        this.sync();
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isRunning() {
        return this.running;
    }

    public List<UUID> getHitmen() {
        return this.hitmen;
    }

    public void addHitman(PlayerEntity hitman) {
        addHitman(hitman.getUuid());
    }

    public void addHitman(UUID hitman) {
        this.hitmen.add(hitman);
    }

    public void setHitmen(List<UUID> hitmen) {
        this.hitmen = hitmen;
    }

    public List<UUID> getDetectives() {
        return this.detectives;
    }

    public void addDetective(PlayerEntity detective) {
        addDetective(detective.getUuid());
    }

    public void addDetective(UUID detective) {
        this.detectives.add(detective);
    }

    public void setDetectives(List<UUID> detectives) {
        this.detectives = detectives;
    }

    public List<UUID> getTargets() {
        return this.targets;
    }

    public void addTarget(PlayerEntity target) {
        addTarget(target.getUuid());
    }

    public void addTarget(UUID target) {
        this.targets.add(target);
    }

    public void setTargets(List<UUID> targets) {
        this.targets = targets;
    }

    public boolean isCivilian(@NotNull PlayerEntity player) {
        return !this.hitmen.contains(player.getUuid()) && !this.detectives.contains(player.getUuid());
    }

    public boolean isHitman(@NotNull PlayerEntity player) {
        return this.hitmen.contains(player.getUuid());
    }

    public boolean isDetective(@NotNull PlayerEntity player) {
        return this.detectives.contains(player.getUuid());
    }

    public void resetLists() {
        setDetectives(new ArrayList<>());
        setHitmen(new ArrayList<>());
        setTargets(new ArrayList<>());
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.running = nbtCompound.getBoolean("Running");

        this.fadeIn = nbtCompound.getInt("FadeIn");
        this.fadeOut = nbtCompound.getInt("FadeOut");

        this.setTargets(uuidListFromNbt(nbtCompound, "Targets"));
        this.setHitmen(uuidListFromNbt(nbtCompound, "Hitmen"));
        this.setDetectives(uuidListFromNbt(nbtCompound, "Detectives"));
    }

    private ArrayList<UUID> uuidListFromNbt(NbtCompound nbtCompound, String listName) {
        ArrayList<UUID> ret = new ArrayList<>();
        for (NbtElement e : nbtCompound.getList(listName, NbtElement.INT_ARRAY_TYPE)) {
            ret.add(NbtHelper.toUuid(e));
        }
        return ret;
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putBoolean("Running", this.running);

        nbtCompound.putInt("FadeIn", fadeIn);
        nbtCompound.putInt("FadeOut", fadeOut);

        nbtCompound.put("Targets", nbtFromUuidList(getTargets()));
        nbtCompound.put("Hitmen", nbtFromUuidList(getHitmen()));
        nbtCompound.put("Detectives", nbtFromUuidList(getDetectives()));
    }

    private NbtList nbtFromUuidList(List<UUID> list) {
        NbtList ret = new NbtList();
        for (UUID player : list) {
            ret.add(NbtHelper.fromUuid(player));
        }
        return ret;
    }

}
