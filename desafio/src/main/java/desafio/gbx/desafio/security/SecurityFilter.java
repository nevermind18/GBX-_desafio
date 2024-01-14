package desafio.gbx.desafio.security;import desafio.gbx.desafio.repository.UsuarioRepository;import desafio.gbx.desafio.service.TokenService;import jakarta.servlet.FilterChain;import jakarta.servlet.ServletException;import jakarta.servlet.http.HttpServletRequest;import jakarta.servlet.http.HttpServletResponse;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;import org.springframework.security.core.context.SecurityContextHolder;import org.springframework.stereotype.Component;import org.springframework.web.filter.OncePerRequestFilter;import java.io.IOException;@Componentpublic class SecurityFilter extends OncePerRequestFilter {    @Autowired    private TokenService tokenService;    @Autowired    private UsuarioRepository usuarioRepository;    @Override    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {        var tokenJWT = recuperarToken(request);        if(tokenJWT != null) {            var subject = tokenService.getSubject(tokenJWT);            var usuario = usuarioRepository.findByNome(subject);            var authorizarion = new UsernamePasswordAuthenticationToken(usuario,null, usuario.getAuthorities());            SecurityContextHolder.getContext().setAuthentication(authorizarion);        }        filterChain.doFilter(request,response);    }    private String recuperarToken(HttpServletRequest request) {        var authorizarionHeader = request.getHeader("Authorization");        if(authorizarionHeader != null) {            return authorizarionHeader.replace("Bearer ", "");        }        return null;    }}