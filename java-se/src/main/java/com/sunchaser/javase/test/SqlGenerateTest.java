package com.sunchaser.javase.test;

/**
 * @author sunchaser
 * @date 2020/3/12
 * @since 1.0
 */
public class SqlGenerateTest {
    public static void main(String[] args) {
        /**
        String dropTable = "DROP TABLE if exists cashcouponcoredb_%s.cash_coupon_business_mapping_%s;";
        String createTable = "CREATE TABLE cashcouponcoredb_%s.cash_coupon_business_mapping_%s like cashcouponcoredb_00.cash_coupon_business_mapping;";
        int dbCount = 8;
        int tbCount = 64;
        for (int i = 0;i < dbCount;i++) {
            int index = i;
            for (int j = 0;j < tbCount;j++) {
                String dbIndex = null;
                if (index < 10) {
                    dbIndex = "00" + index;
                } else if (index < 100) {
                    dbIndex = "0" + index;
                } else {
                    dbIndex = String.valueOf(index);
                }
                index = index + 8;
                String dropTableFormat = String.format(dropTable, "0" + i, dbIndex);
                String createTableFormat = String.format(createTable, "0" + i, dbIndex);
                System.out.println(createTableFormat);
            }
        }
         **/
        String dropTable = "DROP TABLE if exists cashcouponcoredb_%s.seq_%s;";
        String createTable = "CREATE TABLE cashcouponcoredb_%s.seq_%s like cashcouponcoredb_00.seq;";
        int dbCount = 8;
        int tbCount = 8;
        for (int i = 0; i < dbCount; i++) {
            int index = i;
            for (int j = 0; j < tbCount; j++) {
                String dbIndex = null;
                if (index < 10) {
                    dbIndex = "0" + index;
                } else {
                    dbIndex = String.valueOf(index);
                }
                index = index + 8;
                String dropTableFormat = String.format(dropTable, "0" + i, dbIndex);
                String createTableFormat = String.format(createTable, "0" + i, dbIndex);
                System.out.println(createTableFormat);
            }
        }
    }
}
