package com.bkdwei.model;
// Generated 2016-8-2 7:02:44 by Hibernate Tools 5.1.0.Alpha1

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Person generated by hbm2java
 */
@Entity
@Table(name = "person", catalog = "t2")
public class Person implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3547028340155514582L;
    private Integer           age;
    private Integer           id;
    private String            name;

    public Person() {
    }

    public Person(final String name, final Integer age) {
        this.name = name;
        this.age = age;
    }

    @Column(name = "age")
    public Integer getAge() {
        return this.age;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    @Column(name = "name", length = 100)
    public String getName() {
        return this.name;
    }

    public void setAge(final Integer age) {
        this.age = age;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

}