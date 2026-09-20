package vn.hcmute.springboot.config;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

public class CustomSiteMeshFilter
        extends ConfigurableSiteMeshFilter {

    @Override
    protected void applyCustomConfiguration(
            SiteMeshFilterBuilder builder) {

        builder

            .addDecoratorPath(
                "/*",
                "web.jsp"
            )

            .addDecoratorPath(
                "/admin/*",
                "admin.jsp"
            )

            .addExcludedPath(
                "/image*"
            )

            .addExcludedPath(
                "/product-image*"
            )

            .addExcludedPath(
                "/profile-image*"
            )

            .addExcludedPath(
                "/static/*"
            )

            .addExcludedPath(
                "/static/**"
            )

            .addExcludedPath(
                "/api/*"
            )

            .addExcludedPath(
                "/api/**"
            )

            .addExcludedPath(
                "/swagger-ui.html"
            )

            .addExcludedPath(
                "/swagger-ui/*"
            )

            .addExcludedPath(
                "/swagger-ui/**"
            )

            .addExcludedPath(
                "/v3/api-docs"
            )

            .addExcludedPath(
                "/v3/api-docs/*"
            )

            .addExcludedPath(
                "/v3/api-docs/**"
            )

            .addExcludedPath(
                "/graphql"
            )

            .addExcludedPath(
                "/graphql/*"
            )

            .addExcludedPath(
                "/graphql/**"
            )

            .addExcludedPath(
                "/graphiql"
            )

            .addExcludedPath(
                "/graphiql/*"
            )

            .addExcludedPath(
                "/graphiql/**"
            )

            .addExcludedPath(
                "/admin/graphql/*"
            )

            .addExcludedPath(
                "/admin/graphql/**"
            )

            .addExcludedPath(
                "/graphql-ui/*"
            )

            .addExcludedPath(
                "/graphql-ui/**"
            );
    }
}