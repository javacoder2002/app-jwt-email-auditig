package uz.pdp.appjwtemailauditing.sevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.appjwtemailauditing.entity.User;
import uz.pdp.appjwtemailauditing.entity.enums.RoleName;
import uz.pdp.appjwtemailauditing.payload.ApiResponse;
import uz.pdp.appjwtemailauditing.payload.RegisterDto;
import uz.pdp.appjwtemailauditing.repository.RoleRepository;
import uz.pdp.appjwtemailauditing.repository.UserRepository;

import java.util.Collections;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    JavaMailSender javaMailSender;

    public ApiResponse registerUser(RegisterDto registerDto) {
        boolean exists = userRepository.existsByEmail(registerDto.getEmail());
        if (exists)
            return new ApiResponse(false, "email mavjud!");

        User user = new User();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setEmail(registerDto.getEmail());
        //password encodlab saqlash kerak
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRoles(Collections.singleton(roleRepository.findByRoleName(RoleName.ROLE_USER)));
        user.setEmailCode(UUID.randomUUID().toString());
        userRepository.save(user);

        /*
        * Emailga yuborish metodi
        * */
        sendEmail(user.getEmail(), user.getEmailCode());

        return new ApiResponse(true, "Muvvaffaqiyatliq ro'yxaatdan o'tdingiz!");
    }

    //"sherzodsmmm@gmail.com"

    public Boolean sendEmail(String sendingEmail, String emailCode) { //qabul qiluvchining emaili - sendingEmail
        try {
            String href = "http://localhost:8080/api/auth/verifyEmail?emailCode=" + emailCode + "&email=" + sendingEmail;
            String text = "<a href='" + href + "'>Tasdiqlang</a>";

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("Alisher@gmail.com");
            mailMessage.setTo(sendingEmail);
            mailMessage.setSubject("Tasdiqlash");
            mailMessage.setText("");
            javaMailSender.send(mailMessage);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
