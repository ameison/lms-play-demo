import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;
import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import jwt.JwtControllerHelper;
import jwt.JwtControllerHelperImpl;
import jwt.JwtValidator;
import jwt.JwtValidatorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rest.v1.security.PostRepository;

import java.util.concurrent.TimeUnit;

public class Module extends AbstractModule {

    @Override
    protected void configure() {
        bind(JwtValidator.class).to(JwtValidatorImpl.class).asEagerSingleton();
        bind(JwtControllerHelper.class).to(JwtControllerHelperImpl.class).asEagerSingleton();
        bind(MetricRegistry.class).toProvider(MetricRegistryProvider.class).asEagerSingleton();
    }
}


class MetricRegistryProvider implements Provider<MetricRegistry> {
    private static final Logger logger = LoggerFactory.getLogger("application.Metrics");

    private static final MetricRegistry registry = new MetricRegistry();

    private void consoleReporter() {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(registry)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(1, TimeUnit.SECONDS);
    }

    private void slf4jReporter() {
        final Slf4jReporter reporter = Slf4jReporter.forRegistry(registry)
                .outputTo(logger)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(1, TimeUnit.MINUTES);
    }

    public MetricRegistryProvider() {
        //consoleReporter();
        // slf4jReporter();
    }

    @Override
    public MetricRegistry get() {
        return registry;
    }
}