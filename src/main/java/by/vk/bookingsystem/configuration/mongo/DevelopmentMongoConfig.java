package by.vk.bookingsystem.configuration.mongo;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

@Configuration
@Profile("development")
public class DevelopmentMongoConfig extends AbstractMongoConfiguration {

  private static final int MONGO_DB_PORT = 27017;
  private static final String MONGO_DB_HOST = "localhost";
  private static final String MONGO_DB_NAME = "booking-system";

  @Override
  public MongoClient mongoClient() {
    return new MongoClient(MONGO_DB_HOST, MONGO_DB_PORT);
  }

  @Override
  protected String getDatabaseName() {
    return MONGO_DB_NAME;
  }
}
