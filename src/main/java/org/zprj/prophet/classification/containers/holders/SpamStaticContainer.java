package org.prng.prophet.classification.containers.holders;

import org.apache.commons.lang.mutable.MutableDouble;
import org.prng.prophet.util.ProphetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


/**
 * Created by IntelliJ IDEA.
 * User: milicabogicevic
 * Date: 27.11.2011.
 * Time: 13.56.16
 * Load spam data as static corpus
 */
public class SpamStaticContainer extends StaticCorpusContainer {

    private static final String PATH    = ProphetUtil.SPAM_TRAINING;
    private static Logger logger = LoggerFactory.getLogger(SpamStaticContainer.class);

    private SpamStaticContainer() {
        logger.info("Loading spam container...");
        lazyInit();
    }

    public void lazyInit() {
        if(getContainer().isEmpty()) {
            load(PATH);
        }
        if(getWordsCount() == 0) {
            wordCount = getWordsNumber();
        }
    }

    private static class SpamStaticContainerHolder {
        private final static SpamStaticContainer INSTANCE = new SpamStaticContainer();
    }

    public static SpamStaticContainer getInstance() {
        return SpamStaticContainerHolder.INSTANCE;
    }
}
