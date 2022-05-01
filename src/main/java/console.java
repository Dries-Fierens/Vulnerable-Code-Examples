import java.sql.*;

public class console {
    public static void main(String[] args) throws SQLException {
        // mvn clean compile sonar:sonar
        // mvn clean verify -Dsonar.login="token"

        // Get username from parameters
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/user_pass", "user", "pass");;
        // Create a statement from database connection
        Statement statement = connection.createStatement();
        try {
            //request.getParameter("username")
            String username = "Isaac Newton";
            // Create unsafe query by concatenating user defined data with query string
            String query1 = "SELECT secret FROM Users WHERE (username = '" + username + "' AND NOT role = 'admin')";
            // ... OR ...
            // Insecurely format the query string using user defined data
            String query2 = String.format("SELECT secret FROM Users WHERE (username = '%s' AND NOT role = 'admin')", username);
            // Execute query and return the results
            ResultSet result1 = statement.executeQuery(query1);
            ResultSet result2 = statement.executeQuery(query2);
        } catch (Exception e) {
            //logger.log(e.getMessage());
        }finally {
            connection.close();
            statement.close();
        }
    }
}
