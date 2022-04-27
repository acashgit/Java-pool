package edu.school21.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.SQLException;

public class EmbeddedDataSourceTest {
    private EmbeddedDatabase dataSource;
    @BeforeEach
    void init(){
        dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL).addScript("schema.sql").addScript("data.sql").build();
    }

    @Test
    void testGetConnect() throws SQLException {
        Assertions.assertNotNull(dataSource.getConnection());
    }
    @AfterEach
    void after(){
        dataSource.shutdown();
    }
}
