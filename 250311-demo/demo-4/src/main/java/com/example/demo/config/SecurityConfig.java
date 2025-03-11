package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	//css주소 설정
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
	return (web)-> 
	web.ignoring().requestMatchers("/webjars/**","/css/**","/js/**");
	}
	//설정 후에 반드시 재시작
	
	/*
	 private final AuthenticationFailureHandler customAuthenticationFailureHandler;

	    // CustomAuthenticationFailureHandler 의존성 주입
	    public SecurityConfig(AuthenticationFailureHandler customAuthenticationFailureHandler) {
	        this.customAuthenticationFailureHandler = customAuthenticationFailureHandler;
	    }
	    */  
@Bean
public PasswordEncoder passwordEncoder() {
      // 암호화 없이 처리
      //return NoOpPasswordEncoder.getInstance();
	
	  //암호화일 경우 
		return new BCryptPasswordEncoder();
}
	
@Bean
SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//사이트 접속시마다 접속하면 및 제한이 되어 있는 부분을 해제
// http.authorizeHttpRequests();
/*
http.authorizeHttpRequests(
(authorizeHttpRequests)->
authorizeHttpRequests.requestMatchers(
new AntPathRequestMatcher("/**")).permitAll()
).csrf((csrf)->csrf.ignoringRequestMatchers(
new AntPathRequestMatcher("/h2-console/**")))
.headers((header)->header.addHeaderWriter(
new XFrameOptionsHeaderWriter(
XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)));
*/
	
/*
http.authorizeHttpRequests(
		(authorizeHttpRequests)
		->authorizeHttpRequests.requestMatchers("/**").permitAll()
		).csrf((csrf)->csrf.ignoringRequestMatchers(
				new AntPathRequestMatcher("/h2-console/**"))
);
*/
//아래의 코드는 람다함수를 이용하여 처리한 코드
http.authorizeHttpRequests(
			(authorizeHttpRequests)
			->authorizeHttpRequests.requestMatchers("/**").permitAll()
			).csrf((csrf)->csrf.ignoringRequestMatchers(
					new AntPathRequestMatcher("/h2-console/**"))
			).headers((header)->header.addHeaderWriter(
					new XFrameOptionsHeaderWriter(
			XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
			).formLogin((login)->login.loginPage("/login").failureUrl("/login/fail?error=true").defaultSuccessUrl("/login/success")
			//).formLogin((login)->login.loginPage("/login").failureHandler(customAuthenticationFailureHandler).defaultSuccessUrl("/login/success")
			).logout(
					(logout)->
					logout.logoutRequestMatcher
					(new AntPathRequestMatcher("/logout", "GET"))
					.logoutSuccessUrl("/login")
					.invalidateHttpSession(true)
);	
return http.build();
}
}
/*
 * .logout()
    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
    .logoutSuccessUrl("/login?logout");
   */ 
