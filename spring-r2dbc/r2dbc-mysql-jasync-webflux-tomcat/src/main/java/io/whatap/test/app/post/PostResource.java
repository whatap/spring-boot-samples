package io.whatap.test.app.post;

import com.github.jasync.r2dbc.mysql.JasyncConnectionFactory;
import com.github.jasync.sql.db.mysql.pool.MySQLConnectionFactory;
import com.github.jasync.sql.db.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/posts")
public class PostResource {

    private final PostRepository postRepository;

    public PostResource(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping
    public Flux<Post> getPosts() {
        return postRepository.findAll();
    }

    @GetMapping(value = "/mycf")
    public Flux<Post> getPostClient() {
        MySQLConnectionFactory mycf = new MySQLConnectionFactory(
                new Configuration("root", "localhost", 3306, "1111", "test"));
        JasyncConnectionFactory cf = new JasyncConnectionFactory(mycf);
        DatabaseClient client = DatabaseClient.builder().connectionFactory(cf).build();
//                .dataAccessStrategy(new DefaultReactiveDataAccessStrategy(PostgresDialect.INSTANCE)).build();

        client.sql("SELECT * FROM tb_post")
                .map((row, rowMetadata) -> row.get("title"))
                .all()
                .doOnNext(it -> {
                    System.out.println("Record: " + it);
                })
                .blockFirst();
//                .as(StepVerifier::create)
//                .expectNextCount(585)
//                .verifyComplete();

        return null;
    }

    @GetMapping(value = "/find")
    public Flux<?> findByTitle(@RequestParam(value = "title") String like) {
        return postRepository.findByTitleContains(like);
    }

    @GetMapping(value = "/{id}")
    public Mono<?> findById(@PathVariable(value = "id") Integer id) {
        return postRepository.findById(id);
    }

    @GetMapping(value = "/save-one")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<?> savePost() {
        Post post = new Post();
        post.setAuthor("author");
        post.setTitle("test");
        post.setContent("test-content");
        return postRepository.save(post);
    }

    @PostMapping(value = "/save-one-post")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<?> savePostPost(@RequestBody Post post) {
        return postRepository.save(post);
    }

    @GetMapping(value = "/save-all")
    @ResponseStatus(HttpStatus.CREATED)
    public Flux<Post> savePosts() {
        List<Post> posts = new ArrayList<>();
        for (long i = 0; i < 11; i++) {
            Post post = new Post();
            post.setAuthor("author_" + (i + 1));
            post.setTitle("test_" + (i + 1));
            post.setContent("test-content_" + (i + 1));
            posts.add(post);
        }
        return postRepository.saveAll(posts);
    }

    @GetMapping(value = "/delete/{id}")
    public Mono<Void> deletePost(@PathVariable(value = "id") Integer id) {
        return postRepository.deleteById(id);
    }

    @GetMapping(value = "/update/{id}")
    public Mono<Post> updatePost(@PathVariable(value = "id") Integer id) {
        System.out.println("/update/" + id);
        String strVal = String.valueOf(id);
        return postRepository.findById(id).map(post -> {
            post.setTitle("test_" + strVal + " update title");
            post.setContent("test_" + strVal + " update content");
            return post;
        }).flatMap(post -> postRepository.save(post));
    }

    @GetMapping(value = "/query/update/{id}")
    public Mono<Void> updatePost(@PathVariable(value = "id") Integer id,
                                 @RequestParam(value = "title") String updateTitle) {
        return postRepository.updateTitleById(updateTitle, id);
    }

    @GetMapping(value = "/query/do-sleep/{sleep}")
    public Mono<Void> doSleep(@PathVariable(value = "sleep") int sleep) {
        return postRepository.doSleep(sleep);
    }

}
