package org.xiaoxianyu.common.log.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.xiaoxianyu.common.log.annotation.AuditLog;
import org.xiaoxianyu.common.log.model.Audit;
import org.xiaoxianyu.common.log.properties.AuditLogProperties;
import org.xiaoxianyu.common.log.service.IAuditService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * 审计日志切面
 *
 * @author rorschach
 * @date 2021/9/7 10:53
 */
@Slf4j
@Aspect
@ConditionalOnClass({HttpServletRequest.class, RequestContextHolder.class })
public class AuditLogAspect {

    /**
     * 应用名称
     */
    @Value("${spring.application.name}")
    private String applicationName;

    private final AuditLogProperties auditLogProperties;
    private final IAuditService auditService;

    public AuditLogAspect(AuditLogProperties auditLogProperties, IAuditService auditService) {
        this.auditLogProperties = auditLogProperties;
        this.auditService = auditService;
    }

    /**
     * 用于SpEL表达式解析.
     */
    private final SpelExpressionParser spelExpressionParser = new SpelExpressionParser();

    /**
     * 用于获取方法参数定义名字.
     */
    private final DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();

    /**
     * 前置拦截
     * @param joinPoint {@link JoinPoint}
     * @param auditLog {@link AuditLog}
     */
    @Before("@within(auditLog) || @annotation(auditLog)")
    public void beforeMethod(JoinPoint joinPoint, AuditLog auditLog) {
        //判断功能是否开启
        if (auditLogProperties.getEnabled()) {
            if (null == auditService) {
                log.warn("AuditLogAspect - auditService is null");
                return;
            }

            if (null == auditLog) {
                // 获取类上的注解
                auditLog = joinPoint.getTarget().getClass().getDeclaredAnnotation(AuditLog.class);
            }

            Audit audit = getAudit(auditLog, joinPoint);
            auditService.save(audit);
        }
    }

    private static final String OPERATOR = "#";

    /**
     * 构建审计对象
     *
     * @param auditLog {@link AuditLog}
     * @param joinPoint {@link JoinPoint}
     * @return {@link Audit}
     */
    private Audit getAudit(AuditLog auditLog, JoinPoint joinPoint) {
        Audit audit = new Audit();
        audit.setTimestamp(LocalDateTime.now());
        audit.setApplicationName(applicationName);

        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        audit.setClassName(methodSignature.getDeclaringTypeName());
        audit.setMethodName(methodSignature.getName());

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null != attributes) {
            HttpServletRequest request = attributes.getRequest();
            String userId = request.getHeader("x-userid-header");
            String userName = request.getHeader("x-user-header");
            String clientId = request.getHeader("x-tenant-header");
            audit.setUserId(userId);
            audit.setUserName(userName);
            audit.setClientId(clientId);
        }

        String operation = auditLog.operation();
        if (operation.contains(OPERATOR)) {
            //获取方法参数值
            Object[] args = joinPoint.getArgs();
            operation = getValBySpEl(operation, methodSignature, args);
        }

        audit.setOperation(operation);
        return audit;
    }

    /**
     * 解析spEL表达式
     *
     * @param spEl Spring EL表达式
     * @param methodSignature {@link MethodSignature}
     * @param args {@link Object[]}
     * @return 解析值
     */
    private String getValBySpEl(String spEl, MethodSignature methodSignature, Object[] args) {
        //获取方法形参名数组
        String[] paramNames = nameDiscoverer.getParameterNames(methodSignature.getMethod());
        if (null != paramNames && paramNames.length > 0) {
            Expression expression = spelExpressionParser.parseExpression(spEl);
            // spring的表达式上下文对象
            EvaluationContext context = new StandardEvaluationContext();

            // 给上下文赋值
            for(int i = 0; i < args.length; i++) {
                context.setVariable(paramNames[i], args[i]);
            }

            Object obj = expression.getValue(context);
            if (null != obj) {
                return obj.toString();
            }
        }
        return null;
    }
}
