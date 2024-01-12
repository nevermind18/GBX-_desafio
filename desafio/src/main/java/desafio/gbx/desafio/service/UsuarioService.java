package desafio.gbx.desafio.service;import desafio.gbx.desafio.entity.UsuarioEntity;import desafio.gbx.desafio.repository.UsuarioRepository;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.http.ResponseEntity;import org.springframework.stereotype.Service;import java.util.List;@Servicepublic class UsuarioService {    @Autowired    UsuarioRepository usuarioRepository;    public ResponseEntity criarUsuario(UsuarioEntity usuario) {        usuario.setNumeroConta(System.currentTimeMillis());        return ResponseEntity.status(201).body(usuarioRepository.save(usuario));    }    public ResponseEntity<List<UsuarioEntity>> listarUsuario() {        return ResponseEntity.ok(usuarioRepository.findAll());    }    public UsuarioEntity existConta(long numeroConta){        return usuarioRepository.existConta(numeroConta);    }    public void atualizaValor(long numeroConta, double valor){        var conta = existConta(numeroConta);        conta.setSaldo((valor) + conta.getSaldo());        usuarioRepository.save(conta);    }}