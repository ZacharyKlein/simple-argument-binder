package com.example.binding;

import io.micronaut.core.convert.ArgumentConversionContext;
import io.micronaut.core.convert.ConversionService;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.bind.binders.AnnotatedRequestArgumentBinder;
import io.micronaut.http.cookie.Cookie;
import io.micronaut.jackson.serialize.JacksonObjectSerializer;

import javax.inject.Singleton;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

@Singleton
public class ShoppingCartArgumentBinder implements AnnotatedRequestArgumentBinder<ShoppingCart, Object> {

    private final ConversionService<?> conversionService;
    private final JacksonObjectSerializer objectSerializer;

    public ShoppingCartArgumentBinder(ConversionService<?> conversionService, JacksonObjectSerializer objectSerializer) {
        this.conversionService = conversionService;
        this.objectSerializer = objectSerializer;
    }

    @Override
    public Class<ShoppingCart> getAnnotationType() {
        return ShoppingCart.class;
    }

    @Override
    public BindingResult<Object> bind(ArgumentConversionContext<Object> context, HttpRequest<?> source) {
        String parameterName = context.getAnnotationMetadata().stringValue(ShoppingCart.class).orElse(context.getArgument().getName());
        Cookie cookie = source.getCookies().get("shoppingCart");

        if (cookie != null) {
            Optional<Map<String, Object>> cookieValue;

            cookieValue = objectSerializer.deserialize(cookie.getValue().getBytes(StandardCharsets.UTF_8), Argument.mapOf(String.class, Object.class));

            return () -> cookieValue.flatMap(map -> {
                Object obj = map.get(parameterName);
                return conversionService.convert(obj, context);
            });

        }

        return BindingResult.EMPTY;
    }
}
