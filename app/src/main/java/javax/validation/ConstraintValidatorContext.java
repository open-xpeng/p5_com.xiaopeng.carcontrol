package javax.validation;

/* loaded from: classes3.dex */
public interface ConstraintValidatorContext {

    /* loaded from: classes3.dex */
    public interface ConstraintViolationBuilder {

        /* loaded from: classes3.dex */
        public interface LeafNodeBuilderCustomizableContext {
            ConstraintValidatorContext addConstraintViolation();

            LeafNodeContextBuilder inIterable();
        }

        /* loaded from: classes3.dex */
        public interface LeafNodeBuilderDefinedContext {
            ConstraintValidatorContext addConstraintViolation();
        }

        /* loaded from: classes3.dex */
        public interface LeafNodeContextBuilder {
            ConstraintValidatorContext addConstraintViolation();

            LeafNodeBuilderDefinedContext atIndex(Integer num);

            LeafNodeBuilderDefinedContext atKey(Object obj);
        }

        /* loaded from: classes3.dex */
        public interface NodeBuilderCustomizableContext {
            LeafNodeBuilderCustomizableContext addBeanNode();

            ConstraintValidatorContext addConstraintViolation();

            NodeBuilderCustomizableContext addNode(String str);

            NodeBuilderCustomizableContext addPropertyNode(String str);

            NodeContextBuilder inIterable();
        }

        /* loaded from: classes3.dex */
        public interface NodeBuilderDefinedContext {
            LeafNodeBuilderCustomizableContext addBeanNode();

            ConstraintValidatorContext addConstraintViolation();

            NodeBuilderCustomizableContext addNode(String str);

            NodeBuilderCustomizableContext addPropertyNode(String str);
        }

        /* loaded from: classes3.dex */
        public interface NodeContextBuilder {
            LeafNodeBuilderCustomizableContext addBeanNode();

            ConstraintValidatorContext addConstraintViolation();

            NodeBuilderCustomizableContext addNode(String str);

            NodeBuilderCustomizableContext addPropertyNode(String str);

            NodeBuilderDefinedContext atIndex(Integer num);

            NodeBuilderDefinedContext atKey(Object obj);
        }

        LeafNodeBuilderCustomizableContext addBeanNode();

        ConstraintValidatorContext addConstraintViolation();

        NodeBuilderDefinedContext addNode(String str);

        NodeBuilderDefinedContext addParameterNode(int i);

        NodeBuilderCustomizableContext addPropertyNode(String str);
    }

    ConstraintViolationBuilder buildConstraintViolationWithTemplate(String str);

    void disableDefaultConstraintViolation();

    String getDefaultConstraintMessageTemplate();

    <T> T unwrap(Class<T> cls);
}
