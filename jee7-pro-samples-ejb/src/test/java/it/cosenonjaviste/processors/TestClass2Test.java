package it.cosenonjaviste.processors;

import it.cosenonjaviste.utils.BaseArchive;
import it.cosenonjaviste.utils.BeanManagerHelper;
import junit.framework.TestCase;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.lang.annotation.Annotation;
import java.util.logging.Logger;

@RunWith(Arquillian.class)
public class TestClass2Test extends TestCase {

    @Inject
    private TestClass2 testClass2;

    @Inject
    private TestClass2 testClass22;

    @Inject
    private Logger logger = Logger.getLogger(TestClass2Test.class.getName());

    public TestClass2Test() {
        logger.info("Costruisco la classe di test");
    }

    @Deployment
    public static Archive<?> createTestArchive() {
        return BaseArchive.create()
                .addClass(TestClass2.class)
                .addClass(TestClass1.class)
                .addClass(DependentClass.class)
                .addClass(BeanManagerHelper.class);
    }

    /**
     * mvn clean test -Parq-wildfly-managed -Dtest=TestClass2Test#testDoSomething  -DfailIfNoTests=false
     */
    @Test
    public void testDoSomething() {
        logger.info("Avvio il test con la classe " + testClass2);
        testClass2.doSomething();
        logger.info("Chiamo testClass22");
        testClass22.doSomething();
    }

    /**
     * mvn clean test -Parq-wildfly-managed -Dtest=TestClass2Test#testManualLookup  -DfailIfNoTests=false
     */
    @Test
    public void testManualLookup() throws Exception {
        TestClass2 localTestClass2 = BeanManagerHelper.getBean(TestClass2.class);

        assertEquals(testClass2, localTestClass2);
    }
}