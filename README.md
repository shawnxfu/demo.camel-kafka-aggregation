This project uses Apache Camel to read data from Kafka topic
and aggregates (counts) the number of words.

### Setup:
* docker pull apache/kafka:3.8.0
* docker run -d -p 9092:9092 apache/kafka:3.8.0
* docker exec -it [container-id] bash
* /opt/kafka/bin/kafka-topics.sh --create --topic incoming-event --bootstrap-server localhost:9092
* /opt/kafka/bin/kafka-topics.sh --create --topic outcoming-event --bootstrap-server localhost:9092

### Run the demo project:
./mvnw spring-boot:run

### Test:
/opt/kafka/bin/kafka-console-producer.sh --topic incoming-event --bootstrap-server localhost:9092
```shell
>hello world
>hello there
>world wide hello
```

### Results:

```text
2024-10-07T08:28:02.293-06:00  INFO 7864 --- [incoming-event]] d.s.d.c.r.KafkaWordCountRouteBuilder     : print: hello
2024-10-07T08:28:02.297-06:00  INFO 7864 --- [incoming-event]] d.s.d.c.r.KafkaWordCountRouteBuilder     : print: world
2024-10-07T08:28:02.335-06:00  INFO 7864 --- [utcoming-event]] d.s.d.c.r.KafkaWordCountRouteBuilder     : out topic: {world=1, hello=1}
2024-10-07T08:28:09.345-06:00  INFO 7864 --- [incoming-event]] d.s.d.c.r.KafkaWordCountRouteBuilder     : print: hello
2024-10-07T08:28:09.346-06:00  INFO 7864 --- [incoming-event]] d.s.d.c.r.KafkaWordCountRouteBuilder     : print: there
2024-10-07T08:28:09.350-06:00  INFO 7864 --- [utcoming-event]] d.s.d.c.r.KafkaWordCountRouteBuilder     : out topic: {world=1, there=1, hello=2}
2024-10-07T08:28:16.343-06:00  INFO 7864 --- [incoming-event]] d.s.d.c.r.KafkaWordCountRouteBuilder     : print: world
2024-10-07T08:28:16.345-06:00  INFO 7864 --- [incoming-event]] d.s.d.c.r.KafkaWordCountRouteBuilder     : print: wide
2024-10-07T08:28:16.345-06:00  INFO 7864 --- [incoming-event]] d.s.d.c.r.KafkaWordCountRouteBuilder     : print: hello
2024-10-07T08:28:16.349-06:00  INFO 7864 --- [utcoming-event]] d.s.d.c.r.KafkaWordCountRouteBuilder     : out topic: {world=2, wide=1, there=1, hello=3}
```


