package phovdewae.sysadminreminder.database

import android.util.Log
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.MongoException
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.client.MongoClients
import org.bson.BsonDocument
import org.bson.BsonInt64
import java.lang.Exception
import java.sql.Connection
import java.sql.SQLException

object DatabaseConnector {

    private var connection: Connection? = null

    fun createConnection(
        address: String,
        port: String,
        name: String,
        username: String,
        password: String
    ) {
        //Log.d("MyTag", "$address $port $name $username $password")

        if (connection == null) {
            val uri = "mongodb+srv://admin:050799@sysadminremindercluster.lxdhxvv.mongodb.net/?retryWrites=true&w=majority"
            val serverApi = ServerApi
                .builder()
                .version(ServerApiVersion.V1)
                .build()
            val mongoSettings = MongoClientSettings
                .builder()
                .applyConnectionString(ConnectionString(uri))
                .serverApi(serverApi)
                .build()

            try {
                val mongoClient = MongoClients.create(mongoSettings)
                val database = mongoClient.getDatabase("sysadmin_reminder")
                try {
                    val command = BsonDocument("ping", BsonInt64(1))
                    val commandResult = database.runCommand(command)
                    Log.d("MyTag", "Success")
                } catch (e: MongoException) {
                    Log.d("MyTag", e.printStackTrace().toString())
                }
            } catch(e: Exception) {
                Log.d("MyTag", e.printStackTrace().toString())
            }



//            try {
//                Class.forName("com.mysql.jdbc.Driver")
//
////                val url = "jdbc:mysql://$address:$port/$name"
//                val url = "jdbc:mysql://ued3rerxb1uk5nhp:wu390qTm3t3rjJoOE1ax@bqpfui1iyw2syeztdsjv-mysql.services.clever-cloud.com:3306/bqpfui1iyw2syeztdsjv"
//                val user = "ued3rerxb1uk5nhp"
//                val pass = "wu390qTm3t3rjJoOE1ax"
//                //connection = DriverManager.getConnection(url, username, password)
//                connection = DriverManager.getConnection(url, user, pass)
//                Log.d("MyTag", "Success")
//            } catch (e: SQLException) {
//                Log.d("MyTag", "Failed at SQLException. ${e.cause}. ${e.message}\n${e.printStackTrace()}")
//            } catch (e: Exception) {
//                Log.d("MyTag", "Failed at Exception. ${e.cause}. ${e.message}\n${e.printStackTrace()}")
//            }

//            val url = "jdbc:mysql://$address:$port/$name?serverTimezone=UTC"
//            connection = DriverManager.getConnection(url, connectionProps)
        }
    }

    fun connectionExists(): Boolean {
        return try {
            connection?.isValid(5) ?: false
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        }
    }
}