package com.example.FaturamentoFrete.config;

// Indica que essa classe é de configuração do Spring
import org.springframework.context.annotation.Configuration;

// Interfaces para configurar MVC (rotas, CORS, etc)
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Marca essa classe como configuração global
@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Método chamado automaticamente pelo Spring
    // aqui configuramos o CORS da aplicação inteira
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry
                // Aplica o CORS para TODAS as rotas do backend
                .addMapping("/**")

                // Autoriza chamadas vindas do front Vite
                .allowedOrigins("http://localhost:5173")

                // Métodos HTTP permitidos
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")

                // Autoriza qualquer header (Content-Type, Authorization, etc)
                .allowedHeaders("*")

                // Não usa cookies/autenticação por sessão
                .allowCredentials(false);
    }
}
