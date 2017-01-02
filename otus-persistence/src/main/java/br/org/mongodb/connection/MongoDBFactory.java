package br.org.mongodb.connection;

import java.util.Arrays;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

import br.org.mongodb.codecs.ParticipantCodecProvider;

@Stateless
public class MongoDBFactory extends ConnectionConfiguration {

	@ApplicationScoped
	@Produces
	public MongoDatabase getMongoDB() {
		MongoCredential credential = MongoCredential.createCredential(username, database, password.toCharArray());

		CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
				CodecRegistries.fromProviders(new ParticipantCodecProvider()), MongoClient.getDefaultCodecRegistry());

		MongoClientOptions options = MongoClientOptions.builder().codecRegistry(codecRegistry).build();

		ServerAddress serverAddress = new ServerAddress(host, port);

		return new MongoClient(serverAddress, Arrays.asList(credential), options).getDatabase(database);
	}

}
