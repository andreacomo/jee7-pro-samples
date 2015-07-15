package it.cosenonjaviste.service;

import it.cosenonjaviste.model.Parameter;
import it.cosenonjaviste.service.qualifiers.Param;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

@ApplicationScoped
public class ParameterProducer {

    private Map<String, Parameter> params = new ConcurrentHashMap<>();

    @Inject
    private Logger logger;

    ParameterProducer() {
        params.put("param1", new Parameter("value1"));
        params.put("param2", new Parameter("value2"));
        params.put("param3", new Parameter("value3"));
        params.put("param4", new Parameter("value4"));
        params.put("param5", new Parameter("value5"));
    }

    @Produces
    @Param
    Parameter getParam(InjectionPoint injectionPoint) {
        String key = injectionPoint.getAnnotated().getAnnotation(Param.class).key();
        logger.info("Produco il parametro richiesto " + key);
        return params.get(key);
    }
}
