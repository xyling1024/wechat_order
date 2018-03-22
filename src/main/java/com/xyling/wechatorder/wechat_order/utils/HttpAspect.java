package com.xyling.wechatorder.wechat_order.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

/**
 * Created by: edgewalk
 * 2018-01-24 14:17
 *
 * 作用:
 *		当访问切点的方法时, 会在控制台打印出访问IP及类名等
 *
 * 使用:
 * 		将该类放在spring能够扫描到的包下
 * 		开启aop注解
 * 		更换具体业务逻辑中的切点
 *
 *  	<!-- HttpAspect -->
 *  	<context:component-scan base-package="com.edgewalk"/>
 *  	<aop:aspectj-autoproxy></aop:aspectj-autoproxy>
 */
@Aspect
@Component
//@Slf4j
public class HttpAspect {
    private static final ThreadLocal<SimpleDateFormat> sdf = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    //1定义切点
    @Pointcut("execution(public * com.xyling.wechatorder.wechat_order.controller..*.*(..))")
    public void pc() {
    }

    //2定义通知
    @Before("pc()")
    public void doBefore(JoinPoint joinPoint) {


        //1获取request
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();


        StringBuilder sb = (new StringBuilder("\nproject report -------------- ")).append(((SimpleDateFormat) sdf.get()).format(new Date())).append(" ------------------------------\n");
        sb.append("URL         : ").append(request.getRequestURL()).append("  (").append(request.getMethod()).append(")\n");
        sb.append("IP          : ").append(request.getRemoteAddr()).append("\n");
        sb.append("Controller  : ").append(joinPoint.getSignature().getDeclaringTypeName()).append("(").append(joinPoint.getSignature().getDeclaringType().getSimpleName()).append(".java:1)");
        sb.append("\nMethod      : ").append(joinPoint.getSignature().getName()).append("\n");
        if (joinPoint.getArgs().length > 0) {
            Enumeration<String> e = request.getParameterNames();
            if (e.hasMoreElements()) {
                sb.append("Parameter   : ");

                for (; e.hasMoreElements(); sb.append("  ")) {
                    String name = (String) e.nextElement();
                    String[] values = request.getParameterValues(name);
                    if (values.length == 1) {
                        sb.append(name).append("=").append(values[0]);
                    } else {
                        sb.append(name).append("[]={");

                        for (int i = 0; i < values.length; ++i) {
                            if (i > 0) {
                                sb.append(",");
                            }

                            sb.append(values[i]);
                        }

                        sb.append("}");
                    }
                }

                sb.append("\n");
            }
        }
        sb.append("--------------------------------------------------------------------------------\n");
        System.out.println(sb.toString());
    }

    /**
     * 后置通知
     */
    @After("pc()")
    public void doAfter() {
//        logger.info("doAfter ..............");
    }

    /**
     * 获取方法返回值
     *
     * @param object
     */
    @AfterReturning(returning = "object", pointcut = "pc()")
    public void doAfterReturning(Object object) {
//        if (object != null)
//            System.out.println("response    : " + object);
    }
}

