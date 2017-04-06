/**
 * @author seonbo.shim
 * @version 1.0, 2017-04-06
 * @copyright BESPIN GLOBAL
 */
package org.chiwooplatform.sample;

import javax.sql.DataSource;

import org.chiwooplatform.web.AbstractTests;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SampleApplication.class)
public class ChiwooWebSchemaTests
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
