package org.zprj.prophet.classification.containers.holders;

import org.zprj.prophet.util.ProphetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by IntelliJ IDEA.
 * User: zorana
 * Date: 27.11.2011.
 * Time: 13.56.02
 * Load news data as static corpus
 */
public class NewsStaticContainer extends StaticCorpusContainer {

    private static final String PATH    = ProphetUtil.NEWS_TRAINING;
    private static Logger logger = LoggerFactory.getLogger(NewsStaticContainer.class);

    protected NewsStaticContainer() {
        logger.info("Loading news container...");
        lazyInit();
    }

    public void lazyInit() {
        if(getContainer().isEmpty()) {
            load(PATH);
        }
        if(wordCount == 0) {
            wordCount = getWordsNumber();
        }
    }

    private static class NewsStaticContainerHolder {
        private final static NewsStaticContainer INSTANCE = new NewsStaticContainer();
    }

    public static NewsStaticContainer getInstance() {
        return NewsStaticContainerHolder.INSTANCE;
    }
}
