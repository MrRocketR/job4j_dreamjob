package ru.job4j.dreamjob.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Candidate {
    private int id;
    private String name;
    private String description;
    private LocalDateTime created;
    private City city =  new City(0, "Не указан");;
    private byte[] photo;


    public Candidate() {

    }

    public Candidate(int id, String name, String description, LocalDateTime created) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created = created;
    }



    public Candidate(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Candidate(int id, String name) {
        this.id = id;
        this.name = name;

    }

    public Candidate(int id, String name, String description, LocalDateTime created, City city, byte[] photo) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created = created;
        this.city = city;
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Candidate candidate = (Candidate) o;
        return id == candidate.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }


    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
