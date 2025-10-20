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
│           └── client/
│               └── CalculatorClient.java
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
- **build.xml** - Ant build script for compilation and packaging

## Prerequisites

- JDK 6 or higher
- JBoss Application Server (AS 5.x, 6.x, or 7.x)
- Apache Ant (for building)

## Building the Application

1. Update the `jboss.home` property in `build.xml` to point to your JBoss installation

2. Compile and package:
```bash
ant clean package
```

This will create `calculator-ejb.jar` in the `dist/` directory.

## Deployment

1. Copy the JAR file to JBoss deployment directory:
```bash
cp dist/calculator-ejb.jar $JBOSS_HOME/server/default/deploy/
```

2. Start JBoss server:
```bash
cd $JBOSS_HOME/bin
./run.sh  # Linux/Mac
run.bat   # Windows
```

3. Check the server logs for successful deployment. You should see:
```
Bound EJB Home 'CalculatorEJB' to jndi 'ejb/CalculatorHome'
```

## Testing the EJB

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
- The bean is stateless and uses container-managed transactions
- Compatible with Java EE 6 specification
- Division by zero throws a RemoteException

## Troubleshooting

- Ensure JBoss is running on default ports (1099 for JNDI)
- Check `jboss.xml` JNDI names match your lookup strings
- Verify all required JBoss client JARs are in classpath
- Review JBoss server logs for deployment errors
