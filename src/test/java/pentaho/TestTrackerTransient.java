/*
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
 * Foundation.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 * or from the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * Copyright 2014 Pentaho Corporation. All rights reserved.
 */

package pentaho;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerMethod;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;

import javax.inject.Inject;
import java.io.InputStream;

import static org.ops4j.pax.exam.CoreOptions.*;
import static org.ops4j.pax.tinybundles.core.TinyBundles.bundle;
import static org.ops4j.pax.tinybundles.core.TinyBundles.withBnd;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerMethod.class)
public class TestTrackerTransient {


    @Configuration
    public Option[] config() {

        InputStream inp = bundle()
                .add(IMyService.class)
                .add(MyServiceImpl.class)
                .add( MyServiceFactory.class )
                .add( MyServiceTracker.class )
                .add( MyTrackerActivator.class )
                .set(Constants.BUNDLE_SYMBOLICNAME, "Test_Bundle")
                .set( Constants.EXPORT_PACKAGE, "*" )
                .set(Constants.IMPORT_PACKAGE, "*")
                .set( Constants.BUNDLE_ACTIVATOR, MyTrackerActivator.class.getName() )
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

        bundleContext.registerService(IMyService.class.getName(), new MyServiceFactory(), null);
        Bundle bundle = getBundleBySymbolicName("Test_Bundle");
        bundle.stop();
        bundle.start();
    }
}