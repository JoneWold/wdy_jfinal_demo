package com.wdy.bizz.hello;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wdy.vo.TreeNodeVo;
import org.apache.commons.collections4.map.HashedMap;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wgch
 * @Description 树形结构
 * @date 2020/9/15
 */
public class TestTree {

    @Test
    public void test() {
        Map<String, TreeNodeVo> nodes = new HashedMap<>();
        //模拟数据库存储树结构
        nodes.put("1", new TreeNodeVo("1", "root", "1", "0"));
        nodes.put("2", new TreeNodeVo("2", "a", "2", "1"));
        nodes.put("3", new TreeNodeVo("3", "b", "3", "1"));
        nodes.put("4", new TreeNodeVo("4", "c", "4", "1"));
        nodes.put("5", new TreeNodeVo("5", "d", "5", "2"));
        nodes.put("6", new TreeNodeVo("6", "e", "6", "2"));
        nodes.put("7", new TreeNodeVo("7", "f", "7", "3"));
        nodes.put("8", new TreeNodeVo("8", "g", "8", "7"));
        System.out.println("result:" + JSON.toJSONString(this.getNodeJson("0", nodes)));

    }


    /**
     * 递归处理   数据库树结构数据->树形json
     *
     * @param parentCode
     * @param nodes
     * @return
     */
    private JSONArray getNodeJson(String parentCode, Map<String, TreeNodeVo> nodes) {

        //当前层级当前node对象
        TreeNodeVo cur = nodes.get(parentCode);
        //当前层级当前点下的所有子节点（实战中不要慢慢去查,一次加载到集合然后慢慢处理）
        List<TreeNodeVo> childList = this.getChildNodes(parentCode, nodes);

        JSONArray childTree = new JSONArray();
        for (TreeNodeVo node : childList) {
            JSONObject o = new JSONObject();
            o.put("name", node.getName());
            o.put("code", node.getCode());
            JSONArray children = this.getNodeJson(node.getCode(), nodes);
            if (!children.isEmpty()) {
                o.put("children", children);
            }
            childTree.fluentAdd(o);
        }
        return childTree;
    }


    /**
     * 获取当前节点的所有子节点
     *
     * @param parentCode
     * @param nodes
     * @return
     */
    private List<TreeNodeVo> getChildNodes(String parentCode, Map<String, TreeNodeVo> nodes) {
        List<TreeNodeVo> list = new ArrayList<>();
        for (String key : nodes.keySet()) {
            if (nodes.get(key).getParentCode().equals(parentCode)) {
                list.add(nodes.get(key));
            }
        }
        return list;
    }


}
