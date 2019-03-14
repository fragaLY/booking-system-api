package by.vk.bookingsystem.configuration.mongo;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

@Configuration
@Profile("production")
public class ProductionMongoConfig extends AbstractMongoConfiguration {

  @Override
  public MongoClient mongoClient() {
    return new MongoClient("", 0);
  }

  @Override
  protected String getDatabaseName() {
    return "";
  }
}
