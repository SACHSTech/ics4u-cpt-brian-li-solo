package cpt;

import java.util.ArrayList;

public class sorting {

    /*
     * Merge sort takes in an array and returns the same array, sorted.
     */
    public static void mergeSort(ArrayList<data> lister, String strProperty, boolean boolValue) {

        if (lister.size() <= 1) {
            return; // Base case: list is already sorted or empty
        }

        int mid = lister.size() / 2;
        ArrayList<data> left = new ArrayList<>(lister.subList(0, mid)); // Divide the list into left sublist
        ArrayList<data> right = new ArrayList<>(lister.subList(mid, lister.size())); // Divide the list into right //
                                                                                     // sublist
        mergeSort(left, strProperty, boolValue); // Recursively sort the left sublist
        mergeSort(right, strProperty, boolValue); // Recursively sort the right sublist

        if (boolValue) {
            merge(lister, left, right, strProperty, true); // Merge the sorted sublists
        } else {
            merge(lister, left, right, strProperty, false); // Merge the sorted sublists
        }
    }

    private static void merge(ArrayList<data> result, ArrayList<data> left, ArrayList<data> right, String strProperty,
            boolean boolValue) {
        int leftIndex = 0;
        int rightIndex = 0;
        int resultIndex = 0;

        while (leftIndex < left.size() && rightIndex < right.size()) {

            if (boolValue) {

                if (getPropertyValue(left.get(leftIndex), strProperty)
                        .compareTo(getPropertyValue(right.get(rightIndex), strProperty)) <= 0) {
                    result.set(resultIndex++, left.get(leftIndex++));
                }

                else {
                    result.set(resultIndex++, right.get(rightIndex++));
                }

            }

            else {
                if (-getPropertyValue(left.get(leftIndex), strProperty)
                        .compareTo(getPropertyValue(right.get(rightIndex), strProperty)) <= 0) {
                    result.set(resultIndex++, left.get(leftIndex++));
                }

                else {
                    result.set(resultIndex++, right.get(rightIndex++));
                }
            }
        }

        while (leftIndex < left.size()) {
            result.set(resultIndex++, left.get(leftIndex++));
        }

        while (rightIndex < right.size()) {
            result.set(resultIndex++, right.get(rightIndex++));
        }
    }

    private static Comparable getPropertyValue(data dataPoint, String strProperty) {
        if (strProperty.equals("year")) {
            return dataPoint.getYear();
        }

        else if (strProperty.equals("entity")) {
            return dataPoint.getEntity();
        }

        else if (strProperty.equals("day")) {
            return dataPoint.getDay();
        }

        else if (strProperty.equals("parameter")) {
            return dataPoint.getParameter();
        }

        else if (strProperty.equals("domain")) {
            return dataPoint.getDomain();
        }

        else {
            throw new IllegalArgumentException("Invalid property: " + strProperty);
        }
    }

    public static void sortYear(boolean boolReverse, ArrayList<data> sortingList) {
        if (boolReverse) {
            mergeSort(sortingList, "year", true);
        }

        else {
            mergeSort(sortingList, "year", false);
        }
    }

    public static void sortEntity(boolean boolReverse, ArrayList<data> sortingList) {
        if (boolReverse) {
            mergeSort(sortingList, "entity", true);
        }

        else {
            mergeSort(sortingList, "entity", false);
        }
    }

    public static void sortParameter(boolean boolReverse, ArrayList<data> sortingList) {
        if (boolReverse) {
            mergeSort(sortingList, "parameter", true);
        }

        else {
            mergeSort(sortingList, "parameter", false);
        }
    }

    public static void sortDay(boolean boolReverse, ArrayList<data> sortingList) {
        if (boolReverse) {
            mergeSort(sortingList, "day", true);
        }

        else {
            mergeSort(sortingList, "day", false);
        }
    }

    public static void sortDomain(boolean boolReverse, ArrayList<data> sortingList) {
        if (boolReverse) {
            mergeSort(sortingList, "domain", true);
        }

        else {
            mergeSort(sortingList, "domain", false);
        }
    }
}