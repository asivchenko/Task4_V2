package org.example.data.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "logins")
public class Logins {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "access_date")
    private Timestamp accessDate;
    @Column(name = "name_app")
    private String  name_app;

    public String getName_app() {
        return name_app;
    }

    public void setName_app(String name_app) {
        this.name_app = name_app;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;
    public void setAccessDate(Timestamp accessDate) {
        this.accessDate = accessDate;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}