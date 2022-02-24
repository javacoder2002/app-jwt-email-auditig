package uz.pdp.appjwtemailauditing.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User implements UserDetails { //bu userni springning useri sifatida olish uchun UserDetails dan implement olsih kerak

    @Id
    @GeneratedValue
    private UUID id; //userning takrorlanmas qismi

    //@Size(min = 5, max = 50) //database ga aloqasi yoq bolgan cheklov. Javanig  o'zida turib ishlaydi
    @Column(nullable = false, length = 50)
    private String firstName; //ism .

    //@Length(min = 5, max = 50) //size bilan birxil, kutubxonasi boshqa
    @Column(nullable = false)
    private String lastName; //familiya

    //@Email // example@gmail.com - shunday korinishlarni tamilab beradi
    @Column(nullable = false, unique = true)
    private String email; //userning emaili

    @Column(nullable = false)
    private String password; //systemaga kirish uchun parol

    @CreationTimestamp //user qachon yaratilsa o'sha vaqtni oladi
    @Column(nullable = false, updatable = false) //updatable = false - admin o'zgartira olmasligi uchun
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(nullable = false, updatable = false) // updatable = false - admin o'zgartira olmasligi uchun
    private Timestamp updatedAt; // user qachon ohirgi marta update qilgan vaqti

    @Column(nullable = false)
    private boolean active;

    @ManyToMany //koplab rollarga koplab userlar
    private Set<Role> roles;

    @Column(nullable = false)
    private boolean accountNonExpired = true; //DEFAULT user ro'yxatdan otganda true - muddati o'tmagan

    @Column(nullable = false)
    private boolean accountNonLocked = true; //DEFAULT ro'yxatdan o'tganda bloklanmagan

    @Column(nullable = false)
    private boolean credentialsNonExpired = true; //DEFAULT account ishonchliligining muddati otmagan

    @Column(nullable = false)
    private boolean enabled; //DEFAUL yoniq emas , tasdiqlansa true bo'ladi

    @Column(nullable = false)
    private String emailCode;

    /**
     * ### _ These are methods of UserDetails _ ###
     */

    //userning huquqlari ro'yxati
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    //userning username
    @Override
    public String getUsername() {
        return this.email;
    }

    /*
    * getPassword() - method ham bor. password va @Data bolganligi uchun u bizga korinmayapti
    * */

    /*
     * Account ning muddati o'tmaganmi?
     * */
    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired; //ha o'tmagan
    }

    /*
    * Account bloklanmaganmi?
    * */
    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked; //ha bloklanmagan
    }

    /*
    * accountning ishonchliligining muddati o'tmaganmi?
    * */
    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    /*
    * yoniq accountmi , sistemaga kiraoladimi
    * */
    @Override
    public boolean isEnabled() {
        return this.enabled; //ha
    }
}

/**
 * @Size , @Length , @Email annatatsiyalari User clasi  uchun emas,
 * RegisterDto uchun kerak, BU yerga shart emas
 */