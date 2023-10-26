package zipkin2;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes3.dex */
public abstract class Call<V> implements Cloneable {

    /* loaded from: classes3.dex */
    public interface ErrorHandler<V> {
        void onErrorReturn(Throwable th, Callback<V> callback);
    }

    /* loaded from: classes3.dex */
    public interface FlatMapper<V1, V2> {
        Call<V2> map(V1 v1);
    }

    /* loaded from: classes3.dex */
    public interface Mapper<V1, V2> {
        V2 map(V1 v1);
    }

    public abstract void cancel();

    @Override // 
    public abstract Call<V> clone();

    public abstract void enqueue(Callback<V> callback);

    public abstract V execute() throws IOException;

    public abstract boolean isCanceled();

    public static <V> Call<V> create(V v) {
        return new Constant(v);
    }

    public static <T> Call<List<T>> emptyList() {
        return create(Collections.emptyList());
    }

    public final <R> Call<R> map(Mapper<V, R> mapper) {
        return new Mapping(mapper, this);
    }

    public final <R> Call<R> flatMap(FlatMapper<V, R> flatMapper) {
        return new FlatMapping(flatMapper, this);
    }

    public final Call<V> handleError(ErrorHandler<V> errorHandler) {
        return new ErrorHandling(errorHandler, this);
    }

