package com.zhipu.oapi.utils;

import java.util.*;
import java.util.stream.Collectors;

public class StringUtils {
    public static boolean isEmpty(String str) {
        return null == str || "".equals(str.trim());
    }

    public static boolean isNotEmpty(String str) {
        return null != str && !"".equals(str.trim());
    }

    /**
     * @param tpl。the template, use {Name} as placeholder, e.g.: "My name is {name}"
     * @param params. the k-v pair. key should exist  in tpl. with the template above, key can be "name"
     * @return
     */
    public static String formatString(final String tpl, Map<String, String> params) {
        List<KvWrapper> paramWrappers = params.entrySet().stream().map(StringUtils::toWrapper).collect(Collectors.toList());
        paramWrappers.stream().forEach(x -> {
            String placeHolderName = String.format("{%s}", x.key);
            int idx = tpl.indexOf(placeHolderName);
            x.idxInTpl = idx;
        });
        List<String> sortedValues = paramWrappers.stream().sorted(Comparator.comparing(KvWrapper::getIdxInTpl)).map(x -> x.getValue()).collect(Collectors.toList());
        String standardTpl = tpl;
        for (String s : params.keySet()) {
            String placeHolderName = String.format("{%s}", s);
            standardTpl = standardTpl.replace(placeHolderName, "%s");
        }
        return String.format(standardTpl, sortedValues.toArray());
    }

    public static String formatString(final String tpl, String placeHolderPrefix, String placeHolderSuffix, Map<String, String> params) {
        List<KvWrapper> paramWrappers = params.entrySet().stream().map(StringUtils::toWrapper).collect(Collectors.toList());
        paramWrappers.stream().forEach(x -> {
            String placeHolderName = placeHolderPrefix + x.key + placeHolderSuffix;
            int idx = tpl.indexOf(placeHolderName);
            x.idxInTpl = idx;
        });
        List<String> sortedValues = paramWrappers.stream().sorted(Comparator.comparing(KvWrapper::getIdxInTpl)).map(x -> x.getValue()).collect(Collectors.toList());
        String standardTpl = tpl;
        for (String s : params.keySet()) {
            String placeHolderName = placeHolderPrefix + s + placeHolderSuffix;
            standardTpl = standardTpl.replace(placeHolderName, "%s");
        }
        return String.format(standardTpl, sortedValues.toArray());
    }

    private static KvWrapper toWrapper(Map.Entry<String, String> kv) {
        KvWrapper wrapper = new KvWrapper();
        wrapper.key = kv.getKey();
        wrapper.value = kv.getValue();
        return wrapper;
    }

    private static class KvWrapper {
        String key;
        String value;
        int idxInTpl;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getIdxInTpl() {
            return idxInTpl;
        }

        public void setIdxInTpl(int idxInTpl) {
            this.idxInTpl = idxInTpl;
        }
    }

    public static void main(String[] args) throws Exception {
        Map<String, String> params1 = new HashMap<>();
        params1.put("name", "wangxiaodong");
        String result = formatString("My name is {name}", params1);
        System.out.println(result);
        Map<String, String> params2 = new HashMap<>();
        params2.put("name", "kunkun");
        params2.put("like1", "sing");
        params2.put("like2", "hop");
        params2.put("like3", "basketball");
        result = formatString("My name is {name}, I like {like1}, {like2}, {like3}", params2);
        System.out.println(result);

        result = formatString("My name is {name}, I like {like1}, {like2}, {like3}", "{", "}", params2);
        System.out.println(result);
    }
}
