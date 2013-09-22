package com.apeironsol.need.util.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.ConstructorSignature;
import org.aspectj.lang.reflect.MethodSignature;

import com.apeironsol.need.core.portal.messages.BusinessMessages;
import com.apeironsol.need.util.NonNull;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Non null aspect.Checks if method or constructor parameter is not null if it
 * is annotated with @NonNull.
 * 
 * @author Pradeep
 */
@Aspect
public final class NonNullAssertingAspect {

	@Before("execution(* *(.., @NonNull (*), ..))")
	public void enforceNonNullArgumentsOnMethod(final JoinPoint joinPoint) {
		final Method interfaceMethod = getInterfaceMethod(joinPoint);
		final Method implementationMethod = getImplementationMethod(joinPoint, interfaceMethod);

		enforceNonNullArguments(joinPoint, interfaceMethod, implementationMethod);
	}

	@Before("execution(*.new(.., @NonNull (*), ..))")
	public void enforceNonNullArgumentsOnConstructor(final JoinPoint joinPoint) {
		enforceNonNullArguments(joinPoint, getConstructor(joinPoint));
	}

	private void enforceNonNullArguments(final JoinPoint joinPoint, final Constructor<?> constructor) {
		final Annotation[][] annotationParameters = constructor.getParameterAnnotations();
		for (int i = 0; i < annotationParameters.length; i++) {
			for (final Annotation annotation : annotationParameters[i]) {
				if (NonNull.class.equals(annotation.annotationType()) && null == joinPoint.getArgs()[i]) {
					throwException(constructor.toGenericString(), i, constructor.getParameterTypes()[i]);
				}
			}
		}
	}

	private void enforceNonNullArguments(final JoinPoint joinPoint, final Method interfaceMethod,
			final Method implementationMethod) {

		final Annotation[][] annotationParameters = implementationMethod.getParameterAnnotations();
		for (int i = 0; i < annotationParameters.length; i++) {
			for (final Annotation annotation : annotationParameters[i]) {
				if (NonNull.class.equals(annotation.annotationType()) && null == joinPoint.getArgs()[i]) {
					throwException(implementationMethod.toGenericString(), i, interfaceMethod.getParameterTypes()[i]);
				}
			}
		}
	}

	private void throwException(final String methodOrConstructorName, final int parameterPosition,
			final Class<?> parameterType) {

		throw new BusinessException(BusinessMessages.getResourceBundleName(),
				BusinessMessages.NON_NULL_ARGUMENT_CONTAINS_NULL, new Object[] { methodOrConstructorName,
						parameterPosition + 1, parameterType });
	}

	private Method getInterfaceMethod(final JoinPoint joinPoint) {
		return ((MethodSignature) joinPoint.getSignature()).getMethod();
	}

	private Constructor<?> getConstructor(final JoinPoint joinPoint) {
		return ((ConstructorSignature) joinPoint.getSignature()).getConstructor();
	}

	@SuppressWarnings("unchecked")
	private Method getImplementationMethod(final JoinPoint joinPoint, final Method interfaceMethod) {
		Method result = null;

		try {
			result = joinPoint.getSignature().getDeclaringType()
					.getDeclaredMethod(interfaceMethod.getName(), interfaceMethod.getParameterTypes());
		} catch (final SecurityException exception) {
			throw new BusinessException(exception);
		} catch (final NoSuchMethodException exception) {
			throw new BusinessException(exception);
		}

		return result;
	}
}
