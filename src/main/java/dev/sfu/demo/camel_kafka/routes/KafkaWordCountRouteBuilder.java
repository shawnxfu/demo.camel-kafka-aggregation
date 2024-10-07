package dev.sfu.demo.camel_kafka.routes;

import dev.sfu.demo.camel_kafka.aggregations.WordCountAggregationStrategy;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static dev.sfu.demo.camel_kafka.aggregations.WordCountAggregationStrategy.WORD_COUNT_RESULT;

@Component
public class KafkaWordCountRouteBuilder extends RouteBuilder {
    final static Logger LOGGER = LoggerFactory.getLogger(KafkaWordCountRouteBuilder.class);

    @Autowired
    private WordCountAggregationStrategy wordCountAggregationStrategy;

    @Override
    public void configure() throws Exception {
        from("kafka:incoming-event?brokers=localhost:9092")
                .routeId("in-route")
                .to("micrometer:counter:in.msg.counter")
                .split(body().tokenize(" "), wordCountAggregationStrategy).streaming()
                    .process(this::printBody)
                .end()
                .setBody(exchangeProperty(WORD_COUNT_RESULT))
                .to("kafka:outcoming-event?brokers=localhost:9092")
            ;

        from("kafka:outcoming-event?brokers=localhost:9092")
                .routeId("out-route")
                .to("micrometer:counter:out.msg.counter")
                .process(e -> {
                    LOGGER.info("out topic: {}", e.getIn().getBody());
                })
            ;
    }

    private void printBody(Exchange exchange) {
        String message = exchange.getIn().getBody(String.class);
        LOGGER.info("print: {}", message);

//        exchange.getMessage().getHeaders().forEach(this::print);
//
//        exchange.getIn().setBody(Arrays.asList(message.split(" ")));
    }

    private void print(String k, Object v) {
        LOGGER.info(k + " => " + v);
    }
}
