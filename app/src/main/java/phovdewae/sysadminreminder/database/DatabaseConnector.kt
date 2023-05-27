package phovdewae.sysadminreminder.database

import android.util.Log
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object DatabaseConnector {

    private var connection: Connection? = null

    fun getConnection(
        address: String,
        port: String,
        name: String,
        username: String,
        password: String
    ): Connection {
        Log.d("MyTag", "$address $port $name $username $password")

        if (connection == null) {
            val url = "jdbc:mysql://$address:$port/$name"

            Class.forName("com.mysql.cj.jdbc.Driver")

            connection = DriverManager.getConnection(url, username, password)
        }
        return connection!!
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