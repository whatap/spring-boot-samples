package io.whatap.test.app.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "tb_user")
public class User {

    @Id
    private Long id;
    private String name;
    private int age;
    private String info;
    private float ord;
    private LocalDateTime createdAt;

    public User() {
    }

    public User(Long id, String title, int age, String info, float ord, LocalDateTime createdAt) {
        this.id = id;
        this.name = title;
        this.age = age;
        this.info = info;
        this.ord = ord;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public float getOrd() {
        return ord;
    }

    public void setOrd(float ord) {
        this.ord = ord;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
