package phovdewae.sysadminreminder.database

import android.util.Log
import android.widget.Toast
import java.lang.Exception
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
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
        //Log.d("MyTag", "$address $port $name $username $password")

        if (connection == null) {
            val connectionProps = Properties()
            connectionProps["username"] = username
            connectionProps["password"] = password

//            try {
//                Class.forName("com.mysql.jdbc.Driver").newInstance()
//                connection = DriverManager.getConnection(
//                    "jdbc:mysql://127.0.0.1:3306/sysadmin_reminder",
//                    connectionProps
//                )
//                Log.d("MyTag", "Success")
//            } catch (e: SQLException) {
//                Log.d("MyTag", "Failed at SQLException. ${e.cause}. ${e.message}")
//            } catch (e: Exception) {
//                Log.d("MyTag", "Failed at Exception. ${e.cause}. ${e.message}")
//            }

            val url = "jdbc:mysql://$address:$port/$name?serverTimezone=UTC"
            connection = DriverManager.getConnection(url, connectionProps)
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