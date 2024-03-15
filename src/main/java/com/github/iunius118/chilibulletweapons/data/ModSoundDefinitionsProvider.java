package com.github.iunius118.chilibulletweapons.data;

import com.github.iunius118.chilibulletweapons.sounds.ModSoundEvents;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

import java.util.Arrays;

public class ModSoundDefinitionsProvider extends SoundDefinitionsProvider {
    public ModSoundDefinitionsProvider(PackOutput output, String modId, ExistingFileHelper helper) {
        super(output, modId, helper);
    }

    @Override
    public void registerSounds() {
        ResourceLocation[] actionOpenSoundLocations = {
                new ResourceLocation("block/iron_trapdoor/open1"),
                new ResourceLocation("block/iron_trapdoor/open2"),
                new ResourceLocation("block/iron_trapdoor/open3"),
                new ResourceLocation("block/iron_trapdoor/open4")};
        ResourceLocation[] actionCloseSoundLocations = {
                new ResourceLocation("block/iron_trapdoor/close1"),
                new ResourceLocation("block/iron_trapdoor/close2"),
                new ResourceLocation("block/iron_trapdoor/close3"),
                new ResourceLocation("block/iron_trapdoor/close4")};

        add(ModSoundEvents.CHILI_PEPPER_PICK_CHILI_PEPPERS, getDefinition(new ResourceLocation("mob/sheep/shear"))
                .subtitle("subtitles.item.shears.shear"));
        addSoundEventWithSubtitle(ModSoundEvents.GUN_SHOOT, new ResourceLocation("fireworks/blast1"));
        addSoundEvent(ModSoundEvents.GUN_ACTION_OPEN, actionOpenSoundLocations);
        addSoundEventWithSubtitle(ModSoundEvents.GUN_ACTION_CLOSE, actionCloseSoundLocations);
        addSoundEventWithSubtitle(ModSoundEvents.GUN_UPGRADE, new ResourceLocation("random/anvil_use"));
    }

    private void addSoundEvent(SoundEvent soundEvent, ResourceLocation... locations) {
        add(soundEvent, getDefinition(locations));
    }

    private void addSoundEventWithSubtitle(SoundEvent soundEvent, ResourceLocation... locations) {
        add(soundEvent, getDefinition(locations).subtitle(getSubtitle(soundEvent)));
    }

    private SoundDefinition getDefinition(ResourceLocation... locations) {
        SoundDefinition.Sound[] sounds = Arrays.stream(locations).map(SoundDefinitionsProvider::sound).toArray(SoundDefinition.Sound[]::new);
        return definition().with(sounds);
    }

    private String getSubtitle(SoundEvent soundEvent) {
        return "subtitles." + soundEvent.getLocation().getPath();
    }
}
