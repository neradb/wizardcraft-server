package com.wplatform.wizardcraft.storage;

import com.wplatform.wizardcraft.domain.Item;
import com.wplatform.wizardcraft.domain.ItemStorage;

import java.util.List;

public interface Storage {


    ItemStorage getItemStorage();

    List<Item> getItems();

    List<Item> getEquippedItems();

    byte[] getFreeSlots();

    boolean addItem(byte slot, Item item);


    boolean addItem(Item item);


    boolean addMoney(int value);

    int checkInvSpace(Item item);


    boolean tryTakeAll(Storage anotherStorage);

    Item getItem(byte inventorySlot);


    void removeItem(Item item);


    void clear();








}
