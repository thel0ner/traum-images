package metaroa.traumimages.Config;

import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JWTAuthorizationFilter extends OncePerRequestFilter {
    private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer ";
    private final String SECRET = "SomeKeyKavehTaheKiwyReyhanehTaher1321584554484&^^THHHHKavehtaherHomayunTaher878786757uy7ytiuyiuytwiuyiuyrgiuuowy_+";

    private Claims validateToken(HttpServletRequest request){
        String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
        return Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(jwtToken).getBody();
    }

    private void SetUpSpringAuthenication(Claims claims){
        @SuppressWarnings("unchecked")
        List<String> authorities = (List) claims.get("authorities");
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                claims.getSubject(),
                null,
                authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
                );
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private boolean checkJWTTOken(HttpServletRequest request,HttpServletResponse response){
        String authenicationHeader = request.getHeader(HEADER);
        if(authenicationHeader == null || !authenicationHeader.startsWith(PREFIX)){
            return false;
        }
        return true;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException{
        try{
            if(checkJWTTOken(request,response)){
                Claims claims = validateToken(request);
                if(claims.get("authorities") != null){
                    SetUpSpringAuthenication(claims);
                }else{
                    SecurityContextHolder.clearContext();
                }
            }else{
                SecurityContextHolder.clearContext();
            }
            chain.doFilter(request,response);
        }catch(ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN,e.getMessage());
            return;
        }
    }
}
