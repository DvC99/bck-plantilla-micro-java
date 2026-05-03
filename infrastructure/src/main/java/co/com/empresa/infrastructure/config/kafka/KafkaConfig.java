package co.com.empresa.infrastructure.config.kafka;import co.com.empresa.domain.common.events.EventEnvelope;import com.fasterxml.jackson.databind.ObjectMapper;import lombok.extern.slf4j.Slf4j;import org.apache.kafka.clients.consumer.ConsumerConfig;import org.apache.kafka.clients.producer.ProducerConfig;import org.apache.kafka.common.serialization.StringDeserializer;import org.apache.kafka.common.serialization.StringSerializer;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;import org.springframework.boot.kafka.autoconfigure.KafkaProperties;import org.springframework.context.annotation.Bean;import org.springframework.context.annotation.Configuration;import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;import org.springframework.kafka.core.*;import org.springframework.kafka.listener.DefaultErrorHandler;import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;import org.springframework.kafka.support.serializer.JacksonJsonSerializer;import org.springframework.kafka.transaction.KafkaTransactionManager;import org.springframework.util.backoff.FixedBackOff;import java.util.HashMap;import java.util.Map;/**
 * Configuration class for Apache Kafka.
 * <p>
 * This class defines the necessary beans for Kafka producers and consumers,
 * including serialization/deserialization settings, transaction management,
 * and listener container factories.
 */
@Slf4j
@Configuration
public class KafkaConfig {
    private final KafkaProperties kafkaProperties;    @Autowired    public KafkaConfig(KafkaProperties kafkaProperties) {        this.kafkaProperties = kafkaProperties;    }        /**
     * Configures and returns a {@link ProducerFactory} for Kafka messages.
     * <p>
     * Sets the key serializer to {@link StringSerializer} and the value serializer
     * to {@link JacksonJsonSerializer}. Also configures the transaction ID prefix if provided.
     *
     * @return a configured {@link ProducerFactory} instance
     */
    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> configProps = kafkaProperties.buildProducerProperties();        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JacksonJsonSerializer.class);        DefaultKafkaProducerFactory<String, Object> factory = new DefaultKafkaProducerFactory<>(configProps);        String txPrefix = kafkaProperties.getProducer().getTransactionIdPrefix();        log.info("KAFKA_DEBUG: Transaction ID Prefix = : {}", txPrefix);        if (txPrefix != null) {            factory.setTransactionIdPrefix(txPrefix);        }        return factory;    }        /**
     * Configures and returns a {@link KafkaTemplate} for sending Kafka messages.
     *
     * @return a configured {@link KafkaTemplate} instance
     */
    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());    }        /**
     * Configures and returns a {@link ConsumerFactory} for Kafka messages.
     * <p>
     * Sets the key deserializer to {@link StringDeserializer} and the value deserializer
     * to {@link ErrorHandlingDeserializer} wrapping a {@link JacksonJsonDeserializer}.
     *
     * @return a configured {@link ConsumerFactory} instance
     */
    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> configProps = kafkaProperties.buildConsumerProperties();        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);        configProps.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JacksonJsonDeserializer.class.getName());        configProps.put("spring.json.trusted.packages", "*");        return new DefaultKafkaConsumerFactory<>(configProps);    }        /**
     * Configures and returns a {@link ConcurrentKafkaListenerContainerFactory} for generic Kafka messages.
     *
     * @return a configured {@link ConcurrentKafkaListenerContainerFactory} instance
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();        factory.setConsumerFactory(consumerFactory());        factory.setCommonErrorHandler(kafkaErrorHandler());        return factory;    }        /**
     * Configures and returns a {@link DefaultErrorHandler} for Kafka consumers.
     * <p>
     * Uses a {@link FixedBackOff} with a 1000ms interval and 2 retries.
     *
     * @return a configured {@link DefaultErrorHandler} instance
     */
    @Bean
    public DefaultErrorHandler kafkaErrorHandler() {
        FixedBackOff backOff = new FixedBackOff(1000L, 2L);        return new DefaultErrorHandler(backOff);    }        /**
     * Configures and returns a {@link KafkaTransactionManager} for managing Kafka transactions.
     *
     * @param producerFactory the producer factory to be used
     * @return a configured {@link KafkaTransactionManager} instance
     */
    @Bean
    @ConditionalOnProperty(name = "spring.kafka.producer.transaction-id-prefix")
    public KafkaTransactionManager<String, Object> kafkaTransactionManager(ProducerFactory<String, Object> producerFactory) {
        return new KafkaTransactionManager<>(producerFactory);    }        /**
     * Configures and returns a {@link ConsumerFactory} specifically for {@link EventEnvelope} messages.
     * <p>
     * Configures trusted packages for the JSON deserializer to include domain event envelopes.
     *
     * @param objectMapper the Jackson object mapper to be used for deserialization
     * @return a configured {@link ConsumerFactory} instance for event envelopes
     */
    @Bean
    public ConsumerFactory<String, EventEnvelope<?>> eventEnvelopeConsumerFactory(ObjectMapper objectMapper) {
        Map<String, Object> configProps = new HashMap<>(kafkaProperties.buildConsumerProperties());        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);        configProps.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JacksonJsonDeserializer.class.getName());        configProps.put("spring.json.trusted.packages", "co.com.empresa.domain.common.events");        return new DefaultKafkaConsumerFactory<>(                configProps,                new StringDeserializer(),                new JacksonJsonDeserializer<>(EventEnvelope.class)        );    }        /**
     * Configures and returns a {@link ConcurrentKafkaListenerContainerFactory} specifically for {@link EventEnvelope} messages.
     *
     * @param eventEnvelopeConsumerFactory the consumer factory for event envelopes
     * @return a configured {@link ConcurrentKafkaListenerContainerFactory} instance for event envelopes
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, EventEnvelope<?>> eventEnvelopeKafkaListenerContainerFactory(
            ConsumerFactory<String, EventEnvelope<?>> eventEnvelopeConsumerFactory) {        ConcurrentKafkaListenerContainerFactory<String, EventEnvelope<?>> factory = new ConcurrentKafkaListenerContainerFactory<>();        factory.setConsumerFactory(eventEnvelopeConsumerFactory);        factory.setCommonErrorHandler(kafkaErrorHandler());        return factory;    }}