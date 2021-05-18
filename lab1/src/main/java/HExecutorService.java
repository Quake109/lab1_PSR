import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.Set;
import java.util.concurrent.Callable;

import com.hazelcast.aggregation.Aggregators;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastInstanceAware;
import com.hazelcast.core.IExecutorService;
import com.hazelcast.map.IMap;
import models.Client;
import models.ParcelPoint;

public class HExecutorService {

    public static void init() throws UnknownHostException {
        ClientConfig clientConfig = HConfig.getClientConfig();
        final HazelcastInstance client = HazelcastClient.newHazelcastClient( clientConfig );

        IExecutorService executorService = client.getExecutorService("exec");
        executorService.submitToAllMembers(new HCallable());
    }
}

class HCallable implements Callable<Integer>, Serializable, HazelcastInstanceAware {
    private static final long serialVersionUID = 1L;
    private transient HazelcastInstance instance;

    @Override
    public Integer call() throws UnknownHostException {
        IMap<Long, ParcelPoint> parcelPoints = instance.getMap("parcelPoints");
        Set<Long> keys = parcelPoints.localKeySet();
        System.out.println("\nLowest capacity Parcel Point:");
        System.out.println(parcelPoints.aggregate(Aggregators.integerMin("capacity"))+ "\n");

        return keys.size();
    }

    @Override
    public void setHazelcastInstance(HazelcastInstance instance) {
        this.instance = instance;
    }
}