# The chiwoo-web module supports the following functional features.

### Multilingual and message components
  - Identify multilingual locales
  - Multilingual Message DataSource support (file, DBMS, CacheDB)
  - Multilingual Message Refresh
  - Exception class and exception handler for Controller layer
  - Validation processing of request message (JSR-303 bean validation specification compliance))

### Basic Restful security architecture
  - Basic Restful security architecture with spring-security
  - User authentication processing
  - Handling Resource Access Permissions


Note that, To install into the local maven repository, run the following command:
~~~
mvn clean source:jar javadoc:jar install
~~~
