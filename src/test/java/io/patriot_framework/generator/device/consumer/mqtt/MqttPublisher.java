/*
 * Copyright 2020 Patriot project
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package io.patriot_framework.generator.device.consumer.mqtt;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class MqttPublisher {
    private String clientId;
    private Connection connection;
    private Session session;
    private MessageProducer messageProducer;

    public void create(String clientId, String topicName) throws JMSException {
        this.clientId = clientId;

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:8555");

        connection = connectionFactory.createConnection();
        connection.setClientID(clientId);
        System.out.println("Created connection factory");

        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        System.out.println("Created session");

        Topic topic = session.createTopic(topicName);

        messageProducer = session.createProducer(topic);
        System.out.println("Created topic");
    }

    public void closeConnection() throws JMSException {
        connection.close();
    }

    public void publish(String message) throws JMSException {
        TextMessage textMessage = session.createTextMessage(message);
        messageProducer.send(textMessage);
        System.out.println("Sent message");
    }
}
