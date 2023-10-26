package com.unity3d.player;

import com.xiaopeng.lib.framework.moduleinterface.carcontroller.IInputController;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class ReflectionHelper {
    protected static boolean LOG = false;
    protected static final boolean LOGV = false;
    private static a[] a = new a[4096];

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class a {
        public volatile Member a;
        private final Class b;
        private final String c;
        private final String d;
        private final int e;

        a(Class cls, String str, String str2) {
            this.b = cls;
            this.c = str;
            this.d = str2;
            this.e = ((((cls.hashCode() + IInputController.KEYCODE_KNOB_TALKING_BOOK) * 31) + str.hashCode()) * 31) + str2.hashCode();
        }

        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj instanceof a) {
                a aVar = (a) obj;
                if (this.e == aVar.e && this.d.equals(aVar.d) && this.c.equals(aVar.c) && this.b.equals(aVar.b)) {
                    return true;
                }
            }
            return false;
        }

        public final int hashCode() {
            return this.e;
        }
    }

    ReflectionHelper() {
    }

    private static float a(Class cls, Class cls2) {
        if (cls.equals(cls2)) {
            return 1.0f;
        }
        if (cls.isPrimitive() || cls2.isPrimitive()) {
            return 0.0f;
        }
        try {
            if (cls.asSubclass(cls2) != null) {
                return 0.5f;
            }
        } catch (ClassCastException unused) {
        }
        try {
            return cls2.asSubclass(cls) != null ? 0.1f : 0.0f;
        } catch (ClassCastException unused2) {
            return 0.0f;
        }
    }

    private static float a(Class cls, Class[] clsArr, Class[] clsArr2) {
        if (clsArr2.length == 0) {
            return 0.1f;
        }
        int i = 0;
        if ((clsArr == null ? 0 : clsArr.length) + 1 != clsArr2.length) {
            return 0.0f;
        }
        float f = 1.0f;
        if (clsArr != null) {
            int length = clsArr.length;
            float f2 = 1.0f;
            int i2 = 0;
            while (i < length) {
                f2 *= a(clsArr[i], clsArr2[i2]);
                i++;
                i2++;
            }
            f = f2;
        }
        return f * a(cls, clsArr2[clsArr2.length - 1]);
    }

    private static Class a(String str, int[] iArr) {
        while (iArr[0] < str.length()) {
            int i = iArr[0];
            iArr[0] = i + 1;
            char charAt = str.charAt(i);
            if (charAt != '(' && charAt != ')') {
                if (charAt == 'L') {
                    int indexOf = str.indexOf(59, iArr[0]);
                    if (indexOf != -1) {
                        String substring = str.substring(iArr[0], indexOf);
                        iArr[0] = indexOf + 1;
                        try {
                            return Class.forName(substring.replace('/', '.'));
                        } catch (ClassNotFoundException unused) {
                            return null;
                        }
                    }
                    return null;
                } else if (charAt == 'Z') {
                    return Boolean.TYPE;
                } else {
                    if (charAt == 'I') {
                        return Integer.TYPE;
                    }
                    if (charAt == 'F') {
                        return Float.TYPE;
                    }
                    if (charAt == 'V') {
                        return Void.TYPE;
                    }
                    if (charAt == 'B') {
                        return Byte.TYPE;
                    }
                    if (charAt == 'S') {
                        return Short.TYPE;
                    }
                    if (charAt == 'J') {
                        return Long.TYPE;
                    }
                    if (charAt == 'D') {
                        return Double.TYPE;
                    }
                    if (charAt == '[') {
                        return Array.newInstance(a(str, iArr), 0).getClass();
                    }
                    g.Log(5, "! parseType; " + charAt + " is not known!");
                    return null;
                }
            }
        }
        return null;
    }

    private static void a(a aVar, Member member) {
        aVar.a = member;
        a[aVar.hashCode() & (a.length - 1)] = aVar;
    }

    private static boolean a(a aVar) {
        a aVar2 = a[aVar.hashCode() & (a.length - 1)];
        if (aVar.equals(aVar2)) {
            aVar.a = aVar2.a;
            return true;
        }
        return false;
    }

    private static Class[] a(String str) {
        Class a2;
        int i = 0;
        int[] iArr = {0};
        ArrayList arrayList = new ArrayList();
        while (iArr[0] < str.length() && (a2 = a(str, iArr)) != null) {
            arrayList.add(a2);
        }
        Class[] clsArr = new Class[arrayList.size()];
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            clsArr[i] = (Class) it.next();
            i++;
        }
        return clsArr;
    }

    protected static Constructor getConstructorID(Class cls, String str) {
        Class<?>[] parameterTypes;
        if (LOG) {
            g.Log(3, "? getConstructorID(\"" + cls.getName() + "\", \"" + str + "\")");
        }
        Constructor<?> constructor = null;
        a aVar = new a(cls, "", str);
        if (a(aVar)) {
            constructor = (Constructor) aVar.a;
        } else {
            Class[] a2 = a(str);
            float f = 0.0f;
            Constructor<?>[] constructors = cls.getConstructors();
            int length = constructors.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                Constructor<?> constructor2 = constructors[i];
                float a3 = a(Void.TYPE, constructor2.getParameterTypes(), a2);
                if (a3 > f) {
                    if (a3 == 1.0f) {
                        constructor = constructor2;
                        break;
                    }
                    constructor = constructor2;
                    f = a3;
                }
                i++;
            }
            a(aVar, constructor);
        }
        if (constructor != null) {
            if (LOG) {
                StringBuilder sb = new StringBuilder();
                for (Class<?> cls2 : constructor.getParameterTypes()) {
                    if (sb.length() != 0) {
                        sb.append(", ");
                    }
                    sb.append(cls2.getSimpleName());
                }
                g.Log(3, "! " + constructor.getName() + "(" + sb.toString() + ");");
            }
            return constructor;
        }
        throw new NoSuchMethodError("<init>" + str + " in class " + cls.getName());
    }

    protected static Field getFieldID(Class cls, String str, String str2, boolean z) {
        Field field;
        if (LOG) {
            g.Log(3, "? getFieldID(\"" + cls.getName() + "\", \"" + str + "\", \"" + str2 + "\", " + (z ? "static)" : "non-static)"));
        }
        Class cls2 = cls;
        a aVar = new a(cls2, str, str2);
        if (a(aVar)) {
            field = (Field) aVar.a;
        } else {
            Class[] a2 = a(str2);
            float f = 0.0f;
            Field field2 = null;
            while (cls2 != null) {
                Field[] declaredFields = cls2.getDeclaredFields();
                int length = declaredFields.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    Field field3 = declaredFields[i];
                    if (z == Modifier.isStatic(field3.getModifiers()) && field3.getName().compareTo(str) == 0) {
                        float a3 = a(field3.getType(), (Class[]) null, a2);
                        if (a3 > f) {
                            if (a3 == 1.0f) {
                                f = a3;
                                field2 = field3;
                                break;
                            }
                            f = a3;
                            field2 = field3;
                        } else {
                            continue;
                        }
                    }
                    i++;
                }
                if (f == 1.0f || cls2.isPrimitive() || cls2.isInterface() || cls2.equals(Object.class) || cls2.equals(Void.TYPE)) {
                    break;
                }
                cls2 = cls2.getSuperclass();
            }
            a(aVar, field2);
            field = field2;
        }
        if (field != null) {
            if (LOG) {
                g.Log(3, "! " + field.getType().getSimpleName() + " " + field.getDeclaringClass().getSimpleName() + "." + field.getName() + ";");
            }
            return field;
        }
        Object[] objArr = new Object[4];
        objArr[0] = z ? "static" : "non-static";
        objArr[1] = str;
        objArr[2] = str2;
        objArr[3] = cls2.getName();
        throw new NoSuchFieldError(String.format("no %s field with name='%s' signature='%s' in class L%s;", objArr));
    }

    protected static Method getMethodID(Class cls, String str, String str2, boolean z) {
        Class<?>[] parameterTypes;
        if (LOG) {
            g.Log(3, "? getMethodID(\"" + cls.getName() + "\", \"" + str + "\", \"" + str2 + "\", " + (z ? "static)" : "non-static)"));
        }
        Method method = null;
        Class cls2 = cls;
        a aVar = new a(cls2, str, str2);
        if (a(aVar)) {
            method = (Method) aVar.a;
        } else {
            Class[] a2 = a(str2);
            float f = 0.0f;
            while (cls2 != null) {
                Method[] declaredMethods = cls2.getDeclaredMethods();
                int length = declaredMethods.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    Method method2 = declaredMethods[i];
                    if (z == Modifier.isStatic(method2.getModifiers()) && method2.getName().compareTo(str) == 0) {
                        float a3 = a(method2.getReturnType(), method2.getParameterTypes(), a2);
                        if (a3 > f) {
                            f = a3;
                            if (a3 == 1.0f) {
                                method = method2;
                                break;
                            }
                            method = method2;
                        } else {
                            continue;
                        }
                    }
                    i++;
                }
                if (f == 1.0f || cls2.isPrimitive() || cls2.isInterface() || cls2.equals(Object.class) || cls2.equals(Void.TYPE)) {
                    break;
                }
                cls2 = cls2.getSuperclass();
            }
            a(aVar, method);
        }
        if (method == null) {
            Object[] objArr = new Object[4];
            objArr[0] = z ? "static" : "non-static";
            objArr[1] = str;
            objArr[2] = str2;
            objArr[3] = cls2.getName();
            throw new NoSuchMethodError(String.format("no %s method with name='%s' signature='%s' in class L%s;", objArr));
        }
        if (LOG) {
            StringBuilder sb = new StringBuilder();
            for (Class<?> cls3 : method.getParameterTypes()) {
                if (sb.length() != 0) {
                    sb.append(", ");
                }
                sb.append(cls3.getSimpleName());
            }
            g.Log(3, "! " + method.getReturnType().getSimpleName() + " " + method.getDeclaringClass().getSimpleName() + "." + method.getName() + "(" + sb.toString() + ");");
        }
        return method;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeProxyFinalize(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native Object nativeProxyInvoke(int i, String str, Object[] objArr);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeProxyLogJNIInvokeException();

    protected static Object newProxyInstance(int i, Class cls) {
        return newProxyInstance(i, new Class[]{cls});
    }

    protected static Object newProxyInstance(final int i, final Class[] clsArr) {
        if (LOG) {
            g.Log(3, String.format("ReflectionHelper.Proxy(%d,%s)", Integer.valueOf(i), Arrays.asList(clsArr)));
        }
        return Proxy.newProxyInstance(ReflectionHelper.class.getClassLoader(), clsArr, new InvocationHandler() { // from class: com.unity3d.player.ReflectionHelper.1
            private static Object a(Object obj, Method method, Object[] objArr) {
                if (objArr == null) {
                    try {
                        objArr = new Object[0];
                    } catch (NoClassDefFoundError unused) {
                        g.Log(6, String.format("Java interface default methods are only supported since Android Oreo", new Object[0]));
                        ReflectionHelper.nativeProxyLogJNIInvokeException();
                        return null;
                    }
                }
                Class<?> declaringClass = method.getDeclaringClass();
                Constructor declaredConstructor = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class, Integer.TYPE);
                declaredConstructor.setAccessible(true);
                return ((MethodHandles.Lookup) declaredConstructor.newInstance(declaringClass, 2)).in(declaringClass).unreflectSpecial(method, declaringClass).bindTo(obj).invokeWithArguments(objArr);
            }

            protected final void finalize() {
                try {
                    if (ReflectionHelper.LOG) {
                        g.Log(3, String.format("ReflectionHelper.Proxy.finilize(%d, %s)", Integer.valueOf(i), Arrays.asList(clsArr)));
                    }
                    ReflectionHelper.nativeProxyFinalize(i);
                } finally {
                    super.finalize();
                }
            }

            @Override // java.lang.reflect.InvocationHandler
            public final Object invoke(Object obj, Method method, Object[] objArr) {
                if (ReflectionHelper.LOG) {
                    Object[] objArr2 = new Object[4];
                    objArr2[0] = Integer.valueOf(i);
                    objArr2[1] = Arrays.asList(clsArr);
                    objArr2[2] = method.getName();
                    objArr2[3] = objArr == null ? "<null>" : Arrays.asList(objArr);
                    g.Log(3, String.format("ReflectionHelper.Proxy.invoke(%d, %s, %s, %s)", objArr2));
                }
                Object nativeProxyInvoke = ReflectionHelper.nativeProxyInvoke(i, method.getName(), objArr);
                if (nativeProxyInvoke == null) {
                    if ((method.getModifiers() & 1024) == 0) {
                        return a(obj, method, objArr);
                    }
                    ReflectionHelper.nativeProxyLogJNIInvokeException();
                }
                return nativeProxyInvoke;
            }
        });
    }
}
