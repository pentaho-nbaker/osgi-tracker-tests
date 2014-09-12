package pentaho;

/**
 * Created by nbaker on 9/12/14.
 */
public class MyServiceImpl implements IMyService {
    private long bundleId;

    public MyServiceImpl(long bundleId) {

        this.bundleId = bundleId;
    }

    @Override
    public boolean iCanHazPizza() {
        return true;
    }
}
