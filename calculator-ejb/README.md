# Calculator EJB Application

A classic Java EE 6 EJB 2.x application demonstrating stateless session beans without annotations, compatible with JBoss AS.

## Project Structure

```
calculator-ejb/
├── src/
│   └── com/
│       └── calculator/
│           ├── ejb/
│           │   ├── CalculatorRemote.java
│           │   ├── CalculatorHome.java
│           │   └── CalculatorBean.java
│           ├── client/
│           │   └── CalculatorClient.java
│           └── web/
│               └── CalculatorServlet.java
├── web/
│   ├── WEB-INF/
│   │   └── web.xml
│   ├── META-INF/
│   │   └── application.xml
│   ├── calculator.jsp
│   ├── error.jsp
│   └── index.jsp
├── test/
│   └── com/
│       └── calculator/
│           └── ejb/
│               ├── CalculatorBeanTest.java
│               ├── CalculatorIntegrationTest.java
│               └── CalculatorTestSuite.java
├── lib/
│   ├── junit-4.11.jar
│   └── hamcrest-core-1.3.jar
├── META-INF/
│   ├── ejb-jar.xml
│   └── jboss.xml
├── build.xml
└── README.md
```

## Files Description

- **CalculatorRemote.java** - Remote interface defining business methods
- **CalculatorHome.java** - Home interface for creating EJB instances
- **CalculatorBean.java** - Session bean implementation with business logic
- **ejb-jar.xml** - Standard EJB deployment descriptor
- **jboss.xml** - JBoss-specific deployment descriptor with JNDI bindings
- **CalculatorClient.java** - Test client to invoke the EJB
- **CalculatorBeanTest.java** - JUnit 4 unit tests for bean logic
- **CalculatorIntegrationTest.java** - Integration tests requiring running JBoss
- **CalculatorTestSuite.java** - Test suite to run all tests
- **build.xml** - Ant build script for compilation, packaging, and testing

## Prerequisites

- JDK 6 or higher
- JBoss Application Server (AS 5.x, 6.x, or 7.x)
- Apache Ant (for building)
- JUnit 4.11 and Hamcrest Core 1.3 (for testing)

## Building the Application

1. Download required test libraries and place them in the `lib/` directory:
   - junit-4.11.jar
   - hamcrest-core-1.3.jar

2. Update the `jboss.home` property in `build.xml` to point to your JBoss installation

3. Build options:

**Build EJB only:**
```bash
ant clean package
```

**Build Web Application (WAR):**
```bash
ant package-war
```

**Build complete Enterprise Application (EAR):**
```bash
ant package-all
```

This will create:
- `calculator-ejb.jar` - EJB module
- `calculator-web.war` - Web module
- `calculator.ear` - Complete enterprise application-ejb.jar` in the `dist/` directory.

## Deployment

### Option 1: Deploy Complete EAR (Recommended)

1. Build the complete application:
```bash
ant clean package-all
```

2. Copy the EAR file to JBoss:
```bash
cp dist/calculator.ear $JBOSS_HOME/server/default/deploy/
```

3. Start JBoss server:
```bash
cd $JBOSS_HOME/bin
./run.sh  # Linux/Mac
run.bat   # Windows
```

4. Access the web interface:
```
http://localhost:8080/calculator
```

### Option 2: Deploy EJB Only

1. Copy the JAR file to JBoss deployment directory:
```bash
cp dist/calculator-ejb.jar $JBOSS_HOME/server/default/deploy/
```

2. Start JBoss server (same as above)

3. Check the server logs for successful deployment. You should see:
```
Bound EJB Home 'CalculatorEJB' to jndi 'ejb/CalculatorHome'
```

## Using the Web Interface

Once deployed, open your browser and navigate to:
```
http://localhost:8080/calculator
```

The web interface provides:
- **Modern, responsive design** with gradient backgrounds
- **Interactive operation buttons** (Add, Subtract, Multiply, Divide)
- **Real-time visual feedback** on button selection
- **Input validation** and error handling
- **Clear result display** with proper formatting
- **Mobile-friendly layout**

### Web UI Features:
1. Enter two numbers in the input fields
2. Click on one of the four operation buttons
3. Click "Calculate" to see the result
4. Results are displayed instantly below the form
5. Error messages are shown for invalid inputs or division by zero

## Testing the EJB

### Using the Web Interface
Simply open your browser and go to `http://localhost:8080/calculator`

