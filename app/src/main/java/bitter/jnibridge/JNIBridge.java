package bitter.jnibridge;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/* loaded from: classes.dex */
public class JNIBridge {

    /* loaded from: classes.dex */
    private static class a implements InvocationHandler {
        private Object a = new Object[0];
        private long b;
        private Constructor c;

        public a(long j) {
            this.b = j;
            try {
                Constructor declaredConstructor = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class, Integer.TYPE);
                this.c = declaredConstructor;
                declaredConstructor.setAccessible(true);
            } catch (NoClassDefFoundError unused) {
                this.c = null;
            } catch (NoSuchMethodException unused2) {
                this.c = null;
            }
        }

        private Object a(Object obj, Method method, Object[] objArr) {
            if (objArr == null) {
                objArr = new Object[0];
            }
            Class<?> declaringClass = method.getDeclaringClass();
            return ((MethodHandles.Lookup) this.c.newInstance(declaringClass, 2)).in(declaringClass).unreflectSpecial(method, declaringClass).bindTo(obj).invokeWithArguments(objArr);
        }

        public final void a() {
            synchronized (this.a) {
                this.b = 0L;
            }
        }

        public final void finalize() {
            synchronized (this.a) {
                long j = this.b;
                if (j == 0) {
                    return;
                }
                JNIBridge.delete(j);
            }
        }

        @Override // java.lang.reflect.InvocationHandler
        public final Object invoke(Object obj, Method method, Object[] objArr) {
            synchronized (this.a) {
                long j = this.b;
                if (j == 0) {
                    return null;
                }
                try {
                    return JNIBridge.invoke(j, method.getDeclaringClass(), method, objArr);
                } catch (NoSuchMethodError e) {
                    if (this.c == null) {
                        System.err.println("JNIBridge error: Java interface default methods are only supported since Android Oreo");
                        throw e;
                    } else if ((method.getModifiers() & 1024) == 0) {
                        return a(obj, method, objArr);
                    } else {
                        throw e;
                    }
                }
            }
        }
    }

    static native void delete(long j);

    static void disableInterfaceProxy(Object obj) {
        if (obj != null) {
            ((a) Proxy.getInvocationHandler(obj)).a();
        }
    }

    static native Object invoke(long j, Class cls, Method method, Object[] objArr);

    static Object newInterfaceProxy(long j, Class[] clsArr) {
        return Proxy.newProxyInstance(JNIBridge.class.getClassLoader(), clsArr, new a(j));
    }
}
