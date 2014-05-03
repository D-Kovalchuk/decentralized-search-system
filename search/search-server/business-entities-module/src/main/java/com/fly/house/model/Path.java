package com.fly.house.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by dimon on 5/2/14.
 */
@Entity
public class Path extends BasedEntity {

    private String path;

    @NotNull
    @ManyToOne
    private Account account;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<File> files;

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Path path1 = (Path) o;

        if (!account.equals(path1.account)) return false;
        if (!path.equals(path1.path)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = path.hashCode();
        result = 31 * result + account.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Path{" +
                "path='" + path + '\'' +
                ", account=" + account +
                ", files=" + files +
                '}';
    }
}
