
package hello.producer;

import hello.model.Aktion;
import hello.model.Auftrag;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SerializerMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SpringBootApplication
public class ApplicationProducer implements CommandLineRunner {

	final static String queueName = "spring-boot";

	@Autowired
	AnnotationConfigApplicationContext context;

	@Autowired
	RabbitTemplate rabbitTemplate;

	@Bean
	Queue queue() {
		return new Queue(queueName, true);
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange("spring-boot-exchange");
	}

	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(queueName);
	}



	@Bean
	public MessageConverter jsonMessageConverter(){
		return new SerializerMessageConverter();
	}

	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory =new CachingConnectionFactory("192.168.99.100",5672);
		connectionFactory.setUsername("guest");
		connectionFactory.setPassword("guest");
		return connectionFactory;
	}

	@Bean
	SimpleMessageListenerContainer container() {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory());
		container.setQueueNames(queueName);
		container.setMessageConverter(jsonMessageConverter());

		return container;
	}




    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(ApplicationProducer.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
		for(int i=0;i<1000;i++) {
//			Thread.sleep(5000);
			System.out.println("Sending message... "+i);
//			rabbitTemplate.convertAndSend(queueName, "Hello from RabbitMQ! "+i);
			rabbitTemplate.setMessageConverter(jsonMessageConverter());
			rabbitTemplate.convertAndSend(queueName, createAuftrag(i));


		}
		System.out.println("Waiting five seconds...");
		Thread.sleep(5000);
		System.out.println("Close springcontext");
		context.close();
    }

	private Auftrag createAuftrag(int i) {

		Auftrag a = new Auftrag();
		a.setName("Auftrag_"+i);
		a.setNumber(i);

		a.getActions().addAll(createActionsList());

		return a;
	}

	private Collection<? extends Aktion> createActionsList() {
		List<Aktion> aktions = new ArrayList<>();

		{
			Aktion a = new Aktion();
			a.setName("Aktion_1");
			aktions.add(a);
		}
		{
			Aktion a = new Aktion();
			a.setName("Aktion_2");
			aktions.add(a);
		}

		return aktions;
	}
}
