package metaroa.traumimages;

import metaroa.traumimages.Config.JWTAuthorizationFilter;
import metaroa.traumimages.Roles.Roles;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SpringBootApplication
public class TraumImagesApplication {

	public static void main(String[] args) {
		SpringApplication.run(TraumImagesApplication.class, args);
	}
	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter{
		@Override
		protected void configure(HttpSecurity http) throws Exception{
			http.csrf().disable()
					.addFilterAfter(new JWTAuthorizationFilter(),UsernamePasswordAuthenticationFilter.class)
					.authorizeRequests()
					.antMatchers(HttpMethod.POST,"/login").permitAll()
					.antMatchers(HttpMethod.POST,"/register").permitAll()
					.antMatchers(HttpMethod.POST,"/confirm").permitAll()
					.antMatchers(HttpMethod.POST,"/logUserOut").hasAnyAuthority("OrdinaryUser","Financial","SuperAdmin")
					.antMatchers(HttpMethod.GET,"/listPlans").permitAll()
					.antMatchers(HttpMethod.PUT,"/modifyPlan/*").hasAnyAuthority("Financial","SuperAdmin")
					.antMatchers(HttpMethod.POST, "/newPlan").hasAnyAuthority("Financial","SuperAdmin")
					.antMatchers(HttpMethod.DELETE,"/deletePlan/*").hasAnyAuthority("Financial","SuperAdmin")
					.antMatchers(HttpMethod.POST,"/assignRole").hasAnyAuthority("SuperAdmin")
					.antMatchers(HttpMethod.POST,"/placeOrder").hasAnyAuthority("OrdinaryUser");
		}
	}
}
