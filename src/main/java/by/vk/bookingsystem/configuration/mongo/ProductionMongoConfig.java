package by.vk.bookingsystem.configuration.mongo;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.stereotype.Service;

/**
 * The config for production profile of mongodb
 *
 * @author Vadzim_Kavalkou
 */
@Configuration
@Profile("production")
@Service
public class ProductionMongoConfig extends AbstractMongoConfiguration {

  private static final int MONGO_DB_PORT = 27017;
  private static final String MONGO_DB_HOST = "localhost";
  private static final String MONGO_DB_NAME = "booking-system";

  /**
   * Creates new mongo client instance
   *
   * @return {@link MongoClient}
   */
  @Bean
  @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
  @Override
  public MongoClient mongoClient() {
    return new MongoClient(MONGO_DB_HOST, MONGO_DB_PORT);
  }

  /**
   * Returns the name of database
   *
   * @return {@link String}
   */
  @Override
  protected String getDatabaseName() {
    return MONGO_DB_NAME;
  }
}
