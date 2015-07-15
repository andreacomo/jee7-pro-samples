package it.cosenonjaviste.service;

import it.cosenonjaviste.model.Parameter;
import it.cosenonjaviste.service.qualifiers.Param;
import it.cosenonjaviste.utils.BaseArchive;
import it.cosenonjaviste.utils.BeanManagerHelper;
import junit.framework.TestCase;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;

@RunWith(Arquillian.class)
public class ParameterProducerTest extends TestCase {

    @Deployment
    public static Archive<?> createTestArchive() {
        return BaseArchive.create()
                .addClass(Param.class)
                .addClass(ParameterProducer.class)
                .addClass(Parameter.class)
                .addClass(BeanManagerHelper.class)
                .addClass(ParamLiteral.class);
    }

    @Inject
    @Param(key = "param1")
    private Parameter myParam;

    /**
     * mvn clean test -Parq-wildfly-managed -Dtest=ParameterProducerTest#testGetParam  -DfailIfNoTests=false
     */
    @Test
    public void testGetParam() throws Exception {

        assertNotNull(myParam);
        assertEquals("value1", myParam.getValue());

    }

    /**
     * mvn clean test -Parq-wildfly-managed -Dtest=ParameterProducerTest#testManualGetParam  -DfailIfNoTests=false
     */
//    @Test
//    public void testManualGetParam() throws Exception {
//        Parameter parameter = BeanManagerHelper.getBean(Parameter.class, new ParamLiteral("param1"));
//
//        assertNotNull(parameter);
//        assertEquals("value1", parameter.getValue());
//
//    }

    private static class ParamLiteral extends AnnotationLiteral<Param> implements Param {

        private String key;

        public ParamLiteral(String key) {
            this.key = key;
        }

        @Override
        public String key() {
            return key;
        }
    }
}