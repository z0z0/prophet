package org.prng.prophet.classification.filters.words;

import org.prng.prophet.classification.filters.ProphetFilter;

/**
 * Created by IntelliJ IDEA.
 * User: milicabogicevic
 * Date: 06.01.2012.
 * Time: 16.40.58
 * To change this template use File | Settings | File Templates.
 */
public class SpecialCharacterFilter implements ProphetFilter {

    public static final String LINK     =   "http";
    public static final String HASH   =   "#";
    public static final String TAG  =   "@";

    public boolean isAcceptable(String word) {
        return !word.contains(LINK) && !word.contains(HASH) && !word.contains(TAG);
    }
}
