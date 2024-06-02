package com.tony.fast.architecture.aspect;

import com.alibaba.fastjson.JSON;
import com.tony.fast.architecture.context.UserContextHolder;
import com.tony.fast.architecture.model.AccessLog;
import com.tony.fast.architecture.model.UserInfo;
import com.tony.fast.architecture.utils.HttpContextUtils;
import com.tony.fast.architecture.utils.IPUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@Slf4j
@Aspect
@Component
public class AccessLogAspect {
	@Pointcut("execution(* com.tony.fast.architecture.controller..*.*(..))")
	public void includePointCut() {
		
	}

	@Pointcut("execution(* com.tony.fast.architecture.controller.IndexController.*(..))")
	public void excludePointCut() {

	}

	@Around("includePointCut() && !excludePointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		//执行方法
		Object result = point.proceed();
		//执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;

		//保存日志
		saveSysLog(point, time);

		return result;
	}

	private void saveSysLog(ProceedingJoinPoint joinPoint, long startTime) {
		//获取request
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		// 跳过文件上传接口
		if(ServletFileUpload.isMultipartContent(request)) {
			return;
		}

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();

		AccessLog accessLog=new AccessLog();
		//请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		accessLog.setMethod(className + "." + methodName + "()");

		//请求的参数
		Object[] args = joinPoint.getArgs();
		if (args != null && args.length > 0) {
			try{
				String params = JSON.toJSONString(args[0]);
				accessLog.setParams(params);
			}catch (Exception e){
				log.error("params to json error: ", e);
			}
		}

		//设置IP地址
		accessLog.setIp(IPUtils.getIpAddr(request));

		//用户
		UserInfo user = UserContextHolder.getUser();
		if(user != null) {
			accessLog.setAccessUser(user.getUserId());
		}
		accessLog.setExecuteTime(new Date(startTime));
		accessLog.setCreateTime(new Date(System.currentTimeMillis()));
		log.info("接口访问日志 => {}", JSON.toJSONString(accessLog));
	}
}
