package org.zprj.prophet.util;

/**
 * Created by IntelliJ IDEA.
 * User: zorana
 * Date: Mar 9, 2013
 * Time: 11:15:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProphetUtil {

    public static final String NEWS =   "news".intern();
    public static final String SPAM =   "spam".intern();
    public static final int DATA_SIZE = 50000;
    public static final String BRITISH  =   "british".intern();
    public static final String SPAM_PATH = "data/spam_stream50000.json".intern();
    public static final String NEWS_PATH =  "data/news_stream50000.json".intern();
    public static final String SPAM_TRAINING = "data/spam_training50000.json".intern();
    public static final String NEWS_TRAINING = "data/news_training50000.json".intern();
    public static final int SPAM_TRAINING_SIZE = 48000;
    public static final int NEWS_TRAINING_SIZE = 48000;
    public static final int SPAM_TEST_SIZE = DATA_SIZE - SPAM_TRAINING_SIZE;
    public static final int NEWS_TEST_SIZE = DATA_SIZE - NEWS_TRAINING_SIZE;
    public static final String TEST_DATA_PATH = "data/test.json".intern();
    public static final String CLASSIFICATION_FILTER = "org.zprj.prophet.classification.filters.words.SpecialCharacterFilter".intern();
}
