import java.sql.*;

// This class has some useful methods that can be used by other programs
public class jdbc_db {

   // The instance variables for the class
   private Connection connection;
   private Statement statement;

   // The constructor for the class
   public jdbc_db() {
      connection = null;
      statement = null;
   }

   // Connect to the database
   public void connect(String Username, String mysqlPassword) throws SQLException {
      try {
         connection = DriverManager.getConnection("jdbc:mysql://localhost/" + Username + "?" +
               "user=" + Username + "&password=" + mysqlPassword + "&useSSL=false");
      } catch (Exception e) {
         throw e;
      }
   }

   // Disconnect from the database
   public void disConnect() throws SQLException {
      connection.close();
      statement.close();
   }

   // Execute an SQL query passed in as a String parameter
   // and print the resulting relation
   public String query(String q) {
      StringBuilder builder = new StringBuilder();

      try {
         ResultSet resultSet = statement.executeQuery(q);
         builder.append("<br>---------------------------------<br>");
         builder.append("Query: <br>" + q + "<br><br>Result: ");
         builder.append(print(resultSet));
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return builder.toString();
   }

   // Execute an SQL query passed in as a String parameter
   // and print the resulting relation
   public ResultSet rawQuery(String q) {
      ResultSet resultSet = null;

      try {
         resultSet = statement.executeQuery(q);
      } catch (SQLException e) {
         e.printStackTrace();
      }

      return resultSet;
   }

   // Print the results of a query with attribute names on the first line
   // Followed by the tuples, one per line
   public String print(ResultSet resultSet) throws SQLException {
      StringBuilder builder = new StringBuilder();
      ResultSetMetaData metaData = resultSet.getMetaData();
      int numColumn = metaData.getColumnCount();

      builder.append(printHeader(metaData, numColumn));
      builder.append(printRecords(resultSet, numColumn));

      return builder.toString();
   }

   // Print the attribute names
   public String printHeader(ResultSetMetaData metaData, int numColumn) throws SQLException {
      StringBuilder builder = new StringBuilder();

      for (int i = 1; i <= numColumn; i++) {
         if (i > 1)
            builder.append(",  ");
         builder.append(metaData.getColumnName(i));
      }
      builder.append("<br>");
      return builder.toString();
   }

   // Print the attribute values for all tuples in the result
   public String printRecords(ResultSet resultSet, int numColumn) throws SQLException {
      StringBuilder builder = new StringBuilder();
      String columnValue;

      while (resultSet.next()) {
         for (int i = 1; i <= numColumn; i++) {
            if (i > 1)
               builder.append(",  ");
            columnValue = resultSet.getString(i);
            builder.append(columnValue);
         }
         builder.append("<br>");
      }
      return builder.toString();
   }

   // Insert into any table, any values from data passed in as String parameters
   public void insert(String table, String values) {
      String query = "INSERT INTO " + table + " VALUES (" + values + ")";
      try {
         statement.executeUpdate(query);
         System.out.println("inserted");
      } catch (SQLException e) {
         System.out.println("ERROR: not inserted");
         System.out.println("ERROR: Query: " + query);
         System.out.println("ERROR:" + e);
         e.printStackTrace();
      }
   }

   // Create a table only if it doesn't already exist
   public void createTable(String tableName, String columns) {
      // Check if the table already exists
      if (!tableExists(tableName)) {
         String query = "CREATE TABLE " + tableName + " (" + columns + ")";

         try {
            statement.executeUpdate(query);
            System.out.println("ERROR: Table '" + tableName + "' created successfully.");
         } catch (SQLException e) {
            e.printStackTrace();
         }
      } else {
         System.out.println("ERROR: Table '" + tableName + "' already exists.");
      }
   }

   // Helper method to check if a table exists
   private boolean tableExists(String tableName) {
      try {
         DatabaseMetaData dbm = connection.getMetaData();
         ResultSet tables = dbm.getTables(null, null, tableName, null);
         if (tables.next()) {
            return true; // Table exists
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return false; // Table does not exist
   }

   public boolean studentExists(int studentID) {
      String q = "SELECT COUNT(*) FROM Student WHERE studentID = " + studentID + ";";
      try {
         ResultSet resultSet = statement.executeQuery(q);
         if (resultSet.next()) {
            return resultSet.getInt(1) > 0;
         } else {
            System.out.println("ERROR: Couldn't find student");
            return false;
         }
      } catch (SQLException e) {
         e.printStackTrace();
         System.out.println("ERROR: Couldn't find student");
         return false;
      }
   }
   public boolean buildingExists(int buildingID) {
      String q = "SELECT COUNT(*) FROM Building WHERE buildingID = " + buildingID + ";";
      try {
         ResultSet resultSet = statement.executeQuery(q);
         if (resultSet.next()) {
            return resultSet.getInt(1) > 0;
         } else {
            System.out.println("ERROR: Couldn't find building");
            return false;
         }
      } catch (SQLException e) {
         e.printStackTrace();
         System.out.println("ERROR: Couldn't find building");
         return false;
      }
   }
   public boolean roomExists(int roomID) {
      String q = "SELECT COUNT(*) FROM Room WHERE roomID = " + roomID + ";";
      try {
         ResultSet resultSet = statement.executeQuery(q);
         if (resultSet.next()) {
            return resultSet.getInt(1) > 0;
         } else {
            System.out.println("ERROR: Couldn't find room");
            return false;
         }
      } catch (SQLException e) {
         e.printStackTrace();
         System.out.println("ERROR: Couldn't find room");
         return false;
      }
   }

   // public boolean roomFull(int roomID) {
   //    String q = "SELECT COUNT(*) FROM Assignment WHERE roomID = " + roomID + ";";
   //    try {
   //       ResultSet resultSet = statement.executeQuery(q);
   //       if (resultSet.next()) {
   //          return resultSet.getInt(1) > 0;
   //       } else {
   //          System.out.println("Couldn't find room");
   //          return false;
   //       }
   //    } catch (SQLException e) {
   //       e.printStackTrace();
   //       System.out.println("Couldn't find room");
   //       return false;
   //    }
   // }

   // Remove all records and fill them with values for testing
   // Assumes that the tables are already created
   public void initDatabase() throws SQLException {
      statement = connection.createStatement();

   }
}
