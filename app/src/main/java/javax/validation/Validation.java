package javax.validation;

import java.lang.ref.SoftReference;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.WeakHashMap;
import javax.validation.bootstrap.GenericBootstrap;
import javax.validation.bootstrap.ProviderSpecificBootstrap;
import javax.validation.spi.BootstrapState;
import javax.validation.spi.ValidationProvider;

/* loaded from: classes3.dex */
public class Validation {
    public static ValidatorFactory buildDefaultValidatorFactory() {
        return byDefaultProvider().configure().buildValidatorFactory();
    }

    public static GenericBootstrap byDefaultProvider() {
        return new GenericBootstrapImpl();
    }

    public static <T extends Configuration<T>, U extends ValidationProvider<T>> ProviderSpecificBootstrap<T> byProvider(Class<U> cls) {
        return new ProviderSpecificBootstrapImpl(cls);
    }

    /* loaded from: classes3.dex */
    private static class ProviderSpecificBootstrapImpl<T extends Configuration<T>, U extends ValidationProvider<T>> implements ProviderSpecificBootstrap<T> {
        private ValidationProviderResolver resolver;
        private final Class<U> validationProviderClass;

        public ProviderSpecificBootstrapImpl(Class<U> cls) {
            this.validationProviderClass = cls;
        }

        @Override // javax.validation.bootstrap.ProviderSpecificBootstrap
        public ProviderSpecificBootstrap<T> providerResolver(ValidationProviderResolver validationProviderResolver) {
            this.resolver = validationProviderResolver;
            return this;
        }

        @Override // javax.validation.bootstrap.ProviderSpecificBootstrap
        public T configure() {
            if (this.validationProviderClass == null) {
                throw new ValidationException("builder is mandatory. Use Validation.byDefaultProvider() to use the generic provider discovery mechanism");
            }
            GenericBootstrapImpl genericBootstrapImpl = new GenericBootstrapImpl();
            ValidationProviderResolver validationProviderResolver = this.resolver;
            if (validationProviderResolver == null) {
                this.resolver = genericBootstrapImpl.getDefaultValidationProviderResolver();
            } else {
                genericBootstrapImpl.providerResolver(validationProviderResolver);
            }
            try {
                for (ValidationProvider<?> validationProvider : this.resolver.getValidationProviders()) {
                    if (this.validationProviderClass.isAssignableFrom(validationProvider.getClass())) {
                        return (T) this.validationProviderClass.cast(validationProvider).createSpecializedConfiguration(genericBootstrapImpl);
                    }
                }
                throw new ValidationException("Unable to find provider: " + this.validationProviderClass);
            } catch (RuntimeException e) {
                throw new ValidationException("Unable to get available provider resolvers.", e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class GenericBootstrapImpl implements GenericBootstrap, BootstrapState {
        private ValidationProviderResolver defaultResolver;
        private ValidationProviderResolver resolver;

        private GenericBootstrapImpl() {
        }

        @Override // javax.validation.bootstrap.GenericBootstrap
        public GenericBootstrap providerResolver(ValidationProviderResolver validationProviderResolver) {
            this.resolver = validationProviderResolver;
            return this;
        }

        @Override // javax.validation.spi.BootstrapState
        public ValidationProviderResolver getValidationProviderResolver() {
            return this.resolver;
        }

        @Override // javax.validation.spi.BootstrapState
        public ValidationProviderResolver getDefaultValidationProviderResolver() {
            if (this.defaultResolver == null) {
                this.defaultResolver = new DefaultValidationProviderResolver();
            }
            return this.defaultResolver;
        }

        @Override // javax.validation.bootstrap.GenericBootstrap
        public Configuration<?> configure() {
            ValidationProviderResolver validationProviderResolver = this.resolver;
            if (validationProviderResolver == null) {
                validationProviderResolver = getDefaultValidationProviderResolver();
            }
            try {
                if (validationProviderResolver.getValidationProviders().size() == 0) {
                    throw new ValidationException("Unable to create a Configuration, because no Bean Validation provider could be found. Add a provider like Hibernate Validator (RI) to your classpath.");
                }
                try {
                    return validationProviderResolver.getValidationProviders().get(0).createGenericConfiguration(this);
                } catch (RuntimeException e) {
                    throw new ValidationException("Unable to instantiate Configuration.", e);
                }
            } catch (ValidationException e2) {
                throw e2;
            } catch (RuntimeException e3) {
                throw new ValidationException("Unable to get available provider resolvers.", e3);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class DefaultValidationProviderResolver implements ValidationProviderResolver {
        private DefaultValidationProviderResolver() {
        }

        @Override // javax.validation.ValidationProviderResolver
        public List<ValidationProvider<?>> getValidationProviders() {
            return GetValidationProviderListAction.getValidationProviderList();
        }
    }

    /* loaded from: classes3.dex */
    private static class GetValidationProviderListAction implements PrivilegedAction<List<ValidationProvider<?>>> {
        private static final WeakHashMap<ClassLoader, SoftReference<List<ValidationProvider<?>>>> providersPerClassloader = new WeakHashMap<>();

        private GetValidationProviderListAction() {
        }

        public static List<ValidationProvider<?>> getValidationProviderList() {
            GetValidationProviderListAction getValidationProviderListAction = new GetValidationProviderListAction();
            if (System.getSecurityManager() != null) {
                return (List) AccessController.doPrivileged(getValidationProviderListAction);
            }
            return getValidationProviderListAction.run();
        }

        @Override // java.security.PrivilegedAction
        public List<ValidationProvider<?>> run() {
            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            List<ValidationProvider<?>> cachedValidationProviders = getCachedValidationProviders(contextClassLoader);
            if (cachedValidationProviders != null) {
                return cachedValidationProviders;
            }
            List<ValidationProvider<?>> loadProviders = loadProviders(contextClassLoader);
            if (loadProviders.isEmpty()) {
                contextClassLoader = DefaultValidationProviderResolver.class.getClassLoader();
                List<ValidationProvider<?>> cachedValidationProviders2 = getCachedValidationProviders(contextClassLoader);
                if (cachedValidationProviders2 != null) {
                    return cachedValidationProviders2;
                }
                loadProviders = loadProviders(contextClassLoader);
            }
            cacheValidationProviders(contextClassLoader, loadProviders);
            return loadProviders;
        }

        private List<ValidationProvider<?>> loadProviders(ClassLoader classLoader) {
            Iterator it = ServiceLoader.load(ValidationProvider.class, classLoader).iterator();
            ArrayList arrayList = new ArrayList();
            while (it.hasNext()) {
                try {
                    arrayList.add(it.next());
                } catch (ServiceConfigurationError unused) {
                }
            }
            return arrayList;
        }

        private synchronized List<ValidationProvider<?>> getCachedValidationProviders(ClassLoader classLoader) {
            SoftReference<List<ValidationProvider<?>>> softReference;
            softReference = providersPerClassloader.get(classLoader);
            return softReference != null ? softReference.get() : null;
        }

        private synchronized void cacheValidationProviders(ClassLoader classLoader, List<ValidationProvider<?>> list) {
            providersPerClassloader.put(classLoader, new SoftReference<>(list));
        }
    }
}
