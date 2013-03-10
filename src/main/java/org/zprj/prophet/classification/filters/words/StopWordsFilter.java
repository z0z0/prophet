package org.zprj.prophet.classification.filters.words;

import org.apache.commons.io.FileUtils;
import org.zprj.prophet.classification.filters.ProphetFilter;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: zorana
 * Date: 27.11.2011.
 * Time: 13.41.58
 * To change this template use File | Settings | File Templates.
 */
public class StopWordsFilter implements ProphetFilter {

    private Set<String> stop_words;

    public StopWordsFilter() {
        if(stop_words == null) {
            stop_words = new HashSet<String>();
            try {
                String[] stopWords = FileUtils.readFileToString(new File("data/stop_words")).split(",\\s+");
                for(String word : stopWords) {
                    stop_words.add(word);    
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isAcceptable(String word) {
        return !stop_words.contains(word);
    }
            
}
