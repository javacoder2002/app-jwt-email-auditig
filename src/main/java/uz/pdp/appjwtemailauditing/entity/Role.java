package uz.pdp.appjwtemailauditing.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import uz.pdp.appjwtemailauditing.entity.enums.RoleName;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role implements GrantedAuthority { //User entity sida getAuthorities() methodi roles ni qaytarib bilshi uchun Role class GrantedAuthority tipida bolishi kerak

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //rollar soni chegaralangan shuning uchun integer
    private Integer id;

    @Enumerated(EnumType.STRING) //name bazaga o'zining nomi bilan saqlanadi
    private RoleName roleName;

    @Override
    public String getAuthority() {
        return roleName.name(); //roleName RoleName toifasida - ROLE_ADMIN. Uning string toifasi kerak - "ROLE_NAME"
    }
}
