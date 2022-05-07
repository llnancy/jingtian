package com.sunchaser.sparrow.javaee.graphql.context;

import graphql.kickstart.execution.context.GraphQLContext;
import graphql.kickstart.servlet.context.DefaultGraphQLServletContext;
import graphql.kickstart.servlet.context.GraphQLServletContextBuilder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/5/7
 */
@Component
public class CustomGraphQLContextBuilder implements GraphQLServletContextBuilder {

    @Override
    public GraphQLContext build(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String userId = httpServletRequest.getHeader("user_id");
        DefaultGraphQLServletContext context = DefaultGraphQLServletContext.createServletContext()
                .with(httpServletRequest)
                .with(httpServletResponse)
                .build();
        return new CustomGraphQLContext(userId, context);
    }

    @Override
    public GraphQLContext build(Session session, HandshakeRequest handshakeRequest) {
        throw new IllegalStateException("UnSupported");
    }

    @Override
    public GraphQLContext build() {
        throw new IllegalStateException("UnSupported");
    }
}
