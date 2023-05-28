package io.whatap.test.app.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
public class UserResource {

    private final UserRepository userRepository;

    public UserResource(UserRepository postRepository) {
        this.userRepository = postRepository;
    }

    @GetMapping
    public Flux<User> getPosts() {
        return userRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Mono<?> findById(@PathVariable(value = "id") Long id) {
        return userRepository.findById(id);
    }

    @GetMapping(value = "/save-one")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<?> savePost() {
        User user = new User();
        user.setName("author");
        user.setAge(25);
        user.setOrd(5.5F);
        user.setInfo("qazwsxedcrfvtgbyhnujmikqazwsxedcrfvtgbyhnujmikqazwsxedcrfvtgbyhnujmikqazwsxedcrfvtgbyhnujmikqazwsxedcrfvtgbyhnujmikqazwsxedcrfvtgbyhnujmikqazwsxedcrfvtgbyhnujmik");
        user.setCreatedAt(LocalDateTime.now());

        return Mono.just(userRepository.save(user).subscribe());
    }

    @GetMapping(value = "/save-all")
    @ResponseStatus(HttpStatus.CREATED)
    public Flux<User> savePosts() {
        List<User> posts = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            User user = new User();
            user.setName("author_" + (i + 1));
            user.setAge((i + 1));
            user.setOrd((i + 0.1F));
            user.setInfo("test-content_" + (i + 1));
            posts.add(user);
        }
        return userRepository.saveAll(posts);
    }

    @GetMapping(value = "/delete/{id}")
    public Mono<Void> deleteUser(@PathVariable(value = "id") Long id) {
        return userRepository.deleteById(id);
    }

    @GetMapping(value = "/update/{id}")
    public Mono<User> updateUser(@PathVariable(value = "id") Long id) {
        System.out.println("/update/" + id);
        String strVal = String.valueOf(id);
        return userRepository.findById(id).map(user -> {
            user.setName("test_" + strVal + " update name");
            int currentAge = user.getAge();
            user.setAge(currentAge + 5);
            return user;
        }).flatMap(user -> userRepository.save(user));
    }

}
