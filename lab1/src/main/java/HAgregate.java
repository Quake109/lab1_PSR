import java.net.UnknownHostException;

import com.hazelcast.aggregation.Aggregators;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import models.ParcelPoint;

public class HAgregate {

    public static void init() throws UnknownHostException {
        ClientConfig clientConfig = HConfig.getClientConfig();
		final HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
		IMap<Long, ParcelPoint> parcelPoints = client.getMap("parcelPoints");
		System.out.println("\nLowest capacity Parcel Point:");
		System.out.println(parcelPoints.aggregate(Aggregators.integerMin("capacity"))+ "\n");
	}
}
