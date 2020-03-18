package com.sunchaser.javase.test;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayMarketingCampaignDrawcampTriggerRequest;
import com.alipay.api.request.AlipayMarketingVoucherTemplatedetailQueryRequest;
import com.alipay.api.response.AlipayMarketingCampaignDrawcampTriggerResponse;
import com.alipay.api.response.AlipayMarketingVoucherTemplatedetailQueryResponse;

import java.util.Date;

/**
 * @author sunchaser
 * @date 2019/12/5
 * @description
 * @since 1.0
 */
public class Test {
    public static void main(String[] args) throws AlipayApiException {

        /**
        AlipayClient alipayClient = new DefaultAlipayClient(
                "https://openapi.alipay.com/gateway.do",
                "2019092467813194",
                "MIIEuwIBADANBgkqhkiG9w0BAQEFAASCBKUwggShAgEAAoIBAQCEk5wUFlv4BfMHhNFfdPzq0/SUo1LT0JiPMsdAkcVPViF/Vrj6m7gQzBY0ngtFxy30pMwU97xBbJ/m8tUwD9bomyDSJ9PZLCm2fvWpoS0BeaauywUAM71Q4JP6kxA0cq0W2vVpzZF1t4OHDvhjwDmjVRGeD9gpHZof7HyaZCu5/QxrhSB+q0rYzOUqRzmk0HueBbVpZLkRfiZb7czfHgUmgcx2XUtwtifd5VxUKKdyC3KRrYMJD1teRokkXVwtILmXSEoleDFpPRkBHXTmwHO3k9FlzjA/vjL2UJLJHdGVeS2k7Co+5AQGQ5YbPCbcWWUXf5yi0GIPmR5QfKR11C/rAgMBAAECggEAT5zAfkOLCN6xuVMl09fRxUZwug11wKKj6mIIyxp+TQ0g4nH2dO+r0dJshz5c8JMSYrXPo8u8reEICMzL/qeUW2xEgk7OZbyic+e7qBiUiS0/Tfm2UA/B4N5rnm5Prd8rV+02sEl0zybbgfAYcvZM5mtAQBWtrcsL8Z22mZQLugyA8IldYGPmuZQlKq3NgVJBIog9tEKaoSJbj08bE9Juj0ZCwJyKt9T/89yj27Vxof3gATuXrCIlhQ1iBg6wR7IjAKra62Hts2mBKRcVUiQfZMIjeYeYHd+vgi9yZRKgCVGKDVl0ms9JKk63LjPdJ6l6ECpew/r2bzFSIZ/x9ehcCQKBgQC6m3tA0Mq5mU2RcpLMDK/hGItz6+J9dgEFehPYg3Uo7T91Cf+ZXtfYT7tTLJxKMPkTG33RkOx+jQ6+K60xeoxlcddU/hkTnE1vznKQJ/n8hi7BLHbP/M/HKNQEnb1w6PedosipYjVsHxa6FjjwmhZ481YY9JxvnlMzA24PHFYR9QKBgQC14IvG6QE/SIU/hWPA1HzhB4BbGm/txYRYKA3pv8DpsNH5z+84iwEbWRSXSLpQBuOC/Z0/zfnU++xV62gzc15hC/Zm00H+U+D5xSf3h6ZhR1u73Tz0mJAhVeT31N4nzvccKdkFzP/aRmuuNnFjtICx1W/pJ42t5+rcj6+iWpOuXwKBgDXn/G744ddDxfiiVG3FrHkmEgLmvYLot9rRLTJ6pzmvb4lr3f58O3YtcjvQw0VbaFhwtKP5bZgRP5UIIPwOHsifXkkbLjtQB76osMecBMdCK/d51Mi4i31I+hZrSvJ3GjDbeYQU7sHRKUuQZ9p5aMx/3vtOjnJgFkDCWOTclrCRAn8Zk3oUfq+uNoSGajuc4kStYGCsbwtOZHg37WoHdO+r7TGZ76O0T9fYAK7kl1Wb61wTD5cNYenACa0yZ+g/U/nxq7z6uLqPCTyZ4x+7r1e2VlKQGIx52Pprtah9MXIMJw6Y50mdhzZze3OpC/7mBnwShfE22FkUG4jre0k3i4U5AoGBAJBRdrHjiBkcMMLrJVUev+dv1jZTsGHN6pPjvRkVo+fI7BA8ltAUBZK7hBZ1o6x2SYG0t/dAZKR6LGQXf7Npd6J0HjUwIDsWDBs1Hb2SAb2JbhhZ4t/iybWH4bBhfPCP3oLyr4O3GESuqjGIAGYjMjqxNJ4d/dGMGVrwBSQNEjOs",
                "json",
                "GBK",
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAplxH1NIOdBQed1uxY/KqG58BfHLkYZ/g33aLg7KQ5irD7o219EJxbPe6qx64kDCnK5NNml8ZaoH7QJlii6cl3Uwd9vXw2wfrETcCGKZeciV/uzQtAvOtGVsmdc6U6S2sQYocmTbNw9r9BrvKeWfnF16/dHmA/WVwj9O1FaQoIovMKRz2zAtv5I+YohxeZ/JDom3rrVq+G5kvmUGleoFhEfiGCfDzfoyS6I8OMrApP1fACDlyRYf181yc+B3IUcsjiAk3x0RN0bOORVLXgyveN4JEyt/BeJbU7ryUCyQaxhbpwvoxF09LESdAYMROcRJboTins8MEwq+AyMn/eyHrLwIDAQAB",
                "RSA2");
        AlipayMarketingCampaignDrawcampTriggerRequest request = new AlipayMarketingCampaignDrawcampTriggerRequest();
        request.setBizContent("{" +
                "\"user_id\":\"2088502933325877\"," +
                "\"login_id\":\"474298445@qq.com\"," +
                "\"camp_id\":\"CP1644968\"," +
                "\"bind_mobile\":\"\"," +
                "\"camp_source\":1," +
                "\"json_ua\":\"1111111111111111\"," +
                "\"channel_info\":\"{level1:mapp,level2:hz}\"," +
                "\"client_ip\":\"111.111.111.111\"" +
                " }");
        AlipayMarketingCampaignDrawcampTriggerResponse response = alipayClient.execute(request);
        System.out.println(JSON.toJSONString(response));
        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
        **/
        /**
        AlipayClient alipayClient = new DefaultAlipayClient(
                "https://openapi.alipay.com/gateway.do",
                "2019092467813194",
                "MIIEuwIBADANBgkqhkiG9w0BAQEFAASCBKUwggShAgEAAoIBAQCEk5wUFlv4BfMHhNFfdPzq0/SUo1LT0JiPMsdAkcVPViF/Vrj6m7gQzBY0ngtFxy30pMwU97xBbJ/m8tUwD9bomyDSJ9PZLCm2fvWpoS0BeaauywUAM71Q4JP6kxA0cq0W2vVpzZF1t4OHDvhjwDmjVRGeD9gpHZof7HyaZCu5/QxrhSB+q0rYzOUqRzmk0HueBbVpZLkRfiZb7czfHgUmgcx2XUtwtifd5VxUKKdyC3KRrYMJD1teRokkXVwtILmXSEoleDFpPRkBHXTmwHO3k9FlzjA/vjL2UJLJHdGVeS2k7Co+5AQGQ5YbPCbcWWUXf5yi0GIPmR5QfKR11C/rAgMBAAECggEAT5zAfkOLCN6xuVMl09fRxUZwug11wKKj6mIIyxp+TQ0g4nH2dO+r0dJshz5c8JMSYrXPo8u8reEICMzL/qeUW2xEgk7OZbyic+e7qBiUiS0/Tfm2UA/B4N5rnm5Prd8rV+02sEl0zybbgfAYcvZM5mtAQBWtrcsL8Z22mZQLugyA8IldYGPmuZQlKq3NgVJBIog9tEKaoSJbj08bE9Juj0ZCwJyKt9T/89yj27Vxof3gATuXrCIlhQ1iBg6wR7IjAKra62Hts2mBKRcVUiQfZMIjeYeYHd+vgi9yZRKgCVGKDVl0ms9JKk63LjPdJ6l6ECpew/r2bzFSIZ/x9ehcCQKBgQC6m3tA0Mq5mU2RcpLMDK/hGItz6+J9dgEFehPYg3Uo7T91Cf+ZXtfYT7tTLJxKMPkTG33RkOx+jQ6+K60xeoxlcddU/hkTnE1vznKQJ/n8hi7BLHbP/M/HKNQEnb1w6PedosipYjVsHxa6FjjwmhZ481YY9JxvnlMzA24PHFYR9QKBgQC14IvG6QE/SIU/hWPA1HzhB4BbGm/txYRYKA3pv8DpsNH5z+84iwEbWRSXSLpQBuOC/Z0/zfnU++xV62gzc15hC/Zm00H+U+D5xSf3h6ZhR1u73Tz0mJAhVeT31N4nzvccKdkFzP/aRmuuNnFjtICx1W/pJ42t5+rcj6+iWpOuXwKBgDXn/G744ddDxfiiVG3FrHkmEgLmvYLot9rRLTJ6pzmvb4lr3f58O3YtcjvQw0VbaFhwtKP5bZgRP5UIIPwOHsifXkkbLjtQB76osMecBMdCK/d51Mi4i31I+hZrSvJ3GjDbeYQU7sHRKUuQZ9p5aMx/3vtOjnJgFkDCWOTclrCRAn8Zk3oUfq+uNoSGajuc4kStYGCsbwtOZHg37WoHdO+r7TGZ76O0T9fYAK7kl1Wb61wTD5cNYenACa0yZ+g/U/nxq7z6uLqPCTyZ4x+7r1e2VlKQGIx52Pprtah9MXIMJw6Y50mdhzZze3OpC/7mBnwShfE22FkUG4jre0k3i4U5AoGBAJBRdrHjiBkcMMLrJVUev+dv1jZTsGHN6pPjvRkVo+fI7BA8ltAUBZK7hBZ1o6x2SYG0t/dAZKR6LGQXf7Npd6J0HjUwIDsWDBs1Hb2SAb2JbhhZ4t/iybWH4bBhfPCP3oLyr4O3GESuqjGIAGYjMjqxNJ4d/dGMGVrwBSQNEjOs",
                "json",
                "GBK",
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAplxH1NIOdBQed1uxY/KqG58BfHLkYZ/g33aLg7KQ5irD7o219EJxbPe6qx64kDCnK5NNml8ZaoH7QJlii6cl3Uwd9vXw2wfrETcCGKZeciV/uzQtAvOtGVsmdc6U6S2sQYocmTbNw9r9BrvKeWfnF16/dHmA/WVwj9O1FaQoIovMKRz2zAtv5I+YohxeZ/JDom3rrVq+G5kvmUGleoFhEfiGCfDzfoyS6I8OMrApP1fACDlyRYf181yc+B3IUcsjiAk3x0RN0bOORVLXgyveN4JEyt/BeJbU7ryUCyQaxhbpwvoxF09LESdAYMROcRJboTins8MEwq+AyMn/eyHrLwIDAQAB",
                "RSA2");
        AlipayMarketingVoucherSendRequest request = new AlipayMarketingVoucherSendRequest();
        request.setBizContent("{" +
                "\"template_id\":\"20200113000730017625003UL65H\"," +
                "\"login_id\":\"15207156746\"," +
                "\"taobao_nick\":\"\"," +
                "\"user_id\":\"2088022820818355\"," +
                "\"out_biz_no\":\"2016122814164328aadaee_1aee_4\"," +
                "\"amount\":0.01," +
                "\"memo\":\"测试发劵接口\"," +
                "\"extend_info\":\"{\\\"alipayMiniAppToken\\\":\\\"20191125000347843\\\"}\"" +
                "  }");
        AlipayMarketingVoucherSendResponse response = alipayClient.execute(request);
        System.out.println(JSON.toJSONString(response));
        if(response.isSuccess()){
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
        **/

        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                "2018062860457318",
                "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC3wbPBOJBUapZLRJILtNACmIBnBEIK65QOvUuu/c0U4DeysOrWUsTzVo91x0rRpXvb62FIjQ28030EkpUFkt7Eh6HNHu0Jzfw3baIogOtXghxG/MpO4dlXE1//gK5bApDcC9HNi6nY5xoDqTjhE++59EuKw7lhnJs+TVtqULhg/V7pneKK4KAUnbjOOMYeSY29EvgixAhTkU26fYKhziyrUWyZrFuZIbmWnwbr6ADtULWuyAW01W8DgAPUnC3pn3W47a7jBPaLesllmPKfLVCiHymrAmCaDbSCb34/509lZ3x4vsDmvYRXsKWXpqLO4h9lAOTfAHAufy43rE/adQHDAgMBAAECggEAGeqEypyKpo090zHBk0sWBzew7FaKZfXhtM53zR9YfDg/QsF27XFQ+6zaoNbRs42uUU76MnxKUrZVLH7r65RSPM+QP1i8P6TAjKS2uRWtm3LrQAvJEKUDvScrY+KnNIjBG3fDSEUJWGEHpzqHnh7ssYJF4TVHgP/QWVIZsX3e8zarh4XakPk+Ipxmjn+fP6iNnyrPb5pJW9DQDB/lS52Hfft9V4SRScwBEA/2o3onZoIlWGa1bDsreW++ppfQ9w1HPCeoXovkbSD/b9NMmW8hD37TB97kjf1tH71+QZa/8yZ2ockhtR+sKQbjX4hVVLT75IwJ38/9X6WtDOpVxGZwGQKBgQDofE0Js0N2y16xsqPYJSTcAq2MK4TBIeWhCvpzeS6JOnQLanU3VEYh5aipy/G8aeP0LscSvbuBMYS/Auqmh3z3KecYmPHWZhWOE51muM+VtQUHkWne97wg5zNvcObTlDLLymB6dhehyd+tBBS71XY71CNQMov+02UCSF6QOX8S3QKBgQDKV6yTaVvgliUZXNSAF8gDd/e58rZuGt8camH6URDGtlQUE6jlqhvMF2lXPa+sG6+QbyKMy0TPwKpA9b3WoIdNW4NRdSK/SlS8jsGe/YAWh4OWYP/xCH34nITtg9VDEc+FN9xkWGck7GYiTbomNGD8z31SwoFRC0Bfp90IM4aNHwKBgEZa/5FU77CuoOyTzCOoMfxmzxBhRx6PXIec4+g4Hw4mY2HlCK4C0eqGLsGb138WaIbH9TTFuE3Dp5GKl2poDDhcNPRrrJwebp19cB/bA+8hRfFhXzG7tPIcJaOu/hu/eJPVndbRkBtFFWytlHy3f71hWIJ3mYg3fxTuw7dPvxX9AoGAH52TWXUc5fqP0WavzVPp7KDSFDvYRVvtgIMey/3eoS3b9UcdHMV3kmwWCYuZ50psJCU5QMaB+fQgpgW/o8lxWqTx38htpXX3x36GwlDZsqt55AVD+GVbSX0rR/FRYSlnQ2l3BjbJ5vsI+lMqws1sWhQRgNzjV8ZqSIfyAVYLsAECgYEAi6l3kwN4E5njAeXaMXo0lgDVQ3NA23mQeKbj2bWAPnceGtpvDNRWl3qHYAGUyF9llpL+faRcgKQNre7guCoZSv9MYMad19asaq/LRUg6hLWWGDs2Owpt84aLf2fnyq0/cjnkkuKWS5V2cmbzBnGRgpt/uSKKTareaWO8STZE+is=",
                "json","GBK",
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiz4NPDacunRBY+YC9YWY5/FOiyiD+nNRuamg43z2mHmKevieJ/2fO2FNNtRdFHp/TnD8l7mrVjaxDvwc+431AHg6ITnfrQci0HGZMrHqSpJi0JgV2XjHvfJSwCNus1M0MwD3tXW6imR6u+lj8YmOAJ1S9Q6tP+zOdVNfE/HBY2+6IvKK4DAcc8y+5ymd7Iq7Kw0DMTlQKYRczwkWt51yib4n8MHdaFNiaiiJrPnG5Rzc/t+MEbCzOCJQq3Pc8NXGRH7AYA9tC6SNU9hsnuGztJFQGF/SBlxpzDiywY+1cn0C9pTav8RlZkc+Fwv+U/f4eVPH17gsmQh1lOztOvOPeQIDAQAB",
                "RSA2");
        AlipayMarketingVoucherTemplatedetailQueryRequest request = new AlipayMarketingVoucherTemplatedetailQueryRequest();
        request.setBizContent("{" +
                "\"template_id\":\"20200213000730010012004604FQ\"" +
                "  }");
        AlipayMarketingVoucherTemplatedetailQueryResponse response = alipayClient.execute(request);
        System.out.println(JSON.toJSONString(response));
        System.out.println(new Date(1581868800000L));
        if(response.isSuccess()){
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
    }
}
