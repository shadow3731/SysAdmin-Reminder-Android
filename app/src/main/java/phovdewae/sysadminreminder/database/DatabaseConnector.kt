package phovdewae.sysadminreminder.database

import android.os.StrictMode
import android.util.Log
import android.widget.Toast
import java.lang.Exception
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement
import java.util.Properties

object DatabaseConnector {

    private var connection: Connection? = null

    fun createConnection(
        address: String,
        port: String,
        name: String,
        username: String,
        password: String
    ) {
        Log.d("MyTag", "$address $port $name $username $password")

        if (connection == null) {

            try {
//                val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
//                StrictMode.setThreadPolicy(policy)
                Class.forName("com.mysql.cj.jdbc.Driver")
                connection = DriverManager.getConnection(
                    "jdbc:mysql://$address:$port/$name?enabledTLSProtocols=TLSv1.2",
                    username,
                    password
                )
                Log.d("MyTag", "Success")
            } catch (e: SQLException) {
                Log.d("MyTag", "Failed at SQLException. ${e.cause}. ${e.message}\n${e.printStackTrace()}")
            } catch (e: Exception) {
                Log.d("MyTag", "Failed at Exception. ${e.cause}. ${e.message}\n${e.printStackTrace()}")
            }

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