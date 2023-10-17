package io.github.llnancy.jingtian.middleware.apollo;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.ConfigFile;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.core.enums.ConfigFileFormat;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;

/**
 * Client 客户端读取 Apollo 配置
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/3/21
 */
public class ReadApolloConfigTest {

    /**
     * 添加 VM 参数：-Dapp.id=SampleApp -Denv=DEV -Ddev_meta=http://localhost:8080
     */
    public static void main(String[] args) {
        readDefaultNamespaceConfig();
        readSpecifyNamespaceConfig();
        readCommonNamespaceConfig();
        readYamlFormatNamespaceConfig();
        readNotYamlFormatNamespace();
        listenConfigChangeEvent();
    }

    private static void readDefaultNamespaceConfig() {
        Config appConfig = ConfigService.getAppConfig();
        String defaultValue = "default value";
        String value = appConfig.getProperty("simple-config", defaultValue);
        System.out.println(value);
    }

    private static void readSpecifyNamespaceConfig() {
        Config db = ConfigService.getConfig("db");
        String driverClassName = db.getProperty("spring.datasource.driver-class-name", null);
        System.out.println(driverClassName);
    }

    private static void readCommonNamespaceConfig() {
        Config config = ConfigService.getConfig("Dev.spring-common");
        String value = config.getProperty("server.servlet.context-path", null);
        System.out.println(value);
    }

    private static void readYamlFormatNamespaceConfig() {
        Config config = ConfigService.getConfig("application.yml");
        String value = config.getProperty("yml.simple-config", null);
        System.out.println(value);
    }

    private static void readNotYamlFormatNamespace() {
        ConfigFile file = ConfigService.getConfigFile("application", ConfigFileFormat.TXT);
        String content = file.getContent();
        System.out.println(content);
    }

    private static void listenConfigChangeEvent() {
        Config db = ConfigService.getConfig("db");
        db.addChangeListener(configChangeEvent -> {
            System.out.println("listened config change event, the namespace is " + configChangeEvent.getNamespace());
            for (String changedKey : configChangeEvent.changedKeys()) {
                ConfigChange change = configChangeEvent.getChange(changedKey);
                System.out.printf("Found change - key: %s, oldValue: %s, newValue: %s, changeType: %s%n", change.getPropertyName(), change.getOldValue(), change.getNewValue(), change.getChangeType());
            }
        });
        try {
            Thread.sleep(10000000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
