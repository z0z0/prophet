package org.zprj.prophet.classification;

import org.zprj.prophet.util.ProphetUtil;
import org.zprj.prophet.classification.containers.holders.StaticContainerFactory;
import org.zprj.prophet.classification.containers.holders.StaticCorpusContainer;
import org.zprj.prophet.util.ProphetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zorana
 * Date: 20.12.2011.
 * Time: 19.42.34
 * Document classification with spam and news static holders.
 *
 * d = log\frac{P(J|w_1,w_2...w_k)}{P(N|w_1,w_2...w_k)} =
 * = log(\frac{P(J)}{P(N)}) + \sum_{i=1}^{n}\frac{p(w_i|J)}{p(w_i|N)}
 * P(J) - fraction of the total number of words in tweets marked as junk in corpus
 * to the total number of words
 * P(N) - fraction of the total number of words in tweets marked as news in corpus
 * to the total number of words
 * p(w_i|J) - the ratio of the number of times word w_i appears in tweets marked as junk in the corpus
 * to the total number of times it appears in corpus
 * p(w_i|N) - the ratio of the number of times word w_i appears in tweets marked as news in the corpus
 * to the total number of times it appears in corpus
 *
 * If d < 0 => tweet is news
 */
public class NaiveBayes {

    private StaticCorpusContainer spam;
    private StaticCorpusContainer news;
    private static Logger logger = LoggerFactory.getLogger(NaiveBayes.class);

    public NaiveBayes() throws IOException {
        logger.info("Initializing Naive Bayes...");
        spam = StaticContainerFactory.getContainer(ProphetUtil.SPAM);
        news = StaticContainerFactory.getContainer(ProphetUtil.NEWS);
    }

    public boolean isNews(List<String> tweetWords) {
        double spamCount = spam.getWordsCount();
        double newsCount = news.getWordsCount();
        return (Math.log(spamProbability(newsCount, spamCount)/newsProbability(newsCount, spamCount)) + getSum(tweetWords)) < 0;
    }

    public double spamProbability(double newsCount, double spamCount) {
        return spamCount / (spamCount + newsCount);
    }

    public double newsProbability(double newsCount, double spamCount) {
        return newsCount / (spamCount + newsCount);
    }

    public double wordSpamRatio(String word) {
        if(spam.getContainer().containsKey(word) && news.getContainer().containsKey(word)) {
            return spam.getContainer().get(word).doubleValue() / (spam.getContainer().get(word).doubleValue() + news.getContainer().get(word).doubleValue());
        }
        return 1.0;
    }

    public double wordNewsRatio(String word) {
        if(spam.getContainer().containsKey(word) && news.getContainer().containsKey(word)) {
            return news.getContainer().get(word).doubleValue() / (spam.getContainer().get(word).doubleValue() + news.getContainer().get(word).doubleValue());
        }
        return 1.0;
    }

    public double getSum(List<String> tweetWords) {
        double sum = 0;
        for(String word : tweetWords) {
            sum += Math.log(wordSpamRatio(word)/wordNewsRatio(word));
        }
        return sum;
    }
}
