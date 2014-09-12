package pentaho;

import static org.junit.Assert.*;
import static org.ops4j.pax.exam.CoreOptions.*;
import static org.ops4j.pax.tinybundles.core.TinyBundles.*;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;
import org.ops4j.pax.exam.spi.reactors.PerMethod;
import org.osgi.framework.*;
import org.osgi.util.tracker.ServiceTracker;

import java.io.InputStream;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerMethod.class)
public class TestTracker {


    @Configuration
    public Option[] config() {

        InputStream inp = bundle()
                .add(IMyService.class)
                .add(MyServiceImpl.class)
                .add( MyServiceFactory.class )
                .add( MyServiceActivator.class )
                .add( MyServiceTracker.class )
                .set(Constants.BUNDLE_SYMBOLICNAME, "Test_Bundle")
                .set( Constants.EXPORT_PACKAGE, "*" )
                .set(Constants.IMPORT_PACKAGE, "*")
                .set( Constants.BUNDLE_ACTIVATOR, MyServiceActivator.class.getName() )
                .build( withBnd() );

        return options(
                //mavenBundle("com.example.myproject", "myproject-api", "1.0.0-SNAPSHOT"),
                //org.ops4j.pax.exam.CoreOptions.bundle("http://www.example.com/repository/foo-1.2.3.jar"),
                junitBundles(),
                provision( inp )
        );
    }

    @Inject
    private BundleContext bundleContext;

    private Bundle getBundleBySymbolicName( String name ){
        Bundle[] bundles = bundleContext.getBundles();
        for (Bundle bundle : bundles) {
            if(bundle.getSymbolicName().equals(name)){
                return bundle;
            }
        }
        return null;
    }

    @Test
    public void testHttpTracker() throws BundleException {
        ServiceTracker<IMyService, IMyService> tracker = new MyServiceTracker(bundleContext);
        tracker.open();
        Bundle bundle = getBundleBySymbolicName("Test_Bundle");
        bundle.stop();
        bundle.start();
    }
}