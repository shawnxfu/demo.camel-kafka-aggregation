package dev.sfu.demo.camel_kafka.aggregations;

import dev.sfu.demo.camel_kafka.model.WordCountResult;
import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WordCountAggregationStrategy implements AggregationStrategy {
    public static final String WORD_COUNT_RESULT = "WORD_COUNT_RESULT";

    @Autowired
    private WordCountResult wordCountResult;

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        if (newExchange != null) {
            countWord(newExchange);
            return newExchange;
        } else {
            countWord(oldExchange);
            return oldExchange;
        }
    }

    private void countWord(Exchange exchange) {
        String word = exchange.getIn().getBody(String.class);
        if (word != null && word.length() > 0) {
            wordCountResult.addCount(word);
        }
        exchange.setProperty(WORD_COUNT_RESULT, wordCountResult);
    }
}
