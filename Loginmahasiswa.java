/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UAS;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Salwa Faizah
 */
@Entity
@Table(name = "loginmahasiswa")
@NamedQueries({
    @NamedQuery(name = "Loginmahasiswa.findAll", query = "SELECT l FROM Loginmahasiswa l"),
    @NamedQuery(name = "Loginmahasiswa.findByUsername", query = "SELECT l FROM Loginmahasiswa l WHERE l.username = :username"),
    @NamedQuery(name = "Loginmahasiswa.findByPassword", query = "SELECT l FROM Loginmahasiswa l WHERE l.password = :password")})
public class Loginmahasiswa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;

    public Loginmahasiswa() {
    }

    public Loginmahasiswa(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Loginmahasiswa)) {
            return false;
        }
        Loginmahasiswa other = (Loginmahasiswa) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UAS.Loginmahasiswa[ username=" + username + " ]";
    }
    
}
