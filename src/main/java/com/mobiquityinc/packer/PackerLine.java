package com.mobiquityinc.packer;

import java.util.HashMap;
import java.util.List;

public class PackerLine {


    private int maxWeight;

    private List<PackerItem> itemList;

    public int getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public List<PackerItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<PackerItem> itemList) {
        this.itemList = itemList;
    }
}
