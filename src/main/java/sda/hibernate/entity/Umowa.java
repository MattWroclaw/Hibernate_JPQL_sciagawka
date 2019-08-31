package sda.hibernate.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "umowa")
public class Umowa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String tytul;
    @Column
    private Date dataPodpisania;

    @ManyToOne
    @JoinColumn(name = "klientID")
    private Klient klient;

    public Umowa() {
    }

    public Umowa(String tytul, Date dataPodpisania, Klient klient) {
        this.tytul = tytul;
        this.dataPodpisania = dataPodpisania;
        this.klient = klient;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTytul() {
        return tytul;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    public Date getDataPodpisania() {
        return dataPodpisania;
    }

    public void setDataPodpisania(Date dataPodpisania) {
        this.dataPodpisania = dataPodpisania;
    }

    public Klient getKlient() {
        return klient;
    }

    public void setKlient(Klient klient) {
        this.klient = klient;
    }

    @Override
    public String toString() {
        return "Umowa{" +
                "id=" + id +
                ", tytul='" + tytul + '\'' +
                ", dataPodpisania=" + dataPodpisania +
                ", klient=" + klient +
                '}';
    }
}
