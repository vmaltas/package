package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Packer {


    private static final String ITEM_PATTERN = "\\(([^)]*)\\)";

    private static final int maxWeightLimit = 100;

    private static final int maxMaximumWeightLimit = 100;

    private static final int maxItemLimit = 15;

    private static final String filePath = "C:\\Users\\maltas\\Desktop\\INTER\\CV\\hollanda\\2_mobiquity\\inputText1.txt";


    public static void main(String[] args) {

        try {
            System.out.println(pack(filePath));
        } catch (APIException e) {
            e.printStackTrace();
        }
    }

    public static String pack(String filePath) throws APIException {
        String bestPackage = "";
        List<PackerLine> packerLines = new ArrayList<PackerLine>();
        try {
            FileInputStream fstream = new FileInputStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            if (fstream == null) {
                throw new APIException("Problem occured while reading file");
            } else {
                String thisLine;
                while ((thisLine = br.readLine()) != null) {
                    PackerLine packerLine = new PackerLine();
                    int currentMaxWeight = Integer.parseInt(thisLine.split(":")[0].trim());
                    if (currentMaxWeight > maxMaximumWeightLimit) {
                        throw new APIException("A line maximum weight limit exceeds has more than maximum weight limit" + maxMaximumWeightLimit);
                    }
                    packerLine.setMaxWeight(currentMaxWeight);
                    List<PackerItem> packerItemList = new ArrayList<PackerItem>();
                    Pattern p = Pattern.compile(ITEM_PATTERN);
                    Matcher m = p.matcher(thisLine);
                    int itemCount = 0;
                    while (m.find()) {
                        itemCount = itemCount + 1;
                        if (itemCount > maxItemLimit) {
                            throw new APIException("A line exceeds has more than maximum Item limit" + maxItemLimit);
                        }
                        PackerItem packerItem = new PackerItem();
                        String[] tokens = m.group(1).split(",");
                        packerItem.setItemNumber(Integer.parseInt(tokens[0]));
                        Double currentWeight = Double.parseDouble(tokens[1]);
                        if (currentWeight > maxWeightLimit) {
                            throw new APIException("An item exceeds has more than weight limit" + maxWeightLimit);
                        }
                        packerItem.setWeight(currentWeight);
                        packerItem.setCost(Integer.parseInt(tokens[2].substring(1)));
                        if (currentMaxWeight >= currentWeight) {
                            packerItemList.add(packerItem);
                        }
                    }
                    packerLine.setItemList(packerItemList);
                    packerLines.add(packerLine);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new APIException("File Problem persists");
        }

        for (PackerLine packerLine : packerLines) {

            List<PackerItem> optimalList = findOptimalPackage(packerLine);
            if (optimalList.size() != 0) {
                for (PackerItem packerItem : optimalList) {
                    bestPackage = bestPackage + packerItem.getItemNumber() + ",";
                }
            } else {
                bestPackage = bestPackage + "-,";
            }
            bestPackage = bestPackage.substring(0, bestPackage.length() - 1) + "\n";
        }

        return bestPackage;
    }

    public static List<PackerItem> findOptimalPackage(PackerLine packerLine) {

        List<List<PackerItem>> candidatesList = new ArrayList<List<PackerItem>>();
        List<PackerItem> optimumList = new ArrayList<PackerItem>();
        double optimumWeight = packerLine.getMaxWeight();
        int optimumCost = 0;

        for (PackerItem candidateItem : packerLine.getItemList()) {
            int candidatesSize = candidatesList.size();
            for (int i = 0; i < candidatesSize; i++) {
                List<PackerItem> candidate = candidatesList.get(i);
                List<PackerItem> currentCandidate = new ArrayList<PackerItem>(candidate);
                currentCandidate.add(candidateItem);
                double candidateWeight = getTotalWeight(currentCandidate);
                if (candidateWeight < packerLine.getMaxWeight()) {
                    int candidateCost = getTotalCost(currentCandidate);
                    if ((candidateCost > optimumCost) || (candidateCost == optimumCost && candidateWeight < optimumWeight)) {
                        optimumList = currentCandidate;
                        optimumCost = candidateCost;
                        optimumWeight = candidateWeight;
                    }
                }
                candidatesList.add(currentCandidate);
            }
            List<PackerItem> current = new ArrayList<PackerItem>();
            current.add(candidateItem);
            double candidateWeight = getTotalWeight(current);
            if (candidateWeight < packerLine.getMaxWeight()) {
                int candidateCost = getTotalCost(current);
                if ((candidateCost > optimumCost) || (candidateCost == optimumCost && candidateWeight < optimumWeight)) {
                    optimumList = current;
                    optimumCost = candidateCost;
                    optimumWeight = candidateWeight;
                }
            }
            candidatesList.add(current);
        }

        return optimumList;

    }

    public static double getTotalWeight(List<PackerItem> packerItems) {
        double totalWeight = 0;
        for (PackerItem packerItem : packerItems) {
            totalWeight = totalWeight + packerItem.getWeight();
        }
        return totalWeight;
    }

    public static int getTotalCost(List<PackerItem> packerItems) {
        int totalCost = 0;
        for (PackerItem packerItem : packerItems) {
            totalCost = totalCost + packerItem.getCost();
        }
        return totalCost;
    }


}
