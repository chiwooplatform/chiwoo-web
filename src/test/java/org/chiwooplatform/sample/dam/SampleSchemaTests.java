package org.chiwooplatform.sample.dam;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import org.chiwooplatform.sample.AbstractTests;
import org.chiwooplatform.sample.SampleApplication;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SampleApplication.class)
public class SampleSchemaTests
    extends AbstractTests {

    @Autowired
    private DataSource dataSource;

    @Test
    @Sql(scripts = { "/schema.ddl" })
    public void testCreateSchema()
        throws Exception {
        logger.info( "dataSource: {}", dataSource );
    }

    @Test
    @Rollback(false)
    @Sql(value = { "/schema-imports.sql" })
    public void testLoadData()
        throws Exception {
        logger.info( "dataSource: {}", dataSource );
    }
}
