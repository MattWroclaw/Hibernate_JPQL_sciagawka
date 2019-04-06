package sda.hibernate.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "klientumowa")
public class Klient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String imie;
    @Column
    private String nazwisko;

    @OneToMany(mappedBy = "klient")
    private Set<Umowa> umowy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public Set<Umowa> getUmowy() {
        if (umowy == null) {
            umowy = new HashSet<Umowa>();
        }
        return umowy;
    }

    public void setUmowy(Set<Umowa> umowy) {
        this.umowy = umowy;
    }

    public Klient(String imie, String nazwisko) {
        this.imie = imie;
        this.nazwisko = nazwisko;
    }

    public Klient() {
    }
}