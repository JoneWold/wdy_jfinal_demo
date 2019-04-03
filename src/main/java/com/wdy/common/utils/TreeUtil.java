package com.wdy.common.utils;


import com.wdy.common.vo.TreeNodeVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TMW
 * @version 1.0
 * @Description: 机构树
 * @date 2019/3/18 18:08
 */
public class TreeUtil {
    /**
     * 使用递归方法建树
     *
     * @param treeNodes  数据
     * @param parentCode 父节点标示
     * @return
     */
    public static List<TreeNodeVo> buildByRecursive(List<TreeNodeVo> treeNodes, String parentCode) {
        List<TreeNodeVo> trees = new ArrayList<>();
        for (TreeNodeVo treeNode : treeNodes) {
            if (parentCode.equals(treeNode.getParentCode())) {
                trees.add(findChildren(treeNode, treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     *
     * @param treeNodes 数据
     * @return
     */
    public static TreeNodeVo findChildren(TreeNodeVo treeNode, List<TreeNodeVo> treeNodes) {
        for (TreeNodeVo it : treeNodes) {
            if (treeNode.getCode().equals(it.getParentCode())) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                // 是否还有子节点，如果有的话继续往下遍历，如果没有则直接返回
                treeNode.getChildren().add(findChildren(it, treeNodes));
            }
        }
        return treeNode;
    }

    /**
     * 循环生成树形结构
     *
     * @param treeNodes  数据
     * @param parentCode 父节点标示
     * @return
     */
    public static List<TreeNodeVo> bulid(List<TreeNodeVo> treeNodes, String parentCode) {

        List<TreeNodeVo> trees = new ArrayList<>();

        for (TreeNodeVo treeNode : treeNodes) {

            if (parentCode.equals(treeNode.getParentCode())) {
                trees.add(treeNode);
            }

            for (TreeNodeVo it : treeNodes) {
                if (it.getParentCode().equals(treeNode.getCode())) {
                    if (treeNode.getChildren() == null) {
                        treeNode.setChildren(new ArrayList<>());
                    }
                    treeNode.getChildren().add(it);
                }
            }
        }
        return trees;
    }

}
