package sda.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import sda.hibernate.entity.Klient;
import sda.hibernate.entity.Umowa;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class przyklad1 {
    public static void main(String[] args) {
        insertData();
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Klient> klienci = new ArrayList<>();
        List<Umowa> umowy = new ArrayList<>();
        //odpowiednik zapytania SELECT * FROM klient
        klienci = session.createQuery("FROM Klient", Klient.class).getResultList();
        klienci = session.createQuery("SELECT k FROM Klient k", Klient.class).getResultList();
        //klauzula WHERE
        klienci = session.createQuery("SELECT k FROM Klient k WHERE k.imie in ('Jan','Robert')", Klient.class).getResultList();
        klienci = session.createQuery("SELECT k FROM Klient k WHERE k.id between 1 and 10", Klient.class).getResultList();
        klienci = session.createQuery("SELECT k FROM Klient k WHERE k.nazwisko is null", Klient.class).getResultList();
        klienci = session.createQuery("SELECT k FROM Klient k WHERE k.nazwisko is not null", Klient.class).getResultList();
        klienci = session.createQuery("SELECT k FROM Klient k WHERE k.nazwisko LIKE 'A'", Klient.class).getResultList();
        klienci = session.createQuery("SELECT k FROM Klient k WHERE k.nazwisko NOT LIKE 'R'", Klient.class).getResultList();
        klienci = session.createQuery("SELECT k FROM Klient k WHERE k.imie LIKE '_la'", Klient.class).getResultList();
        klienci = session.createQuery("SELECT k FROM Klient k WHERE k.imie ='Ola' or k.imie='Ala'", Klient.class).getResultList();
        klienci = session.createQuery("SELECT k FROM Klient k WHERE k.umowy.size>10", Klient.class).getResultList();

        //select z warunkiem z podklasy
        umowy = session.createQuery("SELECT u FROM Umowa u WHERE u.klient.imie='Ala'", Umowa.class).getResultList();
        //select z konkretnymi kolumnami SELECT imie, nazwisko FROM klient
        Object klienciImieNazwisko = session.createQuery("SELECT k.imie, k.nazwisko FROM Klient k").getResultList();


        //funkcje agregujace
        //Count zwraca liczbe wierszy
        Long count = session.createQuery("SELECT COUNT(k) FROM Klient k", Long.class).getSingleResult();
        //Count zwraca maksymala liczbe
        Long maxIdUmowy = session.createQuery("SELECT MAX(u.id) FROM Umowa u", Long.class).getSingleResult();
        //Count zwraca minimalna liczbe
        Long minIdUmowy = session.createQuery("SELECT MIN(u.id) FROM Umowa u", Long.class).getSingleResult();
        //Count zwraca srednia
        Double avg = session.createQuery("SELECT AVG(k.id) FROM Klient k", Double.class).getSingleResult();

        //laczenie JOIN
        klienci = session.createQuery("SELECT k FROM Klient k JOIN FETCH k.umowy u ", Klient.class).getResultList();
        klienci = session.createQuery("SELECT DISTINCT k FROM Klient k JOIN FETCH k.umowy u ", Klient.class).getResultList();
        klienci = session.createQuery("SELECT k FROM Klient k JOIN  k.umowy u ", Klient.class).getResultList();

        //ustawianie maksymalnej liczby rekordow
        klienci = session.createQuery("SELECT k FROM Klient k", Klient.class).setMaxResults(5).getResultList();

        //ustawianie parametrow
        klienci = session.createQuery("SELECT k FROM Klient k WHERE k.imie=:imie", Klient.class).setParameter("imie", "Adam").getResultList();
        session.close();
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = session.beginTransaction();
        //update z parametrami
        session.createQuery("UPDATE Umowa u SET u.tytul=:tytul WHERE u.id=:id").setParameter("tytul", "nowyTytul").setParameter("id", 3L).executeUpdate();
        tr.commit();
        session.close();
    }

    public static void insertData() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        String imiona[] = {"Adam", "Ewa", "Stanislaw", "Szymon", "Ola", "Ala", "Robert", "Marcin", "Karolina", "Sylwia", "Ewelina", "Kamil", "Sandra", "Wojtek", "Katarzyna", "Slawek", "Kamila"};
        String nazwiska[] = {"Nowak", "Kowalski", "Mickiewicz", "Slowacki", "Duda", "Trampek", "Wilk", "Szybki", "Wesoly", "Smutny", "Mily", "Nowy", "Maly", null, null, null, null};

        for (int i = 0; i < imiona.length; i++) {
            Klient klinet = new Klient(imiona[i], nazwiska[i]);
            session.persist(klinet);
            for (int j = 1; j < i+2; j++) {
                Umowa umowa = new Umowa("umowa" + j, Date.valueOf(LocalDate.now()), klinet);
                session.persist(umowa);
            }
        }
        session.flush();
        session.close();

    }
}
