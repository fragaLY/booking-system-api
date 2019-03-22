package by.vk.bookingsystem.configuration.mongo;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

/**
 * The config for development profile of mongodb
 *
 * @author Vadzim_Kavalkou
 */
@Configuration
@Profile("development")
public class DevelopmentMongoConfig extends AbstractMongoConfiguration {

  private static final int MONGO_DB_PORT = 27017;
  private static final String MONGO_DB_HOST = "localhost";
  private static final String MONGO_DB_NAME = "booking-system";

  /**
   * Creates new mongo client instance
   *
   * @return {@link MongoClient}
   */
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