    public static void propagateIfFatal(Throwable th) {
        if (th instanceof VirtualMachineError) {
            throw ((VirtualMachineError) th);
        }
        if (th instanceof ThreadDeath) {
            throw ((ThreadDeath) th);
        }
        if (th instanceof LinkageError) {
            throw ((LinkageError) th);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static final class Constant<V> extends Base<V> {
        final V v;

        Constant(V v) {
            this.v = v;
        }

        @Override // zipkin2.Call.Base
        protected V doExecute() {
            return this.v;
        }

        @Override // zipkin2.Call.Base
        protected void doEnqueue(Callback<V> callback) {
            callback.onSuccess(this.v);
        }

        @Override // zipkin2.Call.Base, zipkin2.Call
        public Call<V> clone() {
            return new Constant(this.v);
        }

        public String toString() {
            return "ConstantCall{value=" + this.v + "}";
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj instanceof Constant) {
                V v = this.v;
                V v2 = ((Constant) obj).v;
                return v == null ? v2 == null : v.equals(v2);
            }
            return false;
        }

        public int hashCode() {
            V v = this.v;
            return (v == null ? 0 : v.hashCode()) ^ 1000003;
        }
    }

    /* loaded from: classes3.dex */
    static final class Mapping<R, V> extends Base<R> {
        final Call<V> delegate;
        final Mapper<V, R> mapper;

        Mapping(Mapper<V, R> mapper, Call<V> call) {
            this.mapper = mapper;
            this.delegate = call;
        }

        @Override // zipkin2.Call.Base
        protected R doExecute() throws IOException {
            return this.mapper.map(this.delegate.execute());
        }

        @Override // zipkin2.Call.Base
        protected void doEnqueue(final Callback<R> callback) {
            this.delegate.enqueue(new Callback<V>() { // from class: zipkin2.Call.Mapping.1
                @Override // zipkin2.Callback
                public void onSuccess(V v) {
                    try {
                        callback.onSuccess(Mapping.this.mapper.map(v));
                    } catch (Throwable th) {
                        callback.onError(th);
                    }
                }

                @Override // zipkin2.Callback
                public void onError(Throwable th) {
                    callback.onError(th);
                }
            });
        }

        public String toString() {
            return "Mapping{call=" + this.delegate + ", mapper=" + this.mapper + "}";
        }

        @Override // zipkin2.Call.Base, zipkin2.Call
        public Call<R> clone() {
            return new Mapping(this.mapper, this.delegate.clone());
        }
    }

    /* loaded from: classes3.dex */
    static final class FlatMapping<R, V> extends Base<R> {
        final Call<V> delegate;
        final FlatMapper<V, R> flatMapper;
        volatile Call<R> mapped;

        FlatMapping(FlatMapper<V, R> flatMapper, Call<V> call) {
            this.flatMapper = flatMapper;
            this.delegate = call;
        }

        @Override // zipkin2.Call.Base
        protected R doExecute() throws IOException {
            Call<R> map = this.flatMapper.map(this.delegate.execute());
            this.mapped = map;
            return map.execute();
        }

        @Override // zipkin2.Call.Base
        protected void doEnqueue(final Callback<R> callback) {
            this.delegate.enqueue(new Callback<V>() { // from class: zipkin2.Call.FlatMapping.1
                @Override // zipkin2.Callback
                public void onSuccess(V v) {
                    try {
                        FlatMapping flatMapping = FlatMapping.this;
                        Call<R> map = flatMapping.flatMapper.map(v);
                        flatMapping.mapped = map;
                        map.enqueue(callback);
                    } catch (Throwable th) {
                        callback.onError(th);
                    }
                }

                @Override // zipkin2.Callback
                public void onError(Throwable th) {
                    callback.onError(th);
                }
            });
        }

        @Override // zipkin2.Call.Base
        protected void doCancel() {
            this.delegate.cancel();
            if (this.mapped != null) {
                this.mapped.cancel();
            }
        }

        public String toString() {
            return "FlatMapping{call=" + this.delegate + ", flatMapper=" + this.flatMapper + "}";
        }

        @Override // zipkin2.Call.Base, zipkin2.Call
        public Call<R> clone() {
            return new FlatMapping(this.flatMapper, this.delegate.clone());
        }
    }

    /* loaded from: classes3.dex */
    static final class ErrorHandling<V> extends Base<V> {
        static final Object SENTINEL = new Object();
        final Call<V> delegate;
        final ErrorHandler<V> errorHandler;

        ErrorHandling(ErrorHandler<V> errorHandler, Call<V> call) {
            this.errorHandler = errorHandler;
            this.delegate = call;
        }

        @Override // zipkin2.Call.Base
        protected V doExecute() throws IOException {
            try {
                return this.delegate.execute();
            } catch (IOException | Error | RuntimeException e) {
                Object obj = SENTINEL;
                final AtomicReference atomicReference = new AtomicReference(obj);
                this.errorHandler.onErrorReturn(e, new Callback<V>() { // from class: zipkin2.Call.ErrorHandling.1
                    @Override // zipkin2.Callback
                    public void onError(Throwable th) {
                    }

                    @Override // zipkin2.Callback
                    public void onSuccess(V v) {
                        atomicReference.set(v);
                    }
                });
                V v = (V) atomicReference.get();
                if (obj != v) {
                    return v;
                }
                throw e;
            }
        }

        @Override // zipkin2.Call.Base
        protected void doEnqueue(final Callback<V> callback) {
            this.delegate.enqueue(new Callback<V>() { // from class: zipkin2.Call.ErrorHandling.2
                @Override // zipkin2.Callback
                public void onSuccess(V v) {
                    callback.onSuccess(v);
                }

                @Override // zipkin2.Callback
                public void onError(Throwable th) {
                    ErrorHandling.this.errorHandler.onErrorReturn(th, callback);
                }
            });
        }

        @Override // zipkin2.Call.Base
        protected void doCancel() {
            this.delegate.cancel();
        }

        public String toString() {
            return "ErrorHandling{call=" + this.delegate + ", errorHandler=" + this.errorHandler + "}";
        }

        @Override // zipkin2.Call.Base, zipkin2.Call
        public Call<V> clone() {
            return new ErrorHandling(this.errorHandler, this.delegate.clone());
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Base<V> extends Call<V> {
        volatile boolean canceled;
        boolean executed;

        protected void doCancel() {
        }

        protected abstract void doEnqueue(Callback<V> callback);

        protected abstract V doExecute() throws IOException;

        protected boolean doIsCanceled() {
            return false;
        }

        @Override // zipkin2.Call
        public /* bridge */ /* synthetic */ Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

        protected Base() {
        }

        @Override // zipkin2.Call
        public final V execute() throws IOException {
            synchronized (this) {
                if (this.executed) {
                    throw new IllegalStateException("Already Executed");
                }
                this.executed = true;
            }
            if (isCanceled()) {
                throw new IOException("Canceled");
            }
            return doExecute();
        }

        @Override // zipkin2.Call
        public final void enqueue(Callback<V> callback) {
            synchronized (this) {
                if (this.executed) {
                    throw new IllegalStateException("Already Executed");
                }
                this.executed = true;
            }
            if (isCanceled()) {
                callback.onError(new IOException("Canceled"));
            } else {
                doEnqueue(callback);
            }
        }

        @Override // zipkin2.Call
        public final void cancel() {
            this.canceled = true;
            doCancel();
        }

        @Override // zipkin2.Call
        public final boolean isCanceled() {
            return this.canceled || doIsCanceled();
        }
    }
}
