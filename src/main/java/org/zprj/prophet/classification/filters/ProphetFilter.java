package org.zprj.prophet.classification.filters;

/**
 * Created by IntelliJ IDEA.
 * User: zorana
 * Date: 06.01.2012.
 * Time: 17.08.53
 * To change this template use File | Settings | File Templates.
 */
public interface ProphetFilter {

    public boolean isAcceptable(String word);
}
