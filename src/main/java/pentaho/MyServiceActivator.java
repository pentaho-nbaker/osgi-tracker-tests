package pentaho;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Created by nbaker on 9/12/14.
 */
public class MyServiceActivator implements BundleActivator {
    @Override
    public void start(BundleContext bundleContext) throws Exception {
        bundleContext.registerService(IMyService.class.getName(), new MyServiceFactory(), null);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {

    }
}
