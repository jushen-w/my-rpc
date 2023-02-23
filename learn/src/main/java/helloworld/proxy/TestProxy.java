package helloworld.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface Subject {
    String doSomething();
}

class RealSubject implements Subject {
    @Override
    public String doSomething() {
        return "RealSubject is doing something";
    }
}

class DynamicProxy implements InvocationHandler {
    private Object target;

    public DynamicProxy(Object target) {
        this.target = target;
    }

    // Assign the task for proxy
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Dynamic: invoking the method");
        if (method.getName().equals("doSomething")) {
            Object result = method.invoke(target, args);
            return result;
        } else {
            return "Error";
        }
    }
}

class ProxyFactory {
    public static <T> T createProxy(T target, Class<T>[] targetInterfaces) {
        // Proxy.newProxyInstance(class loader,
        //                        list of interfaces to let proxy object knows what functions it will have,
        //                        invocation handler to let proxy object knows what to do)
        T proxy = (T) Proxy.newProxyInstance(ProxyFactory.class.getClassLoader(),
                targetInterfaces, new DynamicProxy(target));
        return proxy;
    }
}

class StaticProxy implements Subject {
    private RealSubject realSubject = new RealSubject();

    @Override
    public String doSomething() {
        System.out.println("Static: invoking the method");
        return realSubject.doSomething();
    }
}

public class TestProxy {
    public static void main(String[] args) {
        RealSubject realSubject = new RealSubject();
        Subject dynamicProxy = ProxyFactory.createProxy(realSubject, new Class[]{Subject.class});
        System.out.println(dynamicProxy.doSomething());
        System.out.println("---");
        // Static Proxy
        StaticProxy staticProxy = new StaticProxy();
        System.out.println(staticProxy.doSomething());
    }
}