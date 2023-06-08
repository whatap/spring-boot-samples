package io.whatap.test.app.post;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostRepository extends ReactiveCrudRepository<Post, Long> {

    @Query("SELECT * FROM tb_post WHERE title like :like")
    Flux<Post> findByTitleContains(String like);

    @Query("UPDATE tb_post SET title = :updateTitle WHERE id = :id")
    Mono<Void> updateTitleById(String updateTitle, Long id);

    @Query("DO SLEEP(:sleep)")
    Mono<Void> doSleep(int sleep);
}
