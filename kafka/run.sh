bin/kafka-topics.sh --create --topic pow-topic --bootstrap-server localhost:9092
bin/kafka-topics.sh --describe --topic pow-topic --bootstrap-server localhost:9092
bin/kafka-console-producer.sh --topic pow-topic --bootstrap-server localhost:9092
bin/kafka-console-consumer.sh --topic pow-topic --from-beginning --bootstrap-server localhost:9092

bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --list
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic pow-topic --group pow-group
