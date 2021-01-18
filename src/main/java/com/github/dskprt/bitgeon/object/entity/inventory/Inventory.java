package com.github.dskprt.bitgeon.object.entity.inventory;

import com.github.dskprt.bitgeon.item.Item;
import com.github.dskprt.bitgeon.object.entity.EntityObject;

import java.util.HashMap;
import java.util.Map;

public class Inventory {

    public EntityObject parent;
    public Map<Integer, Item> items;

    public int selectedSlot;

    public Inventory(EntityObject parent, Map<Integer, Item> items) {
        this(parent, items, 0);
    }

    public Inventory(EntityObject parent, Map<Integer, Item> items, int selectedSlot) {
        this.parent = parent;
        this.items = items;
        this.selectedSlot = selectedSlot;
    }

    public Item getSelectedItem() {
        return items.get(selectedSlot);
    }

    public static Inventory empty(EntityObject parent) {
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