### Using the Command-Line Client

Compile and run the test client:

```bash
javac -cp $JBOSS_HOME/client/jboss-ejb-client.jar:build com/calculator/client/CalculatorClient.java
java -cp .:$JBOSS_HOME/client/* com.calculator.client.CalculatorClient
```

Expected output:
```
Testing Calculator EJB...
10 + 5 = 15.0
10 - 5 = 5.0
10 * 5 = 50.0
10 / 5 = 2.0

All operations completed successfully!
```

## Running Unit Tests

Unit tests test the business logic without requiring a running JBoss server:

```bash
ant test-unit
```

This will run all unit tests in `CalculatorBeanTest.java` which include:
- Addition tests (positive, negative, zero, decimals)
- Subtraction tests
- Multiplication tests
- Division tests
- Division by zero exception handling
- Edge cases and boundary conditions

## Running Integration Tests

Integration tests require a running JBoss server with the EJB deployed:

1. Make sure JBoss is running with the Calculator EJB deployed
2. Run integration tests:

```bash
ant test-integration
```

This will test:
- Remote EJB invocations
- JNDI lookups
- Stateless session bean behavior
- Multiple concurrent calls

## Running All Tests

To run both unit and integration tests:

```bash
ant test-all
```

Test reports (XML format) will be generated in the `test-reports/` directory.

## API Operations

The Calculator EJB exposes four operations:

- **add(double a, double b)** - Adds two numbers
- **subtract(double a, double b)** - Subtracts second number from first
- **multiply(double a, double b)** - Multiplies two numbers
- **divide(double a, double b)** - Divides first number by second (throws exception if divisor is zero)

## JNDI Bindings

- Home Interface: `ejb/CalculatorHome`
- Remote Interface: `ejb/Calculator`

## Notes

- This uses EJB 2.x style with no annotations (pre-EJB 3.0)
- Servlets also use XML configuration (no @WebServlet annotation)
- The bean is stateless and uses container-managed transactions
- Compatible with Java EE 6 specification
- Division by zero throws a RemoteException
- Unit tests use JUnit 4 (compatible with Java EE 6)
- Integration tests require JBoss to be running
- Web UI uses classic JSP/Servlet architecture
- Modern, responsive design with CSS3 and vanilla JavaScript
- No external JavaScript libraries required

## Architecture

```
┌─────────────────┐
│   Web Browser   │
└────────┬────────┘
         │ HTTP
         ▼
┌─────────────────┐
│ CalculatorServlet│
│   (Servlet)     │
└────────┬────────┘
         │ JNDI Lookup
         ▼
┌─────────────────┐
│ CalculatorHome  │
│  (EJB Home)     │
└────────┬────────┘
         │ create()
         ▼
┌─────────────────┐
│ CalculatorRemote│
│  (EJB Remote)   │
└────────┬────────┘
         │ Business Methods
         ▼
┌─────────────────┐
│ CalculatorBean  │
│ (Session Bean)  │
└─────────────────┘
```

## Troubleshooting

**Web UI not loading:**
- Ensure the EAR file is properly deployed
- Check that JBoss is running on port 8080
- Verify the context root is `/calculator`
- Check JBoss server logs for deployment errors

**EJB lookup failures:**
- Ensure JBoss is running on default ports (1099 for JNDI)
- Check `jboss.xml` JNDI names match your lookup strings
- Verify all required JBoss client JARs are in classpath
- Review JBoss server logs for deployment errors

**Servlet errors:**
- Verify web.xml is properly configured
- Ensure servlet-api.jar is available during compilation
- Check that the EJB JAR is included in the WAR's WEB-INF/lib

**Build errors:**
- Update `jboss.home` property in build.xml
- Ensure all JBoss libraries are accessible
- Check Java version compatibility (JDK 6+)
