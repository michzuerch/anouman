package ch.internettechnik.anouman.backend.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by michzuerch on 04.02.16.
 */

@Entity
@NamedQueries({
        @NamedQuery(name = "UzerRole.findAll", query = "SELECT u FROM UzerRole u"),
        @NamedQuery(name = "UzerRole.findById", query = "SELECT u FROM UzerRole u where u.id = :id"),
        @NamedQuery(name = "UzerRole.findByRole", query = "SELECT u FROM UzerRole u where u.role = :role"),
})
public class UzerRole extends AbstractEntity {
    //@todo unique entfernt für Testdaten
    //@Column(unique = true)
    @NotNull
    @NotEmpty
    private String role;

    @Column
    private String roleGroup;

    @ManyToMany(cascade =
            {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "uzer_role",
            joinColumns = {
                    @JoinColumn(
                            name = "roleid",
                            referencedColumnName = "id"
                    )
            },
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "uzerid",
                            referencedColumnName = "id"
                    )
            }
    )
    private Set<Uzer> uzers = new HashSet<Uzer>();

    @Transient
    public int getAnzahlUzers() {
        int t = getUzers().size();
        return t;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRoleGroup() {
        return roleGroup;
    }

    public void setRoleGroup(String roleGroup) {
        this.roleGroup = roleGroup;
    }

    public Set<Uzer> getUzers() {
        return uzers;
    }

    public void setUzers(Set<Uzer> uzers) {
        this.uzers = uzers;
    }
}


