package pentaho;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceFactory;
import org.osgi.framework.ServiceRegistration;

/**
 * Created by nbaker on 9/12/14.
 */
public class MyServiceFactory implements ServiceFactory<IMyService> {
    public IMyService getService(Bundle bundle, ServiceRegistration serviceRegistration) {
        return new MyServiceImpl(bundle.getBundleId());
    }

    public void ungetService(Bundle bundle, ServiceRegistration serviceRegistration, IMyService o) {

    }
}
