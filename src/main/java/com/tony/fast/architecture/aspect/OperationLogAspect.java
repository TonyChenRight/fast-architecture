package com.tony.fast.architecture.aspect;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONObject;
import com.tony.fast.architecture.annoation.OperLog;
import com.tony.fast.architecture.context.OperationLogContextHolder;
import com.tony.fast.architecture.context.UserContextHolder;
import com.tony.fast.architecture.domain.OperationLog;
import com.tony.fast.architecture.enums.OperationModule;
import com.tony.fast.architecture.enums.OperationType;
import com.tony.fast.architecture.model.OperationLogContext;
import com.tony.fast.architecture.model.R;
import com.tony.fast.architecture.model.UserInfo;
import com.tony.fast.architecture.service.OperationLogService;
import com.tony.fast.architecture.utils.HttpContextUtils;
import com.tony.fast.architecture.utils.IPUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Slf4j
@Aspect
@Component
public class OperationLogAspect {
	@Resource
	private OperationLogService operationLogService;
	@Pointcut("@annotation(com.tony.fast.architecture.annoation.OperLog)")
	public void includePointCut() {
		
	}

	@Around("includePointCut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		try {
			MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
			OperLog operLog = methodSignature.getMethod().getAnnotation(OperLog.class);
			HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
			String ipAddr = IPUtils.getIpAddr(request);
			String params = getParams(joinPoint, methodSignature);
			UserInfo user = UserContextHolder.getUser();

			Object result = joinPoint.proceed();

			OperationLogContext context = OperationLogContextHolder.get();
			if (context.getModule() == null && operLog.module() != OperationModule.NONE) {
				context.setModule(operLog.module());
			}
			if (context.getType() == null && operLog.type() != OperationType.NONE) {
				context.setType(operLog.type());
			}
			// targetId 需要填充
			if (CollUtil.isEmpty(context.getTargetIds())) {
				if (result instanceof R responseData) {
					if (!responseData.isSuccess()) {
						return responseData;
					}
					setTargetIdByData(responseData, context);
				}
			}
			List<OperationLog> logs = OperationLog.buildByCreate(context, user, ipAddr, params);
			operationLogService.saveOperationLogs(logs);
			return result;
		}finally {
			OperationLogContextHolder.clear();
		}
	}

	private void setTargetIdByData(Object data, OperationLogContext context) {
		if (data == null) {
			return;
		}
		if (data instanceof List dataIds) {
			for (Object dataId : dataIds) {
				context.addTargetId(dataId.toString());
			}
		}else {
			context.addTargetId(data.toString());
		}
	}
	private String getParams(ProceedingJoinPoint joinPoint, MethodSignature signature) {
		JSONObject json =new JSONObject();
		String[] paramNames = signature.getParameterNames();
		Object[] paramValues = joinPoint.getArgs();
		int paramLength = paramValues == null ? 0 : paramNames.length;
		if (paramLength == 0) {
			return json.toJSONString();
		}

		for (int i = 0; i < paramLength; i++) {
			json.put(paramNames[i], paramValues[i]);
		}
		return json.toJSONString();
	}
}
