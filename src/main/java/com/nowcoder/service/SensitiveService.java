package com.nowcoder.service;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @Date: 18-9-3
 * @version： V1.0
 * @Author: Chandler
 * @Description: ${todo}
 */
@Service
public class SensitiveService implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(SensitiveService.class);

    /**
     * 默认敏感词替换符
     */
    private static final String DEFAULT_REPLACEMENT = "敏感词";

    private class TrieNode{
        /**
         * true的关键字：终结 ，false：继续
         */
        private boolean end = false;
        /**
         * key下一个字符，value为对应的结点
         */
        private Map<Character,TrieNode> subNodes = new HashMap<>();

        /**
         * 添加node到指定位置的key
         * @param key
         * @param node
         */
        void addSubNode(Character key,TrieNode node){
            subNodes.put(key,node);
        }

        /**
         * 获取下一个结点
         * @param key
         * @return
         */
        TrieNode getNextNode(Character key){
            return subNodes.get(key);
        }

        boolean isKeywordEnd(){
            return end;
        }

        void setKeywordEnd(boolean end){
            this.end = end;
        }

        public int getSubNodeCount() {
            return subNodes.size();
        }
    }

    private boolean isSymbol(char c){
        int ic = c;
        return !CharUtils.isAsciiAlphanumeric(c) && (ic<0x2E80 || ic>0x9FFF);
    }

    public TrieNode rootnode = new TrieNode();

    public String filter(String text) {
        if (StringUtils.isBlank(text)) {
            return text;
        }
        String replacement = DEFAULT_REPLACEMENT;
        TrieNode tempNode = rootnode;
        StringBuilder resut = new StringBuilder();
        int begin = 0; //回滚数
        int position =0; //当前比较的位置
        while (position<text.length()){
            char c = text.charAt(position);
            if (isSymbol(c)) {
                if (tempNode==rootnode){
                    resut.append(c);
                    ++begin;
                }
                ++position;
                continue;
            }
            tempNode = tempNode.getNextNode(c);
            //匹配的位置结束
            if (tempNode==null){
                //以begin开始位置不存在敏感词
                resut.append(text.charAt(begin));
                position = begin + 1;
                begin = position;
                tempNode = rootnode;
            }else if (tempNode.isKeywordEnd()){
                //发现敏感词，从begin到position位置替换掉
                resut.append(DEFAULT_REPLACEMENT);
                position = position + 1;
                begin = position;
                tempNode = rootnode;
            }else {
                ++position;
            }
        }
        resut.append(text.substring(begin));

        return resut.toString();
    }

    private void addWord(String lineText){
        TrieNode tempNode = rootnode;
        for (int i = 0; i < lineText.length(); i++) {
            Character ch = lineText.charAt(i);
            //过滤空格
            if (isSymbol(ch)) {
                continue;
            }
            TrieNode node = tempNode.getNextNode(ch);

            if (node == null) {
                node = new TrieNode();
                tempNode.addSubNode(ch,node);
            }

            tempNode = node;

            if (i==lineText.length()-1) {
                //设置结束标记
                tempNode.setKeywordEnd(true);
            }
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        try{
            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("SensitiveWorks.txt");
            InputStreamReader reader = new InputStreamReader(in);
            BufferedReader buffer = new BufferedReader(reader);
            String lineTxt;
            while ((lineTxt=buffer.readLine())!=null){
                lineTxt = lineTxt.trim();
                addWord(lineTxt);
            }
            reader.close();
        }catch (Exception e){
            logger.error("读取敏感词文件失败" + e.getMessage());
        }
    }
    public static void main(String[] argv) {
        SensitiveService s = new SensitiveService();
        s.addWord("色情");
        s.addWord("赌博");
        s.addWord("好色");
        System.out.print(s.filter("赌博你好X色**情XX"));
    }
}
