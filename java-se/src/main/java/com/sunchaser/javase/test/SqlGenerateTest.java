package com.sunchaser.javase.test;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Objects;

/**
 * @author sunchaser
 * @date 2020/3/12
 * @since 1.0
 */
public class SqlGenerateTest {

    private static final String ZERO_STRING = "0";

    /**
     * 建表语句模板：like 语法创建，需要有一个模板表
     * 共4个占位符：
     * 1：分库名前缀
     * 2：分表名前缀
     * 3：模板库全名
     * 4：模板表全名
     */
    private static final String CREATE_TABLE_BASE_TEMPLATE = "CREATE TABLE %s.%s like %s.%s;";

    /**
     * drop表：2个占位符
     * 1: 分库名前缀
     * 2: 分表名前缀
     */
    private static final String DROP_TABLE_BASE_TEMPLATE = "DROP TABLE if exists %s.%s;";

    /**
     * 修改表：3个占位符
     * 1：分库名前缀
     * 2：分表名前缀
     * 3：具体修改      main方法传入参数时只能传一个不带空格的字符串，有局限性
     */
    private static final String ALTER_TABLE_BASE_TEMPLATE = "ALTER TABLE %s.%s %s;";

    public enum ActionEnum {
        CREATE(7,"CREATE",CREATE_TABLE_BASE_TEMPLATE) {
            @Override
            String formatTemplate(String[] args) {
                String dbPrefix = args[1];
                String tbPrefix = args[2];
                String templateDbName = args[3];
                String templateTbName = args[4];
                dbPrefix += "_%s";
                tbPrefix += "_%s";
                return String.format(CREATE_TABLE_BASE_TEMPLATE, dbPrefix, tbPrefix, templateDbName, templateTbName);
            }

            @Override
            Integer getDbCount(String[] args) {
                return Integer.parseInt(args[5]);
            }

            @Override
            Integer getTbCount(String[] args) {
                return Integer.parseInt(args[6]);
            }
        },
        DROP(5,"DROP",DROP_TABLE_BASE_TEMPLATE) {
            @Override
            String formatTemplate(String[] args) {
                String dbPrefix = args[1];
                String tbPrefix = args[2];
                dbPrefix += "_%s";
                tbPrefix += "_%s";
                return String.format(DROP_TABLE_BASE_TEMPLATE,dbPrefix,tbPrefix);
            }

            @Override
            Integer getDbCount(String[] args) {
                return Integer.parseInt(args[3]);
            }

            @Override
            Integer getTbCount(String[] args) {
                return Integer.parseInt(args[4]);
            }
        },
        ALTER(6,"ALTER",ALTER_TABLE_BASE_TEMPLATE) {
            @Override
            String formatTemplate(String[] args) {
                String dbPrefix = args[1];
                String tbPrefix = args[2];
                String alterDetail = args[3];
                dbPrefix += "_%s";
                tbPrefix += "_%s";
                return String.format(ALTER_TABLE_BASE_TEMPLATE,dbPrefix,tbPrefix,alterDetail);
            }

            @Override
            Integer getDbCount(String[] args) {
                return Integer.parseInt(args[4]);
            }

            @Override
            Integer getTbCount(String[] args) {
                return Integer.parseInt(args[5]);
            }
        },
        ;
        private Integer argCount;
        private String action;
        private String template;

        private static final Map<String,ActionEnum> enumMap = Maps.newHashMap();

        static {
            for (ActionEnum actionEnum : ActionEnum.values())
                enumMap.put(actionEnum.action,actionEnum);
        }

        public static ActionEnum getActionEnumByAction(String action) {
            ActionEnum actionEnum = enumMap.get(action);
            ActionEnum lowerCaseActionEnum = enumMap.get(action.toLowerCase());
            ActionEnum upperCaseActionEnum = enumMap.get(action.toUpperCase());
            return actionEnum != null ? actionEnum : lowerCaseActionEnum != null ? lowerCaseActionEnum : upperCaseActionEnum;
        }

        ActionEnum(Integer argCount, String action, String template) {
            this.argCount = argCount;
            this.action = action;
            this.template = template;
        }

        abstract String formatTemplate(String[] args);
        abstract Integer getDbCount(String[] args);
        abstract Integer getTbCount(String[] args);

        public Integer getArgCount() {
            return argCount;
        }

        public String getAction() {
            return action;
        }

