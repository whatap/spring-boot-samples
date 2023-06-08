package io.whatap.test.app.post;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/posts")
public class PostController {

    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping
    public Flux<Post> getPosts() {
        return postRepository.findAll();
    }

    @GetMapping(value = "/find")
    public Flux<?> findByTitle(@RequestParam(value = "title") String like) {
        return postRepository.findByTitleContains(like);
    }

    @GetMapping(value = "/{id}")
    public Mono<?> findById(@PathVariable(value = "id") String id) {
        Long idL = Long.parseLong(id);
        return postRepository.findById(idL);
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

    @GetMapping(value = "/save-all")
    @ResponseStatus(HttpStatus.CREATED)
    public Flux<Post> savePosts() {
        List<Post> posts = new ArrayList<>();
        for (long i = 0; i < 2; i++) {
            Post post = new Post();
            post.setAuthor("author_" + (i + 1));
            post.setTitle("test_" + (i + 1));
            post.setContent("test-content_" + (i + 1));
            posts.add(post);
        }
        return postRepository.saveAll(posts);
    }

    @GetMapping(value = "/delete/{id}")
    public Mono<Void> deletePost(@PathVariable(value = "id") Long id) {
        return postRepository.deleteById(id);
    }

    @GetMapping(value = "/update/{id}")
    public Mono<Post> updatePost(@PathVariable(value = "id") Long id) {
        return postRepository.findById(id).map(post -> {
            post.setTitle("test_" + id + " update title");
            post.setContent("test_" + id + " update content");
            return post;
        }).flatMap(post -> postRepository.save(post));
    }

    @GetMapping(value = "/query/update/{id}")
    public Mono<Void> updatePost(@PathVariable(value = "id") Long id,
                                 @RequestParam(value = "title") String updateTitle) {
        return postRepository.updateTitleById(updateTitle, id);
    }

    @GetMapping(value = "/query/do-sleep/{sleep}")
    public Mono<Void> doSleep(@PathVariable(value = "sleep") String sleep) {
        return postRepository.doSleep(Integer.parseInt(sleep));
    }

}
