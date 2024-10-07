package dev.sfu.demo.camel_kafka;

import dev.sfu.demo.camel_kafka.model.WordCountResult;
import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.apache.camel.builder.AdviceWith.adviceWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@CamelSpringBootTest
@SpringBootTest
class ApplicationTests {

	@Autowired
	CamelContext camelContext;

	@Autowired
	ProducerTemplate producerTemplate;

	@EndpointInject("mock:output")
	MockEndpoint mockOutput;

	@Test
	void shouldCountWordsCorrectly() throws Exception {
		adviceWith(camelContext, "in-route", builder -> {
			builder.replaceFromWith("direct:start-in");
			builder.weaveByToUri("kafka:*").replace().to("mock:output");
		});
		adviceWith(camelContext, "out-route", builder -> {
			builder.replaceFromWith("direct:start-out");
		});
		camelContext.start();
		try {
			mockOutput.expectedMessageCount(1);
			mockOutput.whenExchangeReceived(1, exchange -> {
				WordCountResult result = exchange.getIn().getBody(WordCountResult.class);
				assertNotNull(result);
				assertEquals(2, result.getCount("hello"));
				assertEquals(1, result.getCount("world"));
			});

			producerTemplate.send("direct:start-in", exchange -> {
				exchange.getIn().setBody("hello hello world");
			});

			mockOutput.assertIsSatisfied();
		} finally {
			camelContext.stop();
		}
	}

}
