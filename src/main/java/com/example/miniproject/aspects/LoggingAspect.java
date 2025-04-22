package com.example.miniproject.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Pointcut pour les méthodes des services et contrôleurs
    @Around("execution(* com.example.miniproject.Services..*(..)) || execution(* com.example.miniproject.Controles..*(..))")

    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        String username = getCurrentUsername();

        // Journalisation avant exécution
        logger.info("Utilisateur: {} | Appel de {}.{} avec arguments: {}",
                username != null ? username : "Anonyme", className, methodName, Arrays.toString(args));

        long startTime = System.nanoTime();
        try {
            // Exécuter la méthode cible
            Object result = joinPoint.proceed();
            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1_000_000; // en millisecondes

            // Journalisation après exécution réussie
            logger.info("Utilisateur: {} | {}.{} exécuté en {} ms avec résultat: {}",
                    username != null ? username : "Anonyme", className, methodName, duration, result);
            return result;
        } catch (Throwable t) {
            // Journalisation en cas d'erreur
            logger.error("Utilisateur: {} | Erreur dans {}.{}: {}",
                    username != null ? username : "Anonyme", className, methodName, t.getMessage(), t);
            throw t;
        }
    }

    private String getCurrentUsername() {
        try {
            return SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (Exception e) {
            return null;
        }
    }
}