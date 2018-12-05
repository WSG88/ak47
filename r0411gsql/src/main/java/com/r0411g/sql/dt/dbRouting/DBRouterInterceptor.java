package com.r0411g.sql.dt.dbRouting;

import com.r0411g.sql.dt.dbRouting.annotation.Router;
import com.r0411g.sql.dt.dbRouting.annotation.RouterConstants;
import com.r0411g.sql.dt.dbRouting.router.RouterUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 切面切点 在Router注解的方法执行前执行 切点织入
 *
 */
@Aspect
@Component
public class DBRouterInterceptor {

    private static final Logger log = LoggerFactory.getLogger(DBRouterInterceptor.class);

    private DBRouter dBRouter;

    @Pointcut("@annotation( com.r0411g.sql.dt.dbRouting.annotation.Router)")
    public void aopPoint() {
    }

    @Before("aopPoint()")
    public Object doRoute(JoinPoint jp) throws Throwable {
        long t1 = System.currentTimeMillis();
        boolean result = true;
        Method method = getMethod(jp);
        Router router = method.getAnnotation(Router.class);
        String routeField = router.routerField();
        Object[] args = jp.getArgs();
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                long t2 = System.currentTimeMillis();
                String routeFieldValue = BeanUtils.getProperty(args[i],
                        routeField);
                log.debug("routeFieldValue{}" + (System.currentTimeMillis() - t2));
                if (StringUtils.isNotEmpty(routeFieldValue)) {
                    if (RouterConstants.ROUTER_FIELD_DEFAULT.equals(routeField)) {
                        dBRouter.doRouteByResource("" + RouterUtils.getResourceCode(routeFieldValue));
                        break;
                    } else {
                        this.searchParamCheck(routeFieldValue);
                        String resource = routeFieldValue.substring(routeFieldValue.length() - 4);
                        dBRouter.doRouteByResource(resource);
                        break;
                    }
                }
            }
        }
        log.debug("doRouteTime{}" + (System.currentTimeMillis() - t1));
        return result;
    }

    private Method getMethod(JoinPoint jp) throws NoSuchMethodException {
        Signature sig = jp.getSignature();
        MethodSignature msig = (MethodSignature) sig;
        return getClass(jp).getMethod(msig.getName(), msig.getParameterTypes());
    }

    private Class<? extends Object> getClass(JoinPoint jp)
            throws NoSuchMethodException {
        return jp.getTarget().getClass();
    }


    /**
     * 查询支付结构参数检查
     *
     * @param payId
     */
    private void searchParamCheck(String payId) {
        if (payId.trim().equals("")) {
            throw new IllegalArgumentException("payId is empty");
        }
    }

    public DBRouter getdBRouter() {
        return dBRouter;
    }

    public void setdBRouter(DBRouter dBRouter) {
        this.dBRouter = dBRouter;
    }

}
