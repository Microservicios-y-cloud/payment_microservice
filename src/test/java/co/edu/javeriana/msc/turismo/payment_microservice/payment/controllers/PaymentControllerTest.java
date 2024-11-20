package co.edu.javeriana.msc.turismo.payment_microservice.payment.controllers;

import co.edu.javeriana.msc.turismo.payment_microservice.payment.dto.UserBalanceRequest;
import co.edu.javeriana.msc.turismo.payment_microservice.payment.dto.UserBalanceResponse;
import co.edu.javeriana.msc.turismo.payment_microservice.payment.services.PaymentService;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@Testcontainers
@TestMethodOrder(OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PaymentControllerTest {
    @Autowired
    private PaymentController paymentController;

    @Autowired
    private PaymentService paymentService;

    @Container
    static final MongoDBContainer mongo = new MongoDBContainer("mongo:4.4.6");

    @Container
    static final KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.6.1"));

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.host", mongo::getHost);
        registry.add("spring.data.mongodb.port", mongo::getFirstMappedPort);
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    }

    static KafkaConsumer<Object, Object> mockKafkaConsumer;

    static void createMockKafkaConsumer() {
        String groupId_1 = "service-group";
        String groupId_2 = "service-type-group";
        String groupId_3 = "my-consumer-group";
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId_1);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId_2);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId_3);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        properties.put(JsonDeserializer.TRUSTED_PACKAGES, "co.edu.javeriana.msc.*");
        mockKafkaConsumer = new KafkaConsumer<>(properties, new JsonDeserializer<>(), new JsonDeserializer<>());
        mockKafkaConsumer.subscribe(List.of("paymentQueue"));
    }

    static final UserBalanceRequest validRequest = new UserBalanceRequest(
            "1",
            new co.edu.javeriana.msc.turismo.payment_microservice.payment.dto.Customer(
                    "idAleatorio", "Customer", "juanD", "juan", "Echeverry", "juan@gmail.com"
            ),
            500.0
    );

    @BeforeAll
    static void beforeAll() {
        kafka.start();
        createMockKafkaConsumer();
        mongo.start();
    }

    @AfterAll
    static void afterAll() {
        mongo.stop();
        kafka.stop();
    }

    @BeforeEach
    void setup() {

    }

    @Test
    @Order(1)
    void createUserBalanceTest() {
        ResponseEntity<String> response = paymentController.createUserBalance(validRequest);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @Order(2)
    void createRandomUserBalanceTest() {
        ResponseEntity<String> response = paymentController.createRandomUserBalance(validRequest);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @Order(3)
    void updateRandomUserBalanceTest() {
        String testId = "idAleatorio";
        ResponseEntity<String> response = paymentController.updateRandomUserBalance(testId);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(4)
    void processUserBalanceTest() {
        ResponseEntity<Map<String, String>> response = paymentController.processUserBalance(validRequest);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsKey("message");
    }

    @Test
    @Order(5)
    void createUserBalanceWithServiceMockTest() {
        PaymentService mockPaymentService = mock(PaymentService.class);
        when(mockPaymentService.createUserBalance(validRequest)).thenReturn("Mocked User Balance Created");

        PaymentController mockController = new PaymentController(mockPaymentService);

        ResponseEntity<String> response = mockController.createUserBalance(validRequest);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo("Mocked User Balance Created");

        verify(mockPaymentService, times(1)).createUserBalance(validRequest);
    }

    @Test
    @Order(6)
    void processUserBalanceWithNullResponseTest() {
        PaymentService mockPaymentService = mock(PaymentService.class);
        when(mockPaymentService.processUserBalance(validRequest)).thenReturn(null);

        PaymentController mockController = new PaymentController(mockPaymentService);

        ResponseEntity<Map<String, String>> response = mockController.processUserBalance(validRequest);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsEntry("message", null);
    }


}
