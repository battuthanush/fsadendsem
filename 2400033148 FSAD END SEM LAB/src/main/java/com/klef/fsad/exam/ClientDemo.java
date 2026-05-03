package com.klef.fsad.exam;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.Date;

public class ClientDemo {
    public static void main(String[] args) {

        SessionFactory sf = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Restaurant.class)
                .buildSessionFactory();

        Session s = sf.openSession();

        // INSERT
        Transaction t = s.beginTransaction();

        Restaurant r = new Restaurant();
        r.setName("ABC Restaurant");
        r.setStatus("Open");
        r.setDate(new Date());

        s.save(r);
        t.commit();

        // UPDATE (HQL named parameters)
        t = s.beginTransaction();

        Query q = s.createQuery(
                "update Restaurant r set r.name=:name, r.status=:status where r.id=:id"
        );

        q.setParameter("name", "Updated Restaurant");
        q.setParameter("status", "Closed");
        q.setParameter("id", r.getId());

        q.executeUpdate();
        t.commit();

        s.close();
        sf.close();
    }
}