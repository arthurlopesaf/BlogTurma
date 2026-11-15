package services;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

public class SenhaService {

    Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(
            16, 32, 1, 16384, 2);

    public String GerarHash(String senhaRecebida) {

        return encoder.encode(senhaRecebida);
    }

    public boolean Verificar(String senhaDigitada, String senhaHash) {

        return encoder.matches(senhaDigitada, senhaHash);
    }
}
