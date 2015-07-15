package it.cosenonjaviste.processors;

import javax.inject.Inject;
import java.util.logging.Logger;

public class TestClass1 {

    //@Inject
    private Logger logger = Logger.getLogger(TestClass1.class.getName());

    @Inject
    public TestClass1(DependentClass processor) {
        logger.info("Costruisco l'oggetto 1 con hash " + hashCode());
    }

    public void doSomething() {
        logger.info("faccio qualcosa nella classe 1");
    }
}
