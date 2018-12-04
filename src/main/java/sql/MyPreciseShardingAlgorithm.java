package sql;

import com.alibaba.fastjson.JSON;
import groovy.util.logging.Slf4j;
import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import java.util.Collection;

import static org.apache.kafka.common.requests.DeleteAclsResponse.log;

@Slf4j public class MyPreciseShardingAlgorithm implements PreciseShardingAlgorithm<String> {

  @Override public String doSharding(Collection<String> collection,
      PreciseShardingValue<String> preciseShardingValue) {
    log.info("collection:"
        + JSON.toJSONString(collection)
        + ",preciseShardingValue:"
        + JSON.toJSONString(preciseShardingValue));
    for (String name : collection) {
      if (name.endsWith((preciseShardingValue.getValue().hashCode() & 15) + "")) {
        log.info("return name2:" + name);
        return name;
      }
    }
    return null;
  }
}
