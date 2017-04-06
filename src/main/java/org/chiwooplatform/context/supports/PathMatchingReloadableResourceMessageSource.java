package org.chiwooplatform.context.supports;

import java.util.Properties;
import java.io.IOException;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

public class PathMatchingReloadableResourceMessageSource
    extends ReloadableResourceBundleMessageSource
{
    private static final String PROPERTIES_SUFFIX = ".properties";

    private PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

    @Override
    protected PropertiesHolder refreshProperties( String filename, PropertiesHolder propHolder )
    {
        if ( filename.startsWith( PathMatchingResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX ) )
        {
            return refreshClassPathProperties( filename, propHolder );
        }
        else
        {
            return super.refreshProperties( filename, propHolder );
        }
    }

    private PropertiesHolder refreshClassPathProperties( String filename, PropertiesHolder propHolder )
    {
        Properties properties = new Properties();
        long lastModified = -1;
        try
        {
            String filepath = filename + PROPERTIES_SUFFIX;
            logger.info( "filepath: " + filepath );
            Resource[] resources = resolver.getResources( filepath );
            for ( Resource resource : resources )
            {
                String sourcePath = resource.getURI().toString().replace( PROPERTIES_SUFFIX, "" );
                logger.info( "sourcePath: " + sourcePath );
                PropertiesHolder holder = super.refreshProperties( sourcePath, propHolder );
                properties.putAll( holder.getProperties() );
                if ( lastModified < resource.lastModified() )
                    lastModified = resource.lastModified();
            }
        }
        catch ( IOException ignored )
        {
        }
        return new PropertiesHolder( properties, lastModified );
    }
}