/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.web.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 *
 * @author Edwin G
 */
@Configuration
@EnableWebSocketMessageBroker
@ComponentScan(basePackages = "co.com.expertla.")
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer{  
    
      @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    config.enableSimpleBroker("/topic", "/queue");
    config.setApplicationDestinationPrefixes("/app");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/chat").withSockJS();
    registry.addEndpoint("/send").withSockJS();
    registry.addEndpoint("/voice").withSockJS();
    registry.addEndpoint("/mail").withSockJS();
    registry.addEndpoint("/invitation").withSockJS();
  }
  
}
