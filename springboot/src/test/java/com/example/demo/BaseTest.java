package com.example.demo;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BaseTest {

    private static void breakAndForTest(){

        List<Integer> collect = IntStream.range(0, 100).boxed().collect(Collectors.toList());
        List<Integer> collect1 = IntStream.range(0, 100).boxed().collect(Collectors.toList());
        AtomicInteger atomicInteger = new AtomicInteger();
        for (Integer i: collect) {
            for (Integer j: collect1 ) {
                if (j == 2){
                    break;
                }
                atomicInteger.incrementAndGet();
                System.out.println(i + "|" +j);
            }
        }
        System.out.printf(String.valueOf(atomicInteger.get()));
    }
    public static Boolean checkQadInfo(JSONObject ai,List<JSONObject> blackQadInfo1){
        if(CollectionUtils.isEmpty(blackQadInfo1)){
            return true;
        }
        if(null==ai){
            return false;
        }

        for (JSONObject jsonObject : blackQadInfo1) {
            for (String key : jsonObject.keySet()) {
                String ori = ai.getString(key);
                if(StringUtils.isNotEmpty(ori)){
                    if(ori.startsWith(jsonObject.getString(key))){
                        continue;
                    }else {
                        return true;
                    }

                }else {
                    return true;
                }
            }
        }
        return false;
    }

    private static void test(int num){
        num =  num + 1;
    }


    public static void main(String[] args) throws UnsupportedEncodingException {

            /*String jsonStr = "{\"wi\":{\"wifiscanf\":[{\"bssid\":\"00:0d:66:4b:14:d6\",\"level\":-7,\"ssid\":\"Tenda_725C\",\"frequency\":2421,\"capabilities\":\"[WPA-PSK-CCMP+TKIP][WPA2-PSK-CCMP+TKIP][ESS]\",\"describeContents\":0},{\"bssid\":\"00:e0:4f:88:ce:54\",\"level\":-11,\"ssid\":\"HUAWEI_47DB\",\"frequency\":2415,\"capabilities\":\"[WPA2-EAP-CCMP][ESS]\",\"describeContents\":0},{\"bssid\":\"00:14:0e:ba:ee:32\",\"level\":-29,\"ssid\":\"Tplink_3668\",\"frequency\":5394,\"capabilities\":\"[WPA2-PSK-CCMP][ESS]\",\"describeContents\":0},{\"bssid\":\"00:11:11:6c:c7:8a\",\"level\":-38,\"ssid\":\"D-LINK_4A28\",\"frequency\":2454,\"capabilities\":\"[WPA-PSK-CCMP+TKIP][WPA2-PSK-CCMP+TKIP][ESS]\",\"describeContents\":0},{\"bssid\":\"00:23:ac:b7:3b:09\",\"level\":-59,\"ssid\":\"HUAWEI_E23D\",\"frequency\":5329,\"capabilities\":\"[WPA-PSK-CCMP][WPA2-PSK-CCMP][WPS][ESS]\",\"describeContents\":0},{\"bssid\":\"00:17:e8:d8:88:2c\",\"level\":-74,\"ssid\":\"HORNOR-9560\",\"frequency\":5318,\"capabilities\":\"[WPA-PSK-CCMP+TKIP][WPA2-PSK-CCMP+TKIP][ESS]\",\"describeContents\":0},{\"bssid\":\"00:1c:d6:c1:9e:81\",\"level\":-85,\"ssid\":\"HORNOR-773D\",\"frequency\":5316,\"capabilities\":\"[WPA-PSK-CCMP+TKIP][WPA2-PSK-CCMP+TKIP][WPS][ESS]\",\"describeContents\":0},{\"bssid\":\"00:50:e4:57:32:b3\",\"level\":-94,\"ssid\":\"TP-LINK_88C1\",\"frequency\":2431,\"capabilities\":\"[WPA-PSK-CCMP+TKIP][WPA2-PSK-CCMP+TKIP][WPS][ESS]\",\"describeContents\":0},{\"bssid\":\"00:04:1f:53:d1:36\",\"level\":-98,\"ssid\":\"Tplink_4A2A\",\"frequency\":5345,\"capabilities\":\"[WPA-PSK-CCMP+TKIP][WPA2-PSK-CCMP+TKIP][WPS][ESS]\",\"describeContents\":0}],\"curWifi\":{\"bssid\":\"00:0d:66:4b:14:d6\",\"dns\":\"223.5.5.5\",\"ssid\":\"Tenda_725C\",\"mask\":\"255.255.255.0\",\"netgate\":\"192.168.209.1\",\"ip\":\"192.168.209.196\"}},\"ai\":{\"uid\":\"322082569926925\",\"soundInfo\":{\"music\":\"15,11\",\"ring\":\"7,0\",\"alarm\":\"7,6\",\"screenBrightness\":82,\"sys\":\"7,0\",\"vc\":\"5,4\",\"screenMode\":1},\"cpuName\":\"ARMv7 Processor rev 0 (v7l)\",\"adinfoLocal\":{\"appList\":{},\"fileList\":{\"f1\":[\"/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq\",\"/system/bin/su\",\"/system/xbin/su\"]}},\"isRoot\":false,\"maxCpuFreq\":\"2265600\",\"hasInstallWeixin\":true,\"version\":\"3.4.0-gd59db4e-dirty\",\"ramMemory\":\"1899548\",\"numCores\":4,\"adb\":false,\"imageList\":{\"total\":0,\"ls\":[]},\"cpuInfo\":{\"CPU implementer\":\" 0x51\",\"Serial\":\" 0000000000000000\",\"CPU architecture\":\" 7\",\"Hardware\":\" Qualcomm MSM 8974 HAMMERHEAD (Flattened Device Tree)\",\"CPU variant\":\" 0x2\",\"CPU revision\":\" 0\",\"CPU part\":\" 0x06f\",\"Revision\":\" 000b\",\"BogoMIPS\":\" 38.40\",\"processor\":\" 3\",\"Processor\":\" ARMv7 Processor rev 0 (v7l)\",\"Features\":\" swp half thumb fastmult vfp edsp neon vfpv3 tls vfpv4 idiva idivt \"},\"mc\":\"00:23:c2:10:5b:3c\",\"mediaList\":{\"total\":2,\"ls\":[\"Hangouts message#fe903c458551d06a8de2d2cac6dc9c8e#1970-08-17 05:43:43#id_10\",\"Hangouts video call#10f28a80cb4906c133839735cf7f8bf5#1970-08-17 05:43:43#id_11\"]},\"gid\":\"4AD3400E-7FB0-734E-A57B-DCB325B8252D\",\"longitude\":\"err1\",\"latitude\":\"err1\",\"pMd5\":\"e08ce7e9d916dce46390c2f21ea9188e\"},\"gi\":{\"networkType\":13,\"cellid\":4633967,\"simSerialNumber\":\"89700685275906585114\",\"deviceSoftwareVersion\":\"53\",\"subscriberId\":\"4600123773576911\",\"cellInfo\":{\"lte\":[{\"mcc\":460,\"ci\":4633967,\"tac\":4154,\"mnc\":460,\"pic\":168},{\"pic\":313},{\"pic\":15},{\"pic\":295},{\"pic\":296},{\"pic\":350},{\"pic\":112},{\"pic\":295}]},\"operator\":\"46001\",\"line1Number\":\"145uoSY0793\",\"lac\":4154,\"deviceId\":\"322082569926925\"},\"ri\":\"cd_1548769689641|md_Nexus 5|ad_a93c342af4fe6c1a|gd_4AD3400E-7FB0-734E-A57B-DCB325B8252D|im_322082569926925|lv_19|vs4.4.4|mc_00:23:c2:10:5b:3c|ud_1548772651510\"}\n";
            jsonStr = "{\"b\":1,\"info\":\"{\"wi\":{\"curWifi\":{}},\"ai\":{\"cid\":\"C1001\",\"uid\":\"5EC6BD74-83F5-4998-AD6F-871D664E2C7A\",\"longitude\":\"108.955128\",\"isRoot\":0,\"vid\":\"80011169\",\"aid\":\"760B2EA1-0387-4904-AE73-BE5ABAFCFE1C\",\"latitude\":\"34.190859\",\"sid\":\"DED98557-B2D7-154B-BE18-F6B1AC950129\",\"hasInstallWeixin\":1,\"gid\":\"CB7AB466-9B79-56ED-635A-F63705D733A1\",\"pid\":\"10010\"},\"gi\":{\"networkIp\":{\"ip\":\"10.97.27.29\",\"broadcastIp\":\"10.97.27.29\",\"mask\":\"255.255.255.255\"},\"operator\":\"46000\",\"networkTypeString\":\"CTRadioAccessTechnologyLTE\"}}\",\"c\":{\"pid\":\"10010\",\"ip\":\"117.136.86.142\",\"model\":\"iPhone7,1\",\"gid\":\"CB7AB466-9B79-56ED-635A-F63705D733A1\",\"uid\":\"5EC6BD74-83F5-4998-AD6F-871D664E2C7A\",\"cid\":\"C1001\",\"vid\":\"80011169\",\"t\":\"p_qadInfo\",\"cactiTitle\":\"qadInfo\",\"idfa\":\"D9D1EE14-8267-426E-9FD1-0C74EE53F193\",\"un\":\"fsfe1132\",\"catom\":\"\",\"sid\":\"DED98557-B2D7-154B-BE18-F6B1AC950129\",\"lat\":\"34.190859\",\"lgt\":\"108.955128\",\"usid\":\"151516523\",\"cp\":2,\"mno\":\"46000\",\"msg\":\"\",\"qrid\":\"1548920092282\",\"adid\":\"\",\"realIp\":\"117.136.86.142\",\"re\":0,\"avers\":\"\",\"tsv\":\"\",\"aid\":\"760B2EA1-0387-4904-AE73-BE5ABAFCFE1C\",\"iid\":\"5EC6BD74-83F5-4998-AD6F-871D664E2C7A\",\"nt\":\"CTRadioAccessTechnologyLTE\",\"mac\":\"02:00:00:00:00:00\",\"uniqueId\":\"5EC6BD74-83F5-4998-AD6F-871D664E2C7A\",\"brush\":\"{\"lt\":0}\",\"pitcherTid\":\"pf_pitcher_190131.153452.10.88.182.157.19450.3344735791_1\",\"osVersion\":\"11.2.6\",\"key\":\"2019-01-31 15:34:52.595\",\"ref\":\"|HomeVC|\",\"port\":\"47946\",\"orialCparam\":{\"pid\":\"10010\",\"ip\":\"117.136.86.142\",\"model\":\"iPhone7,1\",\"gid\":\"CB7AB466-9B79-56ED-635A-F63705D733A1\",\"uid\":\"5EC6BD74-83F5-4998-AD6F-871D664E2C7A\",\"cid\":\"C1001\",\"vid\":\"80011169\",\"t\":\"p_qadInfo\",\"cactiTitle\":\"qadInfo\",\"idfa\":\"D9D1EE14-8267-426E-9FD1-0C74EE53F193\",\"un\":\"fsfe1132\",\"catom\":\"\",\"sid\":\"DED98557-B2D7-154B-BE18-F6B1AC950129\",\"lat\":\"34.190859\",\"lgt\":\"108.955128\",\"usid\":\"151516523\",\"cp\":2,\"mno\":\"46000\",\"msg\":\"\",\"qrid\":\"1548920092282\",\"adid\":\"\",\"realIp\":\"117.136.86.142\",\"re\":0,\"avers\":\"\",\"tsv\":\"\",\"aid\":\"760B2EA1-0387-4904-AE73-BE5ABAFCFE1C\",\"iid\":\"5EC6BD74-83F5-4998-AD6F-871D664E2C7A\",\"nt\":\"CTRadioAccessTechnologyLTE\",\"mac\":\"02:00:00:00:00:00\",\"uniqueId\":\"5EC6BD74-83F5-4998-AD6F-871D664E2C7A\",\"brush\":\"{\"lt\":0}\",\"pitcherTid\":\"pf_pitcher_190131.153452.10.88.182.157.19450.3344735791_1\",\"osVersion\":\"11.2.6\",\"key\":\"2019-01-31 15:34:52.595\",\"ref\":\"|HomeVC|\",\"port\":\"47946\"}}}";
            jsonStr = StringUtils.replace(jsonStr, "\"{","{");
            jsonStr = StringUtils.replace(jsonStr, "}\"","}");
            JSONObject jsonobect = JSON.parseObject(jsonStr);
            //        System.out.println(jsonobect.getJSONObject("ai"));
            System.out.println(jsonobect.getJSONObject("ai").getString("soundInfo"));
            String param = "{\"soundInfo\":{\"music\":\"15,11\",\"ring\":\"7,0\",\"alarm\":\"7,6\",\"screenBrightness\":82,\"sys\":\"7,0\",\"vc\":\"5,4\",\"screenMode\":1},\"isRoot\":false}";
            String param1 = "{\"pMd5\":\"e08ce7e9d916dce46390c2f21ea9188e1\"}";
            List<JSONObject> paramList = Lists.newArrayList();
            paramList.add(JSON.parseObject(param));
            paramList.add(JSON.parseObject(param1));
            System.out.println(checkQadInfo(jsonobect.getJSONObject("ai"),paramList));
*/
        /*int num = 0;
        test(num);
        System.out.println(num);*/

        DateTime dateTime = DateTime.now().plusDays(-2);
        System.out.println(dateTime);

        DateTime parse = DateTime.parse("20180304").plusDays(-2);
        System.out.println(parse);

    }


}
