package zipkin2.storage;

import java.util.List;
import java.util.logging.Logger;
import zipkin2.Call;
import zipkin2.Component;

/* loaded from: classes3.dex */
public abstract class StorageComponent extends Component {
    public abstract SpanConsumer spanConsumer();

    public abstract SpanStore spanStore();

    public AutocompleteTags autocompleteTags() {
        return new AutocompleteTags() { // from class: zipkin2.storage.StorageComponent.1
            @Override // zipkin2.storage.AutocompleteTags
            public Call<List<String>> getKeys() {
                return Call.emptyList();
            }

            @Override // zipkin2.storage.AutocompleteTags
            public Call<List<String>> getValues(String str) {
                return Call.emptyList();
            }
        };
    }

    /* loaded from: classes3.dex */
    public static abstract class Builder {
        public abstract StorageComponent build();

        public abstract Builder searchEnabled(boolean z);

        public abstract Builder strictTraceId(boolean z);

        public Builder autocompleteKeys(List<String> list) {
            Logger.getLogger(getClass().getName()).info("autocompleteKeys not yet supported");
            return this;
        }

        public Builder autocompleteTtl(int i) {
            Logger.getLogger(getClass().getName()).info("autocompleteTtl not yet supported");
            return this;
        }

        public Builder autocompleteCardinality(int i) {
            Logger.getLogger(getClass().getName()).info("autocompleteCardinality not yet supported");
            return this;
        }
    }
}
