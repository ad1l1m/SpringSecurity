package com.alibou.security.learnPart;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        // Create a list of LocalDateTime objects
        List<LocalDateTime> dateTimeList = new ArrayList<>();
        dateTimeList.add(LocalDateTime.of(2022, 4, 15, 10, 30)); // Add LocalDateTime objects as needed
        dateTimeList.add(LocalDateTime.of(2022, 4, 15, 9, 0));
        dateTimeList.add(LocalDateTime.of(2022, 4, 15, 11, 45));
        dateTimeList.add(LocalDateTime.of(2022, 4, 15, 8, 15));
        dateTimeList.add(LocalDateTime.of(2022, 4, 15, 8, 14));
        dateTimeList.add(LocalDateTime.of(2022, 4, 15, 9, 12));

        // Print the original list
        System.out.println("Original list: " + dateTimeList);

        // Sort the list based on hour and minute components
        dateTimeList.sort(new HourMinuteComparator());
        // Print the sorted list
        System.out.println("Sorted list: " + dateTimeList);
    }

    // Custom Comparator for sorting based on hour and minute components
    static class HourMinuteComparator implements Comparator<LocalDateTime> {
        @Override
        public int compare(LocalDateTime o1, LocalDateTime o2) {
            // Compare based on hour and minute components
            int hour1 = o1.getHour();
            int minute1 = o1.getMinute();
            int hour2 = o2.getHour();
            int minute2 = o2.getMinute();
            if (hour1 == hour2) {
                return Integer.compare(minute1, minute2);
            } else {
                return Integer.compare(hour1, hour2);
            }
        }
    }

}
