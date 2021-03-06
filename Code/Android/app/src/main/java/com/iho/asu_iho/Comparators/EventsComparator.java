package com.iho.asu_iho.Comparators;

import com.iho.asu_iho.Model.Events;

import java.util.Comparator;

/**
 * Created by Mihir on 3/16/2017.
 */

public class EventsComparator implements Comparator<Events> {

    @Override
    public int compare(Events lhs, Events rhs) {
        return lhs.getDate().compareTo(rhs.getDate());
    }
}
