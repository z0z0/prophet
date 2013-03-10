package org.zprj.prophet.classification.containers.holders;

import org.zprj.prophet.classification.containers.holders.StaticCorpusContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: zorana
 * Date: 04.01.2012.
 * Time: 21.36.56
 * To change this template use File | Settings | File Templates.
 */
public class BritishWordsContainer extends StaticCorpusContainer {
    private Set<String> container;
    private static final String PATH    =   "data/brit-a-z.txt";
    private static final int CAPACITY   =   100000;
    private static Logger logger = LoggerFactory.getLogger(BritishWordsContainer.class);

    //private static Config conf = Config.getInstance();

    private BritishWordsContainer() {
        if(container == null) {
            logger.info("Loading british words container...");
            lazyInit();
        }
    }

    private static class BritishWordsContainerHolder {
        private final static BritishWordsContainer INSTANCE = new BritishWordsContainer();
    }

    public static BritishWordsContainer getInstance() {
        return BritishWordsContainerHolder.INSTANCE;
    }

    private Set<String> init(String path) {
        BufferedReader input = null;
        Set<String> container = new HashSet<String>(CAPACITY);
        try {
            input = new BufferedReader(new FileReader(path));

            String line;
            while ((line = input.readLine()) != null) {
                container.add(line);
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
        return container;
    }

    public void lazyInit() {
        container = init(PATH);
    }
}
