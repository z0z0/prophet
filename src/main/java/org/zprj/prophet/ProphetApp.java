package org.prng.prophet;

import org.prng.prophet.classification.NaiveBayes;
import org.prng.prophet.classification.containers.holders.NewsStaticContainer;
import org.prng.prophet.classification.containers.holders.SpamStaticContainer;
import org.prng.prophet.classification.filters.words.FilterChain;
import org.prng.prophet.messages.ProphetTweet;
import org.prng.prophet.util.ProphetUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: zorana
 * Date: Mar 9, 2013
 * Time: 11:27:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProphetApp {

    public static FilterChain classificationFilter = new FilterChain(ProphetUtil.CLASSIFICATION_FILTER);

    public static void main(String[] args) {

        ArrayList<String> newsCorpus = getCorpus(ProphetUtil.NEWS_PATH);
        ArrayList<String> spamCorpus = getCorpus(ProphetUtil.SPAM_PATH);

        getSubsetCorpus(newsCorpus, ProphetUtil.NEWS_TRAINING, ProphetUtil.NEWS_TRAINING_SIZE);
        getSubsetCorpus(spamCorpus, ProphetUtil.SPAM_TRAINING, ProphetUtil.SPAM_TRAINING_SIZE);

        NewsStaticContainer newsContainer = NewsStaticContainer.getInstance();
        SpamStaticContainer spamContainer = SpamStaticContainer.getInstance();


        ArrayList<String> spamTest = getTestCorpus(spamCorpus, ProphetUtil.SPAM_TEST_SIZE);
        ArrayList<String> newsTest = getTestCorpus(newsCorpus, ProphetUtil.NEWS_TEST_SIZE);

        List<ArrayList<String>> containers = new ArrayList<ArrayList<String>>();
        containers.add(spamTest);
        containers.add(newsTest);
        makeTestSet(containers, ProphetUtil.TEST_DATA_PATH, (spamTest.size() + newsTest.size()));
        

        int news_count = 0;
        int spam_count = 0;

        NaiveBayes nb = null;
        try {
            nb = new NaiveBayes();
            BufferedReader input =  new BufferedReader(new FileReader(ProphetUtil.TEST_DATA_PATH));
            String line = null;
            while((line = input.readLine()) != null) {
                ProphetTweet tweet = new ProphetTweet(line);
                tweet.setWords(classificationFilter);
                if(nb.isNews(tweet.getWords())) {
                    news_count++;
                    newsContainer.addWords(tweet);
                }
                else {
                    spam_count++;
                    spamContainer.addWords(tweet);
                }
            }
            input.close();
            printError(news_count, spam_count, ProphetUtil.NEWS_TEST_SIZE, ProphetUtil.SPAM_TEST_SIZE,
                    ProphetUtil.NEWS_TRAINING_SIZE, ProphetUtil.SPAM_TRAINING_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public static void makeTestSet(List<ArrayList<String>> containers, String path, int size) {
        BufferedWriter out = null;
        Random randomizer = new Random();
        try {
            out = new BufferedWriter(new FileWriter(path));
            for(int i = 0; i < size; i++) {
                int r = randomizer.nextInt(containers.size());
                ArrayList<String> corpus = containers.get(r);
                if(corpus.size() > 0) {
                    int rnd = randomizer.nextInt(corpus.size());
                    out.write(corpus.get(rnd) + "\n");
                    corpus.remove(rnd);
                }
                else {
                    i--;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if(out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<String> getCorpus(String path) {
        BufferedReader input = null;
        ArrayList<String> corpus = new ArrayList<String>();
        try {
            input = new BufferedReader(new FileReader(path));
            String line;
            while ((line = input.readLine()) != null) {
                corpus.add(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return corpus;
    }

    public static void getSubsetCorpus(ArrayList<String> corpus, String path, int size) {
        BufferedWriter out = null;
        Random randomizer = new Random();
        try{
            out = new BufferedWriter(new FileWriter(path));
            for(int i = 0; i < size; i++) {
                int r = randomizer.nextInt(corpus.size());
                out.write(corpus.get(r) + "\n");
                corpus.remove(r);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if(out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<String> getTestCorpus(ArrayList<String> corpus, int size) {
        ArrayList<String> corp = new ArrayList<String>();
        Random randomizer = new Random();
        for(int i = 0; i < size; i++) {
            int r = randomizer.nextInt(corpus.size());
            corp.add(corpus.get(r));
        }
        return corp;
    }

    public static void printError(int newsCount, int spamCount, int newsReal, int spamReal, int newsTraining, int spamTraining) {
        BufferedWriter out = null;
        try{
            out = new BufferedWriter(new FileWriter("data/error_rate", true));
            if(newsCount > newsReal) {
                double error = newsCount - newsReal;
                double errorPercentage = (error * 100.0)/Double.parseDouble(String.valueOf(newsReal));
                out.append(newsTraining + "\t" + newsReal + "\t" + errorPercentage + "\n");
                System.out.println("There is " + errorPercentage + "% of false positive");
            }
            if(spamCount > spamReal) {
                double error = spamCount - spamReal;
                double errorPercentage = (error * 100.0)/Double.parseDouble(String.valueOf(spamReal));
                out.append(spamTraining + "\t" + spamReal + "\t-" + errorPercentage + "\n");
                System.out.println("There is " + errorPercentage + "% of false negative");
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if(out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

