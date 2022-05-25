import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.Properties;

public class Console {
    public static void main(String[] args) throws SQLException, IOException, GeneralSecurityException {
        // mvn clean compile sonar:sonar
        // mvn clean verify -Dsonar.login="token"

        Properties properties = new Properties();
        FileInputStream inputStream = new FileInputStream("src/config.properties");
        properties.load(inputStream);
        String password = properties.getProperty("password");

        if (password == null) {
            throw new IllegalArgumentException("No such parameter present in config file");
        }

        byte[] salt = "54765496".getBytes();
        int iterationCount = 40000;
        int keyLength = 128;
        SecretKeySpec key = Encryption.createSecretKey(password.toCharArray(), salt, iterationCount, keyLength);

        String encryptedPassword = Encryption.encrypt(password, key);
        properties.setProperty("password", encryptedPassword);
        //System.out.println("Encrypted password: " + encryptedPassword);
        String decryptedPassword = Encryption.decrypt(encryptedPassword, key);
        properties.setProperty("password", decryptedPassword);

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/user_pass", "user", decryptedPassword);
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
