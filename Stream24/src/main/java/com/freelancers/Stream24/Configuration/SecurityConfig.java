package com.freelancers.Stream24.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mongodb.client.MongoClient;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth
				// Permit access to user and admin registration, login, and OTP verification
				// without authentication
				.requestMatchers("/api/upload", "/api/{id}", "/api/videos").permitAll()
				.requestMatchers("/auth/register", "/auth/login", "/auth/verify-otp", "/auth/update").permitAll()
				.requestMatchers("/admin/register", "/admin/login", "/admin/verify-otp", "/admin/update-user")
				.permitAll()
				.requestMatchers("/admin/**").permitAll()

				// Secure all other requests, ensuring authentication is required
				.anyRequest().authenticated()).formLogin(form -> form.loginPage("/login") // Custom login page
						.permitAll())
				.logout(logout -> logout.permitAll());

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:3000")
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS").allowedHeaders("*")
						.allowCredentials(true);
			}
		};

	}

	@Bean
	public GridFSBucket gridFSBucket(MongoDatabaseFactory mongoDatabaseFactory, MongoClient mongoClient) {
		return GridFSBuckets.create(mongoClient.getDatabase(mongoDatabaseFactory.getMongoDatabase().getName()));
	}



}
