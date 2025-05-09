package jkt.rss.module.items;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemsRepository extends ReactiveCrudRepository<ItemsEntity, Long>{

}
