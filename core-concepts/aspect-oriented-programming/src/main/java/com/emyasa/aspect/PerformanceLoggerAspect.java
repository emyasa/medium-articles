package com.emyasa.aspect;

import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceLoggerAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(PerformanceLoggerAspect.class);

    @Around("execution(public * com.emyasa..*(..)) && @target(PerformanceLogger)")
    public void logPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        joinPoint.proceed();
        long timeTakenInMilliseconds = System.currentTimeMillis() - startTime;

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Class<?> declaringClass =  methodSignature.getMethod().getDeclaringClass();
        PerformanceLogger logger = declaringClass.getAnnotation(PerformanceLogger.class);

        BiFunction<Long, String, String> logMessageFormatter = ((timeInMilliseconds, unit) -> String.format("took %d %s", timeInMilliseconds, unit));
        final String logMessage;
        switch (logger.timeUnit()) {
            case MILLISECONDS:
                logMessage = logMessageFormatter.apply(timeTakenInMilliseconds, "ms");
                break;
            case SECONDS:
                logMessage = logMessageFormatter.apply(TimeUnit.MICROSECONDS.toSeconds(timeTakenInMilliseconds), "s");
                break;
            default:
                throw new UnsupportedOperationException("timeUnit unsupported");
        }

        LOGGER.info(logMessage);
    }

}
