package com.leass.leass.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "role")
public class Role  implements Comparable<Role>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private int id;
    @Column(name="role")
    private String role;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    @Override
    public int compareTo(Role o) {
        if (o != null) {
            return getRole().compareToIgnoreCase(o.getRole());
        } else {
            return 0;
        }
    }

    public boolean isSelected(Long clientId){
        if (clientId != null) {
            return clientId.equals(id);
        }
        return false;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    @Override
    public String toString() {
        return role;
    }

}
