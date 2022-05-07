package com.sunchaser.sparrow.javaee.graphql.context;

import graphql.kickstart.servlet.context.GraphQLServletContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.dataloader.DataLoaderRegistry;

import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 自定义上下文
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/5/7
 */
@Getter
@AllArgsConstructor
public class CustomGraphQLContext implements GraphQLServletContext {

    private final String userId;
    private final GraphQLServletContext context;

    @Override
    public List<Part> getFileParts() {
        return context.getFileParts();
    }

    @Override
    public Map<String, List<Part>> getParts() {
        return context.getParts();
    }

    @Override
    public HttpServletRequest getHttpServletRequest() {
        return context.getHttpServletRequest();
    }

    @Override
    public HttpServletResponse getHttpServletResponse() {
        return context.getHttpServletResponse();
    }

    @Override
    public Optional<Subject> getSubject() {
        return context.getSubject();
    }

    @Override
    public @NonNull DataLoaderRegistry getDataLoaderRegistry() {
        return context.getDataLoaderRegistry();
    }
}
