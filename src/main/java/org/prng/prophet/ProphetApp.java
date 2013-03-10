package org.prng.prophet;

import org.prng.prophet.classification.NaiveBayes;
import org.prng.prophet.classification.containers.holders.NewsStaticContainer;
import org.prng.prophet.classification.containers.holders.SpamStaticContainer;
import org.prng.prophet.classification.filters.words.FilterChain;
import org.prng.prophet.messages.ProphetTweet;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: shushumiga
 * Date: Mar 9, 2013
 * Time: 11:27:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProphetApp {

    public static FilterChain classificationFilter = new FilterChain("org.prng.prophet.classification.filters.words.SpecialCharacterFilter");
    //public static FilterChain clusteringFilter = new FilterChain("filters.clustering");

    public static void main(String[] args) {

        ArrayList<String> newsCorpus = getCorpus("/data/user_stream.json");
        ArrayList<String> spamCorpus = getCorpus("/data/spam_stream.json");

        getSubsetCorpus(newsCorpus, "data/user_stream.json", 50000);
        getSubsetCorpus(spamCorpus, "data/spam_stream.json", 50000);

        NewsStaticContainer newsContainer = NewsStaticContainer.getInstance();
        System.out.println("NEWS WORDS COUNT: " + newsContainer.getWordsCount());
        SpamStaticContainer spamContainer = SpamStaticContainer.getInstance();
        System.out.println("SPAM WORDS COUNT: " + spamContainer.getWordsCount());

        ArrayList<String> spamTest = getTestCorpus(spamCorpus, 25000);
        ArrayList<String> newsTest = getTestCorpus(newsCorpus, 25000);

        List<ArrayList<String>> containers = new ArrayList<ArrayList<String>>();
        containers.add(spamTest);
        containers.add(newsTest);
        makeTestSet(containers, "data/test.json", (spamTest.size() + newsTest.size()));
        

        int news_count = 0;
        int spam_count = 0;

        NaiveBayes nb = null;
        try {
            nb = new NaiveBayes();
            BufferedReader input =  new BufferedReader(new FileReader("data/test.json"));
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
            printError(news_count, spam_count, 25000, 25000, 50000, 50000);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
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

