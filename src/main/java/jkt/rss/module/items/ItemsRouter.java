package jkt.rss.module.items;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ItemsRouter {
	
	@Bean
	protected RouterFunction<ServerResponse> itemRouter(ItemsHandler handler){
		return RouterFunctions
			.route(RequestPredicates.GET("/api/items")
				.and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::getItems)
			.andRoute(RequestPredicates.GET("/api/item/{id}")
				.and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::getItem)
			.andRoute(RequestPredicates.POST("/api/item")
				.and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::saveItem)
			.andRoute(RequestPredicates.PUT("/api/item/{id}")
				.and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::saveItem)
			.andRoute(RequestPredicates.DELETE("/api/item/{id}")
				.and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::deleteItem);
	}
}