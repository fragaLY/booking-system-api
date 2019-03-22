package by.vk.bookingsystem.configuration.mongo;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

/**
 * The config for production profile of mongodb
 *
 * @author Vadzim_Kavalkou
 */
@Configuration
@Profile("production")
public class ProductionMongoConfig extends AbstractMongoConfiguration {

  /**
   * Creates new mongo client instance
   *
   * @return {@link MongoClient}
   */
  @Override
  public MongoClient mongoClient() {
    return new MongoClient("", 0);
  }

  /**
   * Returns the name of database
   *
   * @return {@link String}
   */
  @Override
  protected String getDatabaseName() {
    return "";
  }
}
