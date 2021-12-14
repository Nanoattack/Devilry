package com.nano.devilry.item.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Random;

public class Knife extends Item
{
    public Knife(Properties properties)
    {
        super(properties);
    }

    protected static final Random random = new Random();

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        ItemStack container = itemStack.copy();
        if(container.hurt(1, random, null)) {
            return ItemStack.EMPTY;
        }else {
            return container;
        }
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }
}
