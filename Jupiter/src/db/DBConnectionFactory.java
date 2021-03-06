package db;
//case mongo
import db.mongodb.MongoDBConnection;
//case Mysql
import db.mysql.MySQLConnection;

//This is used to create different db instances (MYSQL MONO)
public class DBConnectionFactory {
	// This should change based on the pipeline.
	//use this line for the version 1
	//private static final String DEFAULT_DB = "mysql";
	//updated for mongo db on 05/09
	private static final String DEFAULT_DB = "mysql";
	public static DBConnection getConnection(String db) {
		switch (db) {
		case "mysql":
			// return new MySQLConnection();
			return new MySQLConnection();
		case "mongodb":
			// return new MongoDBConnection();
			return new MongoDBConnection();
		default:
			throw new IllegalArgumentException("Invalid db:" + db);
		}

	}

	public static DBConnection getConnection() {
		return getConnection(DEFAULT_DB);
	}
}
