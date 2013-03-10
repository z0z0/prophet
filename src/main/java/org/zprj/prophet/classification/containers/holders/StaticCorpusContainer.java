package org.prng.prophet.classification.containers.holders;

import org.apache.commons.lang.mutable.MutableDouble;
import org.json.JSONObject;
import org.prng.prophet.ProphetApp;
import org.prng.prophet.classification.filters.words.FilterChain;
import org.prng.prophet.messages.ProphetTweet;
import org.prng.prophet.classification.containers.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by IntelliJ IDEA.
 * User: milicabogicevic
 * Date: 27.11.2011.
 * Time: 12.28.24
 * Load initial static corpus into map
 */
public abstract class StaticCorpusContainer implements DataLoader {

    private Map<String, MutableDouble> container = new HashMap<String, MutableDouble>();
    public double wordCount = 0;
    public static FilterChain classificationFilter = new FilterChain("org.prng.prophet.classification.filters.words.SpecialCharacterFilter");
    //public static FilterChain clusteringFilter = new FilterChain("org.prng.pythia.classification.filters.words.StopWordsFilter");
    private static Logger logger = LoggerFactory.getLogger(StaticCorpusContainer.class);



    public void load(String path) {
        BufferedReader input = null;
        File f = new File(path);
        if(f.exists()) {
            try {
                input = new BufferedReader(new FileReader(path));
                String line;
                while ((line = input.readLine()) != null) {
                    ProphetTweet tweet = new ProphetTweet(line);
                    tweet.setWords(classificationFilter);
                    addWords(tweet);
                }
            }
            catch (IOException e) {
                logger.error("Can not read input file", e);
            } finally {
                try {
                    if (input != null) {
                        input.close();
                    }
                } catch (IOException e) {
                    logger.error("Failed to close file: " + path);
                }
            }
        }
    }

    public void addWords(ProphetTweet tweet) {
        List<String> words = tweet.getWords();
        for(String word : words) {
            addWord(word);
        }
    }

    public void addWord(String word) {
        if(!container.containsKey(word)) {
            container.put(word, new MutableDouble());
        }
        container.get(word).increment();
        wordCount++;
    }

    public Map<String, MutableDouble> getContainer() {
        return container;
    }

    public double getWordsNumber() {
        double count = 0;
        for(Map.Entry<String, MutableDouble> entry : getContainer().entrySet()) {
            count += entry.getValue().doubleValue();
        }
        return count;
    }

    public double getWordsCount() {
        return wordCount;
    }

    public abstract void lazyInit();

}



