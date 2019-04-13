package strava.config

import com.mongodb.MongoClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.MongoDbFactory
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.SimpleMongoDbFactory
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper
import org.springframework.data.mongodb.core.convert.MappingMongoConverter
import org.springframework.data.mongodb.core.mapping.MongoMappingContext


@Configuration
class SpringMongoConfig(@Value("\${spring.data.mongodb.database}") val databaseName: String) {

    @Bean
    fun mongoDbFactory(): MongoDbFactory {
        return SimpleMongoDbFactory(MongoClient(), databaseName)
    }

    @Bean
    fun mongoTemplate(): MongoTemplate {

        //remove _class
        val converter = MappingMongoConverter(mongoDbFactory(), MongoMappingContext())
        converter.setTypeMapper(DefaultMongoTypeMapper(null))

        return MongoTemplate(mongoDbFactory(), converter)

    }

}
