package com.github.iunius118.chilibulletweapons.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties CHILI_CHICKEN_SANDWICH = new FoodProperties.Builder().nutrition(6).saturationMod(0.8F).build();
    public static final FoodProperties CHILI_FISH_SANDWICH = new FoodProperties.Builder().nutrition(6).saturationMod(0.8F).build();
    public static final FoodProperties CHILI_MEAT_SANDWICH = new FoodProperties.Builder().nutrition(8).saturationMod(0.8F).build();
    public static final FoodProperties CHILI_POTATO_SANDWICH = new FoodProperties.Builder().nutrition(6).saturationMod(0.6F).build();
    public static final FoodProperties HALF_CHILI_CHICKEN_SANDWICH = new FoodProperties.Builder().nutrition(3).saturationMod(0.8F).build();
    public static final FoodProperties HALF_CHILI_FISH_SANDWICH = new FoodProperties.Builder().nutrition(3).saturationMod(0.8F).build();
    public static final FoodProperties HALF_CHILI_MEAT_SANDWICH = new FoodProperties.Builder().nutrition(4).saturationMod(0.8F).build();
    public static final FoodProperties HALF_CHILI_POTATO_SANDWICH = new FoodProperties.Builder().nutrition(3).saturationMod(0.6F).build();
    public static final FoodProperties FRIED_CHILI_PEPPER = new FoodProperties.Builder().nutrition(2).saturationMod(0.6F).effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600), 1.0F).alwaysEat().fast().build();
}
