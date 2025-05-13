package jkt.rss.module.items;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ItemsService {

	private final ItemsRepository itemsRepository;
	
	public Flux<ItemsEntity> getItems(){		
		return itemsRepository.findAll();
	}
	
	public Mono<ItemsEntity> getItem(Long id){		
		return itemsRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("NOT FOUND ITEM")));
	}
	
	public Mono<ItemsEntity> saveItem(ItemsEntity item){
		return itemsRepository.save(item);
	}
	
	public Mono<Void> deleteItem(Long id){		
		return itemsRepository.deleteById(id);
	}
}
