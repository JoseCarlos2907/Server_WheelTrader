package es.iesfernandoaguilar.perezgonzalez.wheeltrader.services;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Usuario;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario getById(Long id) {
        Optional<Usuario> usuarioOpt = this.usuarioRepository.findById(id);

         Usuario usuario = null;
        if(usuarioOpt.isPresent()) {
            usuario = usuarioOpt.get();
        }

        return usuario;
    }
}
