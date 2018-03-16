package jpa.cache.example;

import javax.persistence.*;

@Entity
@Cacheable
@Table(name = "cache_test")
public class CacheTest {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    public CacheTest() {
    }

    public CacheTest(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CacheTest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
