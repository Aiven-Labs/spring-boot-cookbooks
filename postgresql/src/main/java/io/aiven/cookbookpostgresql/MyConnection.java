package io.aiven.cookbookpostgresql;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class MyConnection {
    
    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void simpleQuery() {
        try (Connection connection = dataSource.getConnection()){
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT CURRENT_TIMESTAMP;");
            while (rs.next()) {
                System.out.println("Connection working, it is now : " + rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }  
    }
}
