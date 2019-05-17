package by.vk.bookingsystem.configuration.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * The config for development profile of mongodb
 *
 * @author Vadzim_Kavalkou
 */
@Configuration
@Profile("development")
@Service
public class DevelopmentMongoConfig {

  /**
   * Configures the client to work with data in cloud storage
   *
   * @param uri - the URI to connect to cluster on Atlas
   * @return {@link MongoClient}
   */
  @Bean(destroyMethod = "close")
  @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
  public MongoClient mongoClient(@Value("${spring.data.mongodb.uri}") String uri) {
    return MongoClients.create(new ConnectionString(uri));
  }
}
