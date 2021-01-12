package com.github.dskprt.bitgeon.tile.entity.inventory;

import com.github.dskprt.bitgeon.item.Item;
import com.github.dskprt.bitgeon.tile.entity.Entity;

import java.util.HashMap;
import java.util.Map;

public class Inventory {

    public Entity parent;
    public Map<Integer, Item> items;

    public Inventory(Entity parent, Map<Integer, Item> items) {
        this.parent = parent;
        this.items = items;
    }

    public static Inventory empty(Entity parent) {
        return new Inventory(parent, new HashMap<>());
    }

    public class Slot {

        public static final int MAIN = 0;
        public static final int SECONDARY = 1;
        public static final int ARMOR_HEAD = 2;
        public static final int ARMOR_CHEST = 3;
        public static final int ARMOR_LEGS = 4;
        public static final int ARMOR_FEET = 5;
    }
}
