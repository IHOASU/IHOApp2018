package com.iho.asu_iho.Comparators;

import com.iho.asu_iho.Model.Science;

import java.util.Comparator;

/**
 * Created by Mihir on 3/16/2017.
 */

public class ScienceComparator implements Comparator<Science> {

    @Override
    public int compare(Science lhs, Science rhs) {
        return rhs.getTimestamp().compareTo(lhs.getTimestamp());
    }
}
