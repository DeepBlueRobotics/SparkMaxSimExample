package frc.robot.lib;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;
import org.mockito.MockSettings;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public final class Mocks {
    
    public static final Answer<Object> RETURN_ORIGINAL_INVOCATION = InvocationOnMock::callRealMethod;

    /**
     * Attempts to create an instance of a class in which some or all of the classes methods are replaced with a mocked implementation
     * @param T the class type which will be mocked
     * @param U the class type which will be used to provide method implementations
     * @param classToMock the class type which will be mocked
     * @param implClass the object to which to try to forward method calls
     * @return an instance of <code>T</code> in which some or all of the classes methods are replaced with a mocked implementation from <code>U</code>
     * @see #createMock(java.lang.Class, java.lang.Object, java.lang.Class...) 
     */
    public static <T, U> T createMock(Class<T> classToMock, U implClass) {
        return createMock(classToMock, implClass, new Class<?>[0]);
    }
    
    /**
     * Attempts to create an instance of a class in which some or all of the classes methods are replaced with a mocked implementation
     * @param T the class type which will be mocked
     * @param U the class type which will be used to provide method implementations
     * @param classToMock the class type which will be mocked
     * @param implClass the object to which to try to forward method calls
     * @param interfaces a list of interfaces which the mocked object should extend
     * @return an instance of <code>T</code> in which some or all of the classes methods are replaced with a mocked implementation from <code>U</code>
     * @see #createMock(java.lang.Class, java.lang.Object) 
     */
    public static <T, U> T createMock(Class<T> classToMock, U implClass, Class<?>... interfaces) {
        return createMock(classToMock, implClass, RETURN_ORIGINAL_INVOCATION);
    }

    /**
     * Attempts to create an instance of a class in which some or all of the classes methods are replaced with a mocked implementation
     * @param T the class type which will be mocked
     * @param U the class type which will be used to provide method implementations
     * @param classToMock the class type which will be mocked
     * @param implClass the object to which to try to forward method calls
     * @param defaultAnswer the answer to use if an overriden method cannot be found
     * @return an instance of <code>T</code> in which some or all of the classes methods are replaced with a mocked implementation from <code>U</code>
     * @see #createMock(java.lang.Class, java.lang.Object, java.lang.Class...) 
     */
    public static <T, U> T createMock(Class<T> classToMock, U implClass, Answer<?> defaultAnswer) {
        return createMock(classToMock, implClass, defaultAnswer, new Class<?>[0]);
    }
    
    /**
     * Attempts to create an instance of a class in which some or all of the classes methods are replaced with a mocked implementation
     * @param T the class type which will be mocked
     * @param U the class type which will be used to provide method implementations
     * @param classToMock the class type which will be mocked
     * @param implClass the object to which to try to forward method calls
     * @param defaultAnswer the answer to use if an overriden method cannot be found
     * @param interfaces a list of interfaces which the mocked object should extend
     * @return an instance of <code>T</code> in which some or all of the classes methods are replaced with a mocked implementation from <code>U</code>
     * @see #createMock(java.lang.Class, java.lang.Object) 
     */
    public static <T, U> T createMock(Class<T> classToMock, U implClass, Answer<?> defaultAnswer, Class<?>... interfaces) {
        HashMap<Method, InvokableMethod> methods = new HashMap<>();
        for(Method m: listMethods(classToMock, interfaces)) {
            if(Modifier.isStatic(m.getModifiers()) || Modifier.isFinal(m.getModifiers())) {
                continue;
            }
            try {
                Method mImpl = implClass.getClass().getMethod(m.getName(), m.getParameterTypes());
                if(!m.getReturnType().isAssignableFrom(mImpl.getReturnType())) {
                    System.err.println("Method Return Types Not the Same for Method: " + m.getName());
                }
                methods.put(m, mImpl::invoke);
            } catch(NoSuchMethodException e) {}
        }
        MockSettings settings;
        if(interfaces.length == 0) {
            settings = Mockito.withSettings();
        } else {
            settings = Mockito.withSettings().extraInterfaces(interfaces);
        }
        settings = settings.defaultAnswer(new MockAnswer<>(methods, implClass, defaultAnswer));
        T mock = Mockito.mock(classToMock, settings);
        return mock;
    }
    
    public static Method[] listMethods(Class<?> base, Class<?>... interfaces) {
        ArrayList<Method> out = new ArrayList<>();
        out.addAll(Arrays.asList(base.getMethods()));
        out.addAll(Arrays.stream(interfaces).map(Class::getMethods).flatMap(Arrays::stream).collect(Collectors.toList()));
        return out.toArray(Method[]::new);
    }

    private Mocks() {}

    private static final class MockAnswer<U> implements Answer<Object> {
        private final HashMap<Method, InvokableMethod> methods;
        private final U impl;
        private final Answer<?> defaultAnswer;
        MockAnswer(HashMap<Method, InvokableMethod> methods, U impl, Answer<?> defaultAnswer) {
            this.methods = methods;
            this.impl = impl;
            this.defaultAnswer = defaultAnswer;
        }
        @Override
        public Object answer(InvocationOnMock invocation) throws Throwable {
            if(methods.containsKey(invocation.getMethod())) {
                try {
                    return methods.get(invocation.getMethod()).invoke(impl, invocation.getArguments());
                } catch(InvocationTargetException e) {
                    throw e.getTargetException();
                }
            }
            return defaultAnswer.answer(invocation);
        }
    }

    private static interface InvokableMethod {
        public Object invoke(Object object, Object[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;
    } 

}