package vn.hcmute.springboot.config;

import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.catalina.Context;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.WebResourceRoot;
import org.springframework.util.ResourceUtils;

public class JSPStaticResourceConfigurer implements LifecycleListener {

    private final Context context;

    private final String subPath = "/META-INF";

    public JSPStaticResourceConfigurer(Context context) {
        this.context = context;
    }

    @Override
    public void lifecycleEvent(LifecycleEvent event) {

        if (!Lifecycle.CONFIGURE_START_EVENT.equals(event.getType())) {
            return;
        }

        final URL finalLocation = getUrl();

        if (ResourceUtils.isFileURL(finalLocation)) {

            try {

                Path basePath = Paths.get(finalLocation.toURI());

                Path metaInfPath = basePath.resolve("META-INF");

                if (!Files.isDirectory(metaInfPath)) {
                    return;
                }

            } catch (Exception e) {

                throw new IllegalStateException(
                        "Unable to check JSP resource directory",
                        e
                );
            }
        }

        this.context.getResources().createWebResourceSet(
                WebResourceRoot.ResourceSetType.RESOURCE_JAR,
                "/",
                finalLocation,
                subPath
        );
    }

    private URL getUrl() {

        final URL location = this.getClass()
                .getProtectionDomain()
                .getCodeSource()
                .getLocation();

        if (ResourceUtils.isFileURL(location)) {

            return location;

        } else if (ResourceUtils.isJarURL(location)
                || "nested".equalsIgnoreCase(location.getProtocol())) {

            try {

                String locationStr = location.getPath()
                        .replaceFirst("^nested:", "")
                        .replaceFirst("/!BOOT-INF/classes/!/$", "!/")
                        .replaceFirst("/!WEB-INF/classes/!/$", "!/");

                return new URI(
                        "jar:file",
                        locationStr,
                        null
                ).toURL();

            } catch (Exception e) {

                throw new IllegalStateException(
                        "Unable to add new JSP source URI to Tomcat resources",
                        e
                );
            }

        } else {

            throw new IllegalStateException(
                    "Can not add Tomcat resources, unhandleable url: "
                            + location
            );
        }
    }
}