package it.cosenonjaviste.processors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.logging.Logger;

@ApplicationScoped
public class TestClass2 {

    @Inject
    private TestClass1 testClass1;

    //@Inject
    private Logger logger = Logger.getLogger(TestClass2.class.getName());

    public TestClass2() {
        logger.info("Costruisco l'oggetto 2 con hash " + hashCode());
        logger.info("Costruttore: TestClass1 is null? " + (testClass1 == null));
    }

    @PostConstruct
    void init() {
        logger.info("Init dell'oggetto 2 con hash " + hashCode());
        logger.info("Init method: TestClass1 is null? " + (testClass1 == null));
    }

    public void doSomething() {
        logger.info("faccio qualcosa nella classe 2");
        testClass1.doSomething();
    }
}
