package dev.sfu.demo.camel_kafka.routes;

import dev.sfu.demo.camel_kafka.model.Event;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class RestServiceRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        restConfiguration().component("servlet")
                .bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true")
        ;

        rest("/event")
                .consumes("application/json").produces("application/json")

                .post("/create")
                    .description("create event")
                    .type(Event.class)
                    .to("direct:createEvent")
                ;

        from("direct:createEvent")
                .log("create event: ${body.message}")
                .transform().constant("Created")
        ;

    }
}
