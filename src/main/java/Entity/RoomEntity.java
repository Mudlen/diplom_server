package Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "room", schema = "chat")
public class RoomEntity {
    private int id;
    private String name;
    private int adminId;
    private int isPublic;



    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "admin_id", nullable = false)
    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    @Basic
    @Column(name = "isPublic", nullable = false)
    public int getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(int isPublic) {
        this.isPublic = isPublic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomEntity that = (RoomEntity) o;
        return id == that.id && adminId == that.adminId && isPublic == that.isPublic && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, adminId, isPublic);
    }
    @Override
    public String toString() {
        return "RoomEntity{" +
                "id=" + id +
                ", name='" + name +
                ", adminId=" + adminId +
                ", isPublic=" + isPublic +
                '}';
    }
}
