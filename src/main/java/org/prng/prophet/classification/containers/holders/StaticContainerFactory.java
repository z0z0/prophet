package org.prng.prophet.classification.containers.holders;

import org.prng.prophet.classification.containers.holders.BritishWordsContainer;
import org.prng.prophet.classification.containers.holders.NewsStaticContainer;
import org.prng.prophet.classification.containers.holders.SpamStaticContainer;
import org.prng.prophet.classification.containers.holders.StaticCorpusContainer;
import org.prng.prophet.util.ProphetUtil;

/**
 * Created by IntelliJ IDEA.
 * User: antomripmuk
 * Date: 2/20/12
 * Time: 12:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class StaticContainerFactory {

    public static StaticCorpusContainer getContainer(String path) {
        if(ProphetUtil.NEWS.equals(path)) {
            return NewsStaticContainer.getInstance();
        }
        else if (ProphetUtil.SPAM.equals(path)) {
            return SpamStaticContainer.getInstance();
        }
        else {
            return BritishWordsContainer.getInstance();
        }
    }
}