        public String getTemplate() {
            return template;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("ActionEnum{");
            sb.append("argCount=").append(argCount);
            sb.append(", action='").append(action).append('\'');
            sb.append(", template='").append(template).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

    /**
     * 建表：CREATE userdb user userdb user 8 64
     * 删表：DROP userdb user 8 64
     * @param args
     */
    public static void main(String[] args) {
        try {
            if (Objects.isNull(args) || args.length < 2) {
                System.out.println("输入参数有误，请查看帮助文档");
                return;
            }
            String action = args[0];
            ActionEnum actionEnum = ActionEnum.getActionEnumByAction(action);
            if (actionEnum == null || args.length != actionEnum.argCount) {
                System.out.println("输入参数有误，请查看帮助文档");
                return;
            }
            String formatTemplate = actionEnum.formatTemplate(args);
            Integer dbCount = actionEnum.getDbCount(args);
            Integer tbCount = actionEnum.getTbCount(args);
            System.out.println(generateShardingSqlString(dbCount, tbCount, formatTemplate));
        } catch (NumberFormatException nfe) {
            System.out.println("输入参数有误，请查看帮助文档");
            nfe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String generateShardingSqlString(Integer dbCount,Integer tbCount,String sqlTemplate) throws IllegalArgumentException {
        // check params
        if (Objects.isNull(dbCount) || Objects.isNull(tbCount) || StringUtils.isEmpty(sqlTemplate)) {
            throw new IllegalArgumentException("请检查输入参数");
        }
        if (dbCount > tbCount) {
            throw new IllegalArgumentException("分库数量不能大于分表数量");
        }
        int perTbCount = tbCount / dbCount;
        int sumTbCount = perTbCount * dbCount;
        if (sumTbCount != tbCount) {
            throw new IllegalArgumentException("分库与分表的数量不合理");
        }
        return generateShardingSqlStringCore(dbCount,tbCount,sqlTemplate);
    }

    /**
     * 利用String占位符生成分库分表的SQL
     *
     * 例如：8库 每库2表  共16表：
     * 库索引为：00 ~ 07
     * 00库的表索引为：00,08
     * 01库的表索引为：01,09
     * 02库的表索引为：02,10
     * 03库的表索引为：03,11
     * 04库的表索引为：04,12
     * 05库的表索引为：05,13
     * 06库的表索引为：06,14
     * 07库的表索引为：07,15
     *
     *
     * 例如8库 每库128表 共1024表：
     * 库索引为：00 ~ 07
     * 00库的表索引为：0000,0008,0016....1015
     * 01库的表索引为：0001,0009,0017....1016
     * 02库的表索引为：0002,0010,0018....1017
     * ......
     * 08库的表索引为：0007,0015,0023....1023
     *
     * 例如500库 每库2表 共1000表：
     * 库索引为：000~499
     * 000库的表索引为：0000,0500
     * 001库的表索引为：0001,0501
     * ...
     * 499库的表索引为：0499,0999
     *
     * 生成的sql：从第0库开始往后依次生成，每个库的sql全部生成完了再生成下一个库的sql。
     *
     * @param dbCount 一共几个库
     * @param tbCount 一共几个表
     * @param sqlTemplate SQL模板
     * @return 分库分表建表SQL语句
     */
    private static String generateShardingSqlStringCore(Integer dbCount,Integer tbCount,String sqlTemplate) throws IllegalArgumentException {
        // 每个库几个表
        int perTbCount = tbCount / dbCount;
        // 确定库总数的位数：几位数
        int tempDbCount = dbCount;
        int dbDigits = 0;
        while (tempDbCount > 0) {
            tempDbCount = tempDbCount / 10;
            dbDigits++;
        }
        if (dbDigits == 1) {
            dbDigits++;
        }
        // 确定表总数的位数：几位数
        int tempTbCount = tbCount;
        int tbDigits = 0;
        while (tempTbCount > 0) {
            tempTbCount = tempTbCount / 10;
            tbDigits++;
        }
        StringBuilder sb = new StringBuilder();
        // 外层循环库的个数，每次循环生成一个库的全部sql
        for (int i = 0;i < dbCount;i++) {
            // 表初始索引 = 当前第几库
            int tbIndex = i;
            // 内层循环每个库的表个数，每次循环生成当前库的所有表的sql
            for (int j = 0;j < perTbCount;j++) {
                // 将i格式化，前面补“0”，直至dbDigits位
                StringBuilder dbI = new StringBuilder(String.valueOf(i));
                while (dbI.length() < dbDigits) {
                    dbI.insert(0, ZERO_STRING);
                }
                // 将tbIndex格式化，前面补“0”，直至tbDigits位
                StringBuilder tbI = new StringBuilder(String.valueOf(tbIndex));
                while (tbI.length() < tbDigits) {
                    tbI.insert(0,ZERO_STRING);
                }
                String format = String.format(sqlTemplate, dbI.toString(), tbI.toString());
                tbIndex += dbCount;
                sb.append(format).append("\n");
            }
        }
        return sb.toString();
    }
}
