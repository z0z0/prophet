package org.zprj.prophet.classification.filters.words;

import org.zprj.prophet.ProphetApp;
import org.zprj.prophet.classification.filters.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * User: zorana
 * Date: 06.01.2012.
 */
public class FilterChain {

    private static Logger logger = LoggerFactory.getLogger(FilterChain.class);
    private String filtersName;
    private List<ProphetFilter> filters = new LinkedList<ProphetFilter>();


    public FilterChain(String filtersPath) {
        filtersName = filtersPath;
        setFiltersList();
    }

    public boolean isAcceptableWord(String word) {
        for(ProphetFilter filter : filters) {
            if(!filter.isAcceptable(word)) {
                return false;
            }
        }
        return true;
    }

    public void setFiltersList() {
        if(filters.isEmpty()) {
            String[] filtersName = this.filtersName.split(",");
            for(String filter : filtersName) {
                try {
                    logger.info("Adding filter " + filter + " to chain list...");
                    filters.add((ProphetFilter) Class.forName(filter).newInstance());
                } catch (Exception e) {
                    logger.error("Couldn't instantiate filter " + filter);
                }
            }
        }
    }

    public void addFilter(ProphetFilter filter) {
        filters.add(filter);
    }
}
