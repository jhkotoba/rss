package jkt.rss.module.items;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ItemsHandler {	
	
	private final ItemsService itemsService;
	
	public Mono<ServerResponse> getItems(ServerRequest serverRequest){
		return itemsService.getItems()
		        .collectList()
		        .flatMap(list ->
		            ServerResponse.ok()
		                .contentType(MediaType.APPLICATION_JSON)
		                .bodyValue(list)
		        )
		        .onErrorResume(RuntimeException.class, ex ->		        	
		            ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
		                .contentType(MediaType.APPLICATION_JSON)
		                .bodyValue(Map.of("message", ex.getMessage()))
		        );
	}
	
	public Mono<ServerResponse> getItem(ServerRequest serverRequest){
		
		Long id = Long.parseLong(serverRequest.pathVariable("id"));		
		return itemsService.getItem(id)
			.flatMap(item -> ServerResponse.ok()
		            .contentType(MediaType.APPLICATION_JSON)
		            .bodyValue(item)
			)
			.onErrorResume(RuntimeException.class, ex -> {
				ex.printStackTrace();
				return ServerResponse.status(HttpStatus.UNAUTHORIZED)
					.contentType(MediaType.APPLICATION_JSON)
					.bodyValue(Map.of(
							"message", ex.getMessage()
		                ));
			});
	}
	
	public Mono<ServerResponse> saveItem(ServerRequest serverRequest){
		
		return serverRequest.bodyToMono(ItemsRequest.class)
			.flatMap(request -> {
				
				boolean hasId = serverRequest.pathVariables().containsKey("id");
				if(hasId) {
					String id = serverRequest.pathVariable("id");
					return itemsService.getItem(Long.parseLong(id))
							.flatMap(item -> {
								item.setUpdatedAt(LocalDateTime.now());
								item.setName(request.getName());
								return Mono.just(item);
							});
				}else {
					ItemsEntity item = ItemsEntity.builder()
							.name(request.getName())
							.createdAt(LocalDateTime.now())
							.updatedAt(LocalDateTime.now())
							.build();
					return Mono.just(item);
				}
			})
			.flatMap(itemsService::saveItem)
			.flatMap(item -> ServerResponse.ok()
		            .contentType(MediaType.APPLICATION_JSON)
		            .bodyValue(item)
			)
			.onErrorResume(Exception.class, ex -> {
				ex.printStackTrace();
				return ServerResponse.status(HttpStatus.UNAUTHORIZED)
					.contentType(MediaType.APPLICATION_JSON)
					.bodyValue(Map.of(
							"message", ex.getMessage()
		                ));
			});
		
	}
	
	public Mono<ServerResponse> deleteItem(ServerRequest serverRequest){
		
		Long id = Long.parseLong(serverRequest.pathVariable("id"));
		
		return itemsService.getItem(id)
			.flatMap(item -> itemsService.deleteItem(item.getId()))
			.flatMap(item -> ServerResponse.ok()
		            .contentType(MediaType.APPLICATION_JSON)
		            .bodyValue(null)
			)
			.onErrorResume(Exception.class, ex -> {				
				return ServerResponse.status(HttpStatus.UNAUTHORIZED)
					.contentType(MediaType.APPLICATION_JSON)
					.bodyValue(Map.of(
							"message", ex.getMessage()
		                ));
			});
		
	}
	
}
