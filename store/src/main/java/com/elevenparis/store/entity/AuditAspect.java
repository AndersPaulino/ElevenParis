package com.elevenparis.store.entity;

import com.elevenparis.store.service.ElasticsearchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class AuditAspect {

    private static final Logger logger = LoggerFactory.getLogger(AuditAspect.class);
    private final JwtDecoder jwtDecoder;
    private final ElasticsearchService elasticsearchService;

    @Autowired
    public AuditAspect(JwtDecoder jwtDecoder, ElasticsearchService elasticsearchService) {
        this.jwtDecoder = jwtDecoder;
        this.elasticsearchService = elasticsearchService;
    }

    @Pointcut("execution(* com.elevenparis.store.repository..*(..))")
    public void audit() {}
    @Around("audit()")
    public Object logAudit(ProceedingJoinPoint joinPoint) throws Throwable {
        // Obtém a autenticação do SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username;

        if (authentication != null && authentication.isAuthenticated()) {
            // Obtém o token JWT
            String token = ((Jwt) authentication.getPrincipal()).getTokenValue();

            // Decodifica o token e obtém o nome de usuário
            Jwt jwt = jwtDecoder.decode(token);
            username = jwt.getClaimAsString("preferred_username"); // Use "username" se necessário
        } else {
            username = "Usuário não autenticado";
        }

        String methodName = joinPoint.getSignature().getName();
        Object[] methodArgs = joinPoint.getArgs();
        String action = "Executou o método: " + methodName + " com argumentos: " + Arrays.toString(methodArgs);

        final String finalUsername = username;

        logger.info(() -> "Usuário: " + finalUsername + " executou o método: " + methodName + " com argumentos: " + Arrays.toString(methodArgs));

        // Preparar dados para enviar ao Elastic
        Map<String, Object> logData = new HashMap<>();
        logData.put("username", finalUsername);
        logData.put("method", methodName);
        logData.put("args", methodArgs);
        logData.put("action", action);

        // Indexar o log no Elasticsearch
        try {
            String jsonLogData = new ObjectMapper().writeValueAsString(logData);
            boolean success = elasticsearchService.indexDocument("logs", jsonLogData);
            if (success) {
                logger.info(() -> "Log indexado com sucesso no Elasticsearch.");
            } else {
                logger.warn(() -> "Log não pôde ser indexado no Elasticsearch.");
            }
        } catch (Exception e) {
            logger.error(() -> "Erro ao indexar log no Elasticsearch: " + e.getMessage());
        }

        Object result = joinPoint.proceed();
        logger.info(() -> "Método " + methodName + " retornou: " + result);

        return result;
    }


}

