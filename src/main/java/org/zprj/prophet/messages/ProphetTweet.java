package org.zprj.prophet.messages;

import org.json.JSONException;
import org.json.JSONObject;
import org.zprj.prophet.classification.filters.words.FilterChain;
import org.zprj.prophet.classification.stemmer.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zorana
 * Date: Mar 9, 2013
 * Time: 11:19:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProphetTweet extends Tweet {

    private boolean isNews;
    private List<String> words;
    //private List<String> ngrams;

    public ProphetTweet(String str) {
        super(str);
    }
//    private Tweet tweet;

//    public PythiaTweet(Tweet tweet) {
//        super();
//        this.tweet = tweet;
//    }
//
//    public PythiaTweet() { }

//    public ProphetTweet getInstance(String str) {
//        try {
//            return new JSONObject(str);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public void setWords(FilterChain classification) {
        if(words == null) {
            this.words = new ArrayList<String>();
            String[] words = this.getText().toLowerCase().split("\\s+");
            Stemmer s = new Stemmer();
            for(int i = 0; i < words.length; i++) {
                if(classification.isAcceptableWord(words[i])) {
                    this.words.add(s.stemWord(words[i]));
                }
            }
        }
    }

    public List<String> getWords() {
        return words;
    }
}
